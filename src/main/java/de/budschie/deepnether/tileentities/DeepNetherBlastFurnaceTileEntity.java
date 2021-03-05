package de.budschie.deepnether.tileentities;

import de.budschie.deepnether.block.NetherBlastFurnaceBlock;
import de.budschie.deepnether.container.DeepNetherBlastFurnaceContainer;
import de.budschie.deepnether.item.IDeepnetherFuel;
import de.budschie.deepnether.tileentities.RecipeEntry.FuelType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.items.CapabilityItemHandler;

public class DeepNetherBlastFurnaceTileEntity extends TileEntity implements ICapabilityProvider, INamedContainerProvider, ITickableTileEntity
{
	RecipeEntry currentRecipe;
	int fuelLeft;
	int durationLeft;
	
	// Must not be castable to an item!
	IDeepnetherFuel currentFuel;
	
	// Slots: 0: Output, 1: Input, 2: Fuel
	ModItemStackHandler inventory = new ModItemStackHandler(3)
	{
		protected void onContentsChanged(int slot) 
		{
			super.onContentsChanged(slot);
			markDirty();
		};
		
		protected void onContentsChanged(int slot, boolean silent) 
		{
			super.onContentsChanged(slot);
			
			if(silent)
				markDirtySilent();
			else
				markDirty();
		};
	};
	
	protected DeepNetherBlastFurnaceTileEntity()
	{
		super(TileEntityInit.DEEP_NETHER_BLAST_FURNACE_TYPE);
	}
	
	@Override
	public CompoundNBT write(CompoundNBT compound)
	{
		compound.put("inventory", inventory.serializeNBT());
		compound.putInt("timeLeft", durationLeft);
		compound.putInt("fuelLeft", fuelLeft);
		if(currentFuel != null)
			compound.put("fuel", DeepNetherBlastFurnaceTileEntity.writeFuel(currentFuel));
		
		if(currentRecipe != null)
			compound.put("recipe", writeCurrentRecipe(currentRecipe));
		return super.write(compound);
	}
	
	public static CompoundNBT writeFuel(IDeepnetherFuel fuel)
	{
		CompoundNBT nbt = new CompoundNBT();
		nbt.putInt("fSpeed", fuel.getFuelSpeed());
		nbt.putInt("fTime", fuel.getFuelTime());
		nbt.putInt("fType", fuel.getFuelType().ordinal());
		return nbt;
	}
	
	public static IDeepnetherFuel readFuel(CompoundNBT nbt)
	{
		return new IDeepnetherFuel()
		{
			
			@Override
			public FuelType getFuelType()
			{
				return (nbt.getInt("fType") >= FuelType.values().length || nbt.getInt("fType") < 0) ? FuelType.HEAT : FuelType.values()[nbt.getInt("fType")];
			}
			
			@Override
			public int getFuelTime()
			{
				return nbt.getInt("fTime");
			}
			
			@Override
			public int getFuelSpeed()
			{
				return nbt.getInt("fSpeed");
			}
		};
	}
	
	public static CompoundNBT writeCurrentRecipe(RecipeEntry currentRecipe)
	{
		CompoundNBT nbt = new CompoundNBT();
		nbt.putInt("type", currentRecipe.fuelType.ordinal());
		nbt.putString("id", RecipesDeepnetherBlastFurnace.getIDFromRecipe(currentRecipe));
		
		return nbt;
	}
	
	public static RecipeEntry readCurrentRecipe(CompoundNBT nbt)
	{
		return RecipesDeepnetherBlastFurnace.recipes.get((nbt.getInt("type") < 0 || nbt.getInt("type") >= FuelType.values().length) ? FuelType.HEAT : FuelType.values()[nbt.getInt("type")]).get(nbt.getString("id"));
	}
	
	@Override
	public void read(BlockState blockState, CompoundNBT compound)
	{
		inventory.deserializeNBT(compound.getCompound("inventory"));
		durationLeft = compound.getInt("timeLeft");
		fuelLeft = compound.getInt("fuelLeft");
		if(compound.contains("fuel"))
			currentFuel = readFuel(compound.getCompound("fuel"));
		if(compound.contains("recipe"))
			currentRecipe = readCurrentRecipe(compound.getCompound("recipe"));
		super.read(blockState, compound);
	}
	
	@Override
	public SUpdateTileEntityPacket getUpdatePacket()
	{
		CompoundNBT compound = new CompoundNBT();
		compound.put("inventory", inventory.serializeNBT());
		compound.putInt("timeLeft", durationLeft);
		compound.putInt("fuelLeft", fuelLeft);
		if(currentFuel != null)
			compound.put("fuel", DeepNetherBlastFurnaceTileEntity.writeFuel(currentFuel));
		
		if(currentRecipe != null)
			compound.put("recipe", writeCurrentRecipe(currentRecipe));
		return new SUpdateTileEntityPacket(getPos(), 0, compound);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt)
	{
		CompoundNBT compound = pkt.getNbtCompound();
		inventory.deserializeNBT(compound.getCompound("inventory"));
		durationLeft = compound.getInt("timeLeft");
		fuelLeft = compound.getInt("fuelLeft");
		if(compound.contains("fuel"))
			currentFuel = readFuel(compound.getCompound("fuel"));
		if(compound.contains("recipe"))
			currentRecipe = readCurrentRecipe(compound.getCompound("recipe"));
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side)
	{
		if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return LazyOptional.of(new NonNullSupplier<T>()
			{
				@Override
				public T get()
				{
					return (T) inventory;
				}
			});
		}
		return super.getCapability(cap, side);
	}
	
	public ModItemStackHandler getInventory()
	{
		return inventory;
	}

	@Override
	public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_)
	{
		return new DeepNetherBlastFurnaceContainer(p_createMenu_1_, p_createMenu_2_, this);
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return new StringTextComponent("Test String!");
	}
	
	@Override
	public void markDirty()
	{
		//Marks if the fuel stack should be decreased
		boolean shouldBurnFuel = (currentFuel == null);
		IDeepnetherFuel fuel = currentFuel != null ? currentFuel : (this.inventory.getStackInSlot(2).getItem() instanceof IDeepnetherFuel ? ((IDeepnetherFuel)this.inventory.getStackInSlot(2).getItem()) : null);
		//First, check if the fuel is a deepnether fuel
		if(fuel != null)
		{	
			//Second, check if any recipe is possible
			RecipeEntry possibleRecipe = RecipesDeepnetherBlastFurnace.getRecipeByItems(this.inventory.getStackInSlot(1).getItem(), fuel);
			
			if(possibleRecipe == null)
			{
				currentRecipe = null;
				durationLeft = 0;
				BlockState newBlockState = getBlockState().with(NetherBlastFurnaceBlock.LIT, false);
				this.getWorld().setBlockState(getPos(), newBlockState);
			}
			else
			{
				// Check if the output slot is valid
				ItemStack iStackOutputSlot = this.inventory.getStackInSlot(0);
				
				if((iStackOutputSlot.getItem() == possibleRecipe.itemOut.getItem() || iStackOutputSlot.getItem() == Items.AIR) && (iStackOutputSlot.getCount() + possibleRecipe.itemOut.getCount()) <= possibleRecipe.itemOut.getMaxStackSize())
				{
					currentRecipe = possibleRecipe;
					BlockState newBlockState = getBlockState().with(NetherBlastFurnaceBlock.LIT, true);
					this.getWorld().setBlockState(getPos(), newBlockState);
					durationLeft = currentRecipe.time;
					if(shouldBurnFuel)
					{
						this.fuelLeft = fuel.getFuelTime();
						this.currentFuel = fuel;
						
						ItemStack newFuelStack = this.inventory.getStackInSlot(2).copy();
						newFuelStack.setCount(this.inventory.getStackInSlot(2).getCount()-1);
						
						this.inventory.setStackInSlot(2, newFuelStack, true);
					}
				}
				else
				{
					durationLeft = 0;
					currentRecipe = null;
					BlockState newBlockState = getBlockState().with(NetherBlastFurnaceBlock.LIT, false);
					this.getWorld().setBlockState(getPos(), newBlockState);
					System.out.println("NAH, not today. Reason: Item not same or air: " + !(iStackOutputSlot.getItem() == possibleRecipe.itemIn || iStackOutputSlot.getItem() == Items.AIR) + " with item " + iStackOutputSlot.getItem().getRegistryName().toString() + "; Too much count: " + !((iStackOutputSlot.getCount() + possibleRecipe.itemOut.getCount()) <= possibleRecipe.itemOut.getMaxStackSize()));
				}
			}
		}
		super.markDirty();
	}
	
	private void markDirtySilent()
	{
		super.markDirty();
	}

	@Override
	public void tick()
	{
		if(currentRecipe != null)
		{
			if(fuelLeft > 0)
			{
				fuelLeft -= currentRecipe.fuelConsume;
				durationLeft -= currentFuel.getFuelSpeed();
				
				if(durationLeft <= 0)
				{
					ItemStack outputSlot = this.inventory.getStackInSlot(0).copy();
					if((outputSlot.getItem() == currentRecipe.itemOut.getItem() || outputSlot.getItem() == Items.AIR) && (outputSlot.getCount() + currentRecipe.itemOut.getCount()) <= currentRecipe.itemOut.getMaxStackSize())
					{
						if(outputSlot.getItem() == Items.AIR)
						{
							this.inventory.setStackInSlot(0, currentRecipe.itemOut.copy());
						}
						else
						{
							outputSlot.setCount(outputSlot.getCount() + currentRecipe.itemOut.getCount());
							this.inventory.setStackInSlot(0, outputSlot, true);
						}
						
						this.currentRecipe = null;
						ItemStack inputSlot = this.inventory.getStackInSlot(1).copy();
						inputSlot.setCount(inputSlot.getCount() - 1);
						this.inventory.setStackInSlot(1, inputSlot, true);
						this.markDirty();
						return;
					}
					else
					{
						throw new IllegalStateException("The item in the output slot isnt the same as the recipe output item or the item count is higher than the max count! This is a bug. Please report it. This maybe happen if you modify this tileentity without minecraft knowing it (this happens when markDirty() isnt called)! If this message keeps crashing your game, reset the tileentity at X:" + this.pos.getX() + " Y: " + pos.getY() + " Z: " + pos.getZ());
					}
				}
				
				if(fuelLeft <= 0)
				{
					if(this.inventory.getStackInSlot(2).getItem() instanceof IDeepnetherFuel)
					{
						IDeepnetherFuel fuel = (IDeepnetherFuel) this.inventory.getStackInSlot(2).getItem();
						RecipeEntry entry = RecipesDeepnetherBlastFurnace.getRecipeByItems(this.inventory.getStackInSlot(1).getItem(), fuel);
						if(entry == null)
						{
							currentRecipe = null;
							durationLeft = 0;
							currentFuel = null;
							BlockState newBlockState = getBlockState().with(NetherBlastFurnaceBlock.LIT, false);
							this.getWorld().setBlockState(getPos(), newBlockState);
						}
						else
						{
							if(currentRecipe != entry)
							{
								currentRecipe = entry;
								durationLeft = currentRecipe.time;
							}
							
							currentFuel = fuel;
							
							ItemStack newFuelStack = this.inventory.getStackInSlot(2).copy();
							newFuelStack.setCount(this.inventory.getStackInSlot(2).getCount()-1);
							
							this.inventory.setStackInSlot(2, newFuelStack, true);
							this.fuelLeft = fuel.getFuelTime();
						}
					}
				}
			}
			else
			{
				durationLeft++;
				if(durationLeft >= currentRecipe.time)
				{
					currentRecipe = null;
				}
			}
		}
	}
}

package de.budschie.deepnether.container;

import de.budschie.deepnether.main.References;
import de.budschie.deepnether.tileentities.DeepNetherBlastFurnaceTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.registries.ObjectHolder;

public class DeepNetherBlastFurnaceContainer extends Container
{
	@ObjectHolder(value = References.MODID+":nether_blast_furnace_container")
	public static final ContainerType<DeepNetherBlastFurnaceContainer> TYPE = null;
	
	PlayerInventory playerInv;
	DeepNetherBlastFurnaceTileEntity te;
	
	public DeepNetherBlastFurnaceTileEntity getTileEntity()
	{
		return te;
	}
	
	public DeepNetherBlastFurnaceContainer(int id, PlayerInventory playerInv, DeepNetherBlastFurnaceTileEntity te)
	{
		super(TYPE, id);
		
		this.playerInv = playerInv;
		this.te = te;
		
		this.addSlot(new SlotItemHandler(te.getInventory(), 1, 56, 17));
        this.addSlot(new SlotItemHandler(te.getInventory(), 2, 56, 53));
        this.addSlot(new SlotItemHandler(te.getInventory(), 0, 116, 35));

        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlot(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k)
        {
            this.addSlot(new Slot(playerInv, k, 8 + k * 18, 142));
        }
	}
	
	public DeepNetherBlastFurnaceContainer(int id, PlayerInventory playerInv)
	{
		super(TYPE, id);
		
		this.playerInv = playerInv;
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn)
	{
		return true;
	}
	
	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index)
	{
		return ItemStack.EMPTY;
	}
}
package de.budschie.deepnether.block;

import java.util.Random;
import java.util.function.Consumer;

import de.budschie.deepnether.tileentities.DeepNetherBlastFurnaceTileEntity;
import de.budschie.deepnether.tileentities.TileEntityInit;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class NetherBlastFurnaceBlock extends BaseBlock 
{
	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	public static final BooleanProperty LIT = BlockStateProperties.LIT;
	public NetherBlastFurnaceBlock(Properties props, String name, ItemGroup group)
	{
		super(props, name, group);
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder)
	{
		builder.add(FACING, LIT);
	}
	
	@Override
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		if(stateIn.get(LIT))
		{
			worldIn.addParticle(ParticleTypes.LARGE_SMOKE, pos.getX(), pos.getY(), pos.getZ(), (rand.nextDouble()-.5)/4, rand.nextDouble(), (rand.nextDouble()-.5)/4);
		}
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing()).with(LIT, false);
	}
	
	@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world)
	{
		return TileEntityInit.DEEP_NETHER_BLAST_FURNACE_TYPE.create();
	}
	
	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player)
	{
		TileEntity te = worldIn.getTileEntity(pos);
		if(te != null && te instanceof DeepNetherBlastFurnaceTileEntity)
		{
			DeepNetherBlastFurnaceTileEntity teCasted = (DeepNetherBlastFurnaceTileEntity) te;
			InventoryHelper.dropItems(worldIn, pos, teCasted.getInventory().getNonNullList());
		}
		
		super.onBlockHarvested(worldIn, pos, state, player);
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_)
	{
		if(!worldIn.isBlockLoaded(pos))
		{
			return ActionResultType.FAIL;
		}
		if(!worldIn.isRemote)
		{
			TileEntity te = worldIn.getTileEntity(pos);
			if(te != null && te instanceof DeepNetherBlastFurnaceTileEntity)
			{
				NetworkHooks.openGui((ServerPlayerEntity) player, ((INamedContainerProvider)te), new Consumer<PacketBuffer>()
				{
					@Override
					public void accept(PacketBuffer t)
					{
						t.writeInt(pos.getX());
						t.writeInt(pos.getY());
						t.writeInt(pos.getZ());
					}
				});
			}
		}
		
		return ActionResultType.SUCCESS;
	}
}

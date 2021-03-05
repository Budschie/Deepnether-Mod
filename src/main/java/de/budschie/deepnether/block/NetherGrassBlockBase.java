package de.budschie.deepnether.block;

import java.util.Random;

import de.budschie.deepnether.main.DeepnetherMain;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public abstract class NetherGrassBlockBase extends BaseBlock
{
	public NetherGrassBlockBase(Properties props, String name, ItemGroup group)
	{
		super(props, name, group);
	}
	
	@Override
	public boolean ticksRandomly(BlockState state)
	{
		return true;
	}
	
	@Override
	public boolean isTransparent(BlockState state)
	{
		return true;
	}
	
	@Override
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random)
	{
		if(!worldIn.getBlockState(pos.add(0, 1, 0)).isSolid() || worldIn.getBlockState(pos.add(0, 1, 0)) == Blocks.AIR.getDefaultState())
		{
			int xOffset = random.nextInt(5)-2;
			int yOffset = random.nextInt(3)-1;
			int zOffset = random.nextInt(5)-2;
			
			BlockPos newSpreadedPos = pos.add(xOffset, yOffset, zOffset);
			
			if(isSoil(worldIn.getBlockState(newSpreadedPos)) && canSpread(worldIn.getBlockState(newSpreadedPos), worldIn.getBlockState(newSpreadedPos.add(0, 1, 0)), newSpreadedPos, worldIn))
			{
				grow(newSpreadedPos, worldIn);
			}
		}
		else
		{
			ungrow(pos, worldIn);
		}
	}
	
	public abstract boolean isSoil(BlockState state);
	
	public boolean canSpread(BlockState stateBlock, BlockState stateAbove, BlockPos stateBlockPos, ServerWorld worldIn)
	{
		return stateAbove == Blocks.AIR.getDefaultState() || !stateAbove.isSolid();
	}
	
	public abstract void ungrow(BlockPos pos, World worldIn);
	public abstract void grow(BlockPos pos, World worldIn);
}

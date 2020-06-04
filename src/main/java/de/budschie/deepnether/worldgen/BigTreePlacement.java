package de.budschie.deepnether.worldgen;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.mojang.datafixers.Dynamic;

import de.budschie.deepnether.block.BlockInit;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class BigTreePlacement extends Placement<IPlacementConfig>
{
	public static final int maxHeight = 110;
	public static final int minHeight = 50;
	
	public BigTreePlacement(Function<Dynamic<?>, ? extends IPlacementConfig> configFactoryIn)
	{
		super(configFactoryIn);
	}

	@Override
	public Stream<BlockPos> getPositions(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generatorIn, Random random, IPlacementConfig configIn, BlockPos pos)
	{
		if(random.nextInt(25) != 0)
			return Stream.empty();
		
		int x = random.nextInt(4) + pos.getX();
		int z = random.nextInt(4) + pos.getZ();
		int y = 1;
		
		loop:
		for(int yInner = minHeight; yInner < maxHeight; yInner++)
		{
			if(worldIn.getBlockState(new BlockPos(x, yInner, z)) == Blocks.AIR.getDefaultState() && worldIn.getBlockState(new BlockPos(x, yInner+1, z)) == Blocks.AIR.getDefaultState() && worldIn.getBlockState(new BlockPos(x, yInner-1, z)) == BlockInit.NETHER_DUST_GRASS_BLOCK.getDefaultState())
			{
				y = yInner;
				break loop;
			}
		}
		
		return Stream.of(new BlockPos(x,y,z));
	}

}

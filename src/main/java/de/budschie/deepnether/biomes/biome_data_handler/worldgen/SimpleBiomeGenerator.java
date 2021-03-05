package de.budschie.deepnether.biomes.biome_data_handler.worldgen;

import java.util.Optional;

import de.budschie.deepnether.dimension.DeepnetherChunkGenerator;
import de.budschie.deepnether.dimension.InterpolationChannelBuffer;
import de.budschie.deepnether.util.MathUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.IChunk;

public class SimpleBiomeGenerator implements IBiomeGenerator, ILavaGenerationInterface
{
	int seaLevel;
	int nearLavaBlockRange;
	BlockState grassBlock;
	BlockState groundBlock;
	BlockState lavaBlock;
	BlockState nearLavaBlock;
	
	public SimpleBiomeGenerator(int seaLevel, int nearLavaBlockRange, BlockState grassBlock, BlockState groundBlock,
			BlockState lavaBlock, BlockState nearLavaBlock)
	{
		super();
		this.seaLevel = seaLevel;
		this.nearLavaBlockRange = nearLavaBlockRange;
		this.grassBlock = grassBlock;
		this.groundBlock = groundBlock;
		this.lavaBlock = lavaBlock;
		this.nearLavaBlock = nearLavaBlock;
		
	}
	
	public SimpleBiomeGenerator(int nearLavaBlockRange, BlockState grassBlock, BlockState groundBlock,
			BlockState lavaBlock, BlockState nearLavaBlock)
	{
		this(WorldGenConsts.SEA_LEVEL, nearLavaBlockRange, grassBlock, groundBlock, lavaBlock, nearLavaBlock);
	}

	@Override
	public void generate(int chunkStartX, int chunkStartZ, int localX, int localZ, IChunk chunk,
			DeepnetherChunkGenerator chunkGenerator, InterpolationChannelBuffer interpolationChannelBuffer)
	{
		int currentTerrainHeight = interpolationChannelBuffer.<Integer>getValue("terrainHeight")[localX][localZ];
		int minTerrainHeight = interpolationChannelBuffer.<Integer>getValue("minTerrainHeight")[localX][localZ];
		double currentHeightValue = interpolationChannelBuffer.<Double>getValue("heightmap")[localX][localZ];
		
		// Modify so that the minTerrainHeight applies
		// Remapping from 0 to 1 to x to 1
		currentHeightValue = MathUtil.linearInterpolation(minTerrainHeight / (double)currentTerrainHeight, 1, currentHeightValue);
				
		int definedTerrainHeight = (int) (currentHeightValue * currentTerrainHeight);
		
		// System.out.println("DATA: terrainHeight:" + currentTerrainHeight + "; currentHeightValue: " + currentHeightValue + "; definedTerrainHeight: " + definedTerrainHeight);
		
		for(int y = 0; y <= Math.max(definedTerrainHeight, seaLevel); y++)
		{
			BlockState chosenBlockState = Blocks.AIR.getDefaultState();
			
			if(y > definedTerrainHeight && y <= seaLevel)
			{
				chosenBlockState = lavaBlock;
			}
			else if(((y - nearLavaBlockRange) < seaLevel && y >= seaLevel) || (y < seaLevel && y == definedTerrainHeight))
			{
				chosenBlockState = nearLavaBlock;
			}
			else if(y < definedTerrainHeight || (y == definedTerrainHeight && y < seaLevel))
			{
				chosenBlockState = groundBlock;
			}
			else if(y == definedTerrainHeight)
			{
				chosenBlockState = grassBlock;
			}
			
			chunk.setBlockState(new BlockPos(localX + chunkStartX, y, localZ + chunkStartZ), chosenBlockState, false);
		}
	}

	@Override
	public Optional<BlockState> getFillerBlock()
	{
		return Optional.empty();
	}	
}

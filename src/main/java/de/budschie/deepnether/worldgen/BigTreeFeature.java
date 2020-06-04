package de.budschie.deepnether.worldgen;

import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Float;
import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import de.budschie.deepnether.block.BlockInit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class BigTreeFeature extends Feature<IFeatureConfig>
{

	public BigTreeFeature(Function<Dynamic<?>, ? extends IFeatureConfig> configFactoryIn)
	{
		super(configFactoryIn);
	}

	@Override
	public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, IFeatureConfig config)
	{
		if(pos.getY() == 1)
		{
			return true;
		}
		
		int maxSize = rand.nextInt(10)+10;
		int maxRadius = rand.nextInt(6)+6;
		
		spawnLeaves(maxSize - 1 - rand.nextInt(5), pos, (rand.nextFloat() + 1)/2, rand, worldIn, maxSize/2, rand.nextInt(4) + 14);
		
		for(int i = 0; i < maxSize; i++)
		{
			float radius = getCurveForSize(maxSize, maxRadius, i, rand);
			Ellipse2D ellipse = new Ellipse2D.Float(-radius/2, -radius/2, radius, radius);
			
			for(int x = -maxRadius; x < maxRadius; x++)
			{
				for(int z = -maxRadius; z < maxRadius; z++)
				{
					if(ellipse.contains(new Point(x, z)))
					{
						BlockPos bPos = new BlockPos(x+pos.getX(), pos.getY()+i, z+pos.getZ());
						//System.out.println("NEW BLOCK AT " + bPos.toString());
						worldIn.setBlockState(bPos, BlockInit.ANCIENT_LOG.getDefaultState(), 3);
					}
				}
			}
		}
		
		return true;
	}
	
	public void spawnLeaves(int at, BlockPos placePos, float decrease, Random random, IWorld world, int layers, float radiusStart)
	{
		float radius = radiusStart;
		for(int i = 0; i < layers; i++)
		{
			Ellipse2D ellipse = new Ellipse2D.Float(-radius/2, -radius/2, radius, radius);
			for(int x = (int) -radius; x < (int)radius; x++)
			{
				for(int z = (int) -radius; z < (int)radius; z++)
				{
					if(ellipse.contains(new Point(x, z)))
					{
						world.setBlockState(new BlockPos(placePos.getX() + x, placePos.getY() + i + at, placePos.getZ() + z), random.nextInt(5) == 0 ? BlockInit.ANCIENT_WITHERED_LEAVES.getDefaultState() : BlockInit.ANCIENT_LEAVES.getDefaultState(), 2);
					}
				}
			}
			radius-=decrease;
		}
	}
	
	/** Gets a radius for the maxSize/ currentPosition. currentPosition = 0 is the starting point**/
	public static float getCurveForSize(float maxSize, float maxRadius, float currentPosition, Random rand)
	{
		if(currentPosition <= 1)
		{
			return (maxRadius-1)-rand.nextFloat();
		}
		else
		{
			float v = lerp((maxRadius-1), maxRadius/2, (float) (Math.sin((currentPosition/maxSize))));
			return v;
		}
	}
	
	public static float lerp(float val1, float val2, float toLerp)
	{
		return val1 * (1-toLerp) + val2 * toLerp;
	}
}

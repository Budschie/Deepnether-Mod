package de.budschie.deepnether.worldgen;

import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import de.budschie.deepnether.block.BlockInit;
import de.budschie.deepnether.block.NetherCrystalBlockBase;
import net.kdotjpg.opensimplexnoise.OpenSimplexNoise;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class CrystalsWorldGen extends Feature<IFeatureConfig>
{
	public CrystalsWorldGen(Function<Dynamic<?>, ? extends IFeatureConfig> configFactoryIn)
	{
		super(configFactoryIn);
	}
	// ArrayList<BlockState> blocks = new ArrayList<BlockState>();
	
	@Override
	public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos position, IFeatureConfig config)
	{
		System.out.println("PLACING!!!!!!!!!!!");
		OpenSimplexNoise osn = new OpenSimplexNoise(rand.nextLong());
		
		//BlockState crystalBlock = blocks.get(rand.nextInt(blocks.size()));
		BlockState crystalBlock = NetherCrystalBlockBase.NETHER_CRYSTALS.get(rand.nextInt(NetherCrystalBlockBase.NETHER_CRYSTALS.size())).getDefaultState();
		boolean hasAlreadyFoundOneBlock = false;
		
		BlockPos startingPos = null;
		
		findPos:
		for(int y = 150; y > 0; y--)
		{
			BlockState currentBlockState = worldIn.getBlockState(new BlockPos(position.getX(), y, position.getZ()));
			if(currentBlockState == BlockInit.COMPRESSED_NETHERRACK.getDefaultState())
			{
				hasAlreadyFoundOneBlock = true;
			}
			else if(currentBlockState == Blocks.AIR.getDefaultState() && hasAlreadyFoundOneBlock)
			{
				startingPos = new BlockPos(position.getX(), y, position.getZ());
				break findPos;
			}
		}
		
		if(startingPos == null)
		{
			System.out.println("M FAILED");
			return false;
		}
		
		boolean isNoMore = false;
		int ySubtract = 0;
		float thickness = rand.nextInt(10);
		float thicknessSubtractor = rand.nextFloat()+0.09f;
		
		float thicknessActual = thickness;
		
		BlockPos posLast = null;
		
		while(!isNoMore)
		{
			//Do Stuff
			if(posLast == null)
			{
				posLast = startingPos.subtract(new Vec3i(0, ySubtract, 0));
			}
			
			
			
			int xAdd = 0;
			boolean xNegative = false;
			float xAddCalcOsn = (float) osn.eval(posLast.getX(), posLast.getY(), posLast.getZ());
			if(xAddCalcOsn < 0)
			{
				xNegative = true;
				xAddCalcOsn *= -1;
			}
			
			if(xAddCalcOsn >= rand.nextFloat())
			{
				xAdd = (xNegative ? -1 : 1);
			}
			
			int zAdd = 0;
			boolean zNegative = false;
			float zAddCalcOsn = (float) osn.eval(posLast.getX(), posLast.getY(), posLast.getZ()+50);
			if(zAddCalcOsn < 0)
			{
				zNegative = true;
				zAddCalcOsn *= -1;
			}
			
			if(zAddCalcOsn >= rand.nextFloat())
			{
				zAdd = (zNegative ? -1 : 1);
			}

			BlockPos centerPosActual = new BlockPos(posLast.getX()+xAdd, startingPos.getY()-ySubtract, posLast.getZ()+zAdd);
			Ellipse2D ellipse = new Ellipse2D.Float(centerPosActual.getX()-(thicknessActual/2), centerPosActual.getZ()-(thicknessActual/2), thicknessActual+new Random().nextFloat(), thicknessActual+new Random().nextFloat());

			for(int x = 0; x < thicknessActual*2; x++)
			{
				for(int z = 0; z < thicknessActual*2; z++)
				{
					if(ellipse.contains(new Point((int)(centerPosActual.getX()+x-thicknessActual), (int)(centerPosActual.getZ()+z-thicknessActual))))
					{
						worldIn.setBlockState(new BlockPos(centerPosActual.getX()+x-thicknessActual, startingPos.getY()-ySubtract, centerPosActual.getZ()+z-thicknessActual), crystalBlock, 2);
					}
				}
			}
			
			//END
			
			ySubtract+=1;
			thicknessActual-=thicknessSubtractor;
			posLast = new BlockPos(centerPosActual.getX(), centerPosActual.getY(), centerPosActual.getZ());
			
			if(thicknessActual <= 0)
			{
				isNoMore = true;
			}
		}
		
		return true;
	}
	
	/*
	public CrystalsWorldGen(BlockState reactToBlockState)
	{
		blockStateToReact = reactToBlockState;
	}
	
	public void addBlock(BlockState state)
	{
		blocks.add(state);
	}
	*/
	
	/*
	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) 
	{
		OpenSimplexNoise osn = new OpenSimplexNoise(rand.nextLong());
		
		BlockState crystalBlock = blocks.get(rand.nextInt(blocks.size()));
		boolean hasAlreadyFoundOneBlock = false;
		
		BlockPos startingPos = null;
		
		findPos:
		for(int y = 150; y > 0; y--)
		{
			BlockState currentBlockState = worldIn.getBlockState(new BlockPos(position.getX(), y, position.getZ()));
			if(currentBlockState == blockStateToReact)
			{
				hasAlreadyFoundOneBlock = true;
			}
			else if(currentBlockState == Blocks.AIR.getDefaultState() && hasAlreadyFoundOneBlock)
			{
				startingPos = new BlockPos(position.getX(), y, position.getZ());
				break findPos;
			}
		}
		
		if(startingPos == null)
			return false;
		
		boolean isNoMore = false;
		int ySubtract = 0;
		float thickness = rand.nextInt(10);
		float thicknessSubtractor = rand.nextFloat()+0.09f;
		
		float thicknessActual = thickness;
		
		BlockPos posLast = null;
		
		while(!isNoMore)
		{
			//Do Stuff
			if(posLast == null)
			{
				posLast = startingPos.subtract(new Vec3i(0, ySubtract, 0));
			}
			
			
			
			int xAdd = 0;
			boolean xNegative = false;
			float xAddCalcOsn = (float) osn.eval(posLast.getX(), posLast.getY(), posLast.getZ());
			if(xAddCalcOsn < 0)
			{
				xNegative = true;
				xAddCalcOsn *= -1;
			}
			
			if(xAddCalcOsn >= rand.nextFloat())
			{
				xAdd = (xNegative ? -1 : 1);
			}
			
			int zAdd = 0;
			boolean zNegative = false;
			float zAddCalcOsn = (float) osn.eval(posLast.getX(), posLast.getY(), posLast.getZ()+50);
			if(zAddCalcOsn < 0)
			{
				zNegative = true;
				zAddCalcOsn *= -1;
			}
			
			if(zAddCalcOsn >= rand.nextFloat())
			{
				zAdd = (zNegative ? -1 : 1);
			}

			BlockPos centerPosActual = new BlockPos(posLast.getX()+xAdd, startingPos.getY()-ySubtract, posLast.getZ()+zAdd);
			Ellipse2D ellipse = new Ellipse2D.Float(centerPosActual.getX()-(thicknessActual/2), centerPosActual.getZ()-(thicknessActual/2), thicknessActual+new Random().nextFloat(), thicknessActual+new Random().nextFloat());

			for(int x = 0; x < thicknessActual*2; x++)
			{
				for(int z = 0; z < thicknessActual*2; z++)
				{
					if(ellipse.contains(new Point((int)(centerPosActual.getX()+x-thicknessActual), (int)(centerPosActual.getZ()+z-thicknessActual))))
					{
						worldIn.setBlockState(new BlockPos(centerPosActual.getX()+x-thicknessActual, startingPos.getY()-ySubtract, centerPosActual.getZ()+z-thicknessActual), crystalBlock);
					}
				}
			}
			
			//END
			
			ySubtract+=1;
			thicknessActual-=thicknessSubtractor;
			posLast = new BlockPos(centerPosActual.getX(), centerPosActual.getY(), centerPosActual.getZ());
			
			if(thicknessActual <= 0)
			{
				isNoMore = true;
			}
		}
		
		return true;
	}
	*/
}

package de.budschie.deepnether.features.tree_generation.leaves;

import java.util.Random;
import java.util.function.BiConsumer;

import de.budschie.deepnether.features.tree_generation.ITreePart;
import de.budschie.deepnether.features.tree_generation.TreeEmitionArgs;
import de.budschie.deepnether.util.geometry.Sphere;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;

public class GreenForestLeaves implements ITreePart
{
	private int sphereRadius;
	private int deltaSphereRadius;
	private BlockState leaves;
	
	public GreenForestLeaves(int sphereRadius, int deltaSphereRadius, BlockState leaves)
	{
		this.sphereRadius = sphereRadius;
		this.deltaSphereRadius = deltaSphereRadius;
		this.leaves = leaves;
	}

	@Override
	public void generate(BiConsumer<String, TreeEmitionArgs> emit, TreeEmitionArgs emitionArgs, ISeedReader reader,
			ChunkGenerator generator, Random rand)
	{
		if(!(emitionArgs instanceof LeavesEmitionArgs))
			throw new IllegalArgumentException("The emition args of " + emitionArgs.getClass().getName() + " and " + LeavesEmitionArgs.class.getName() + " are not compatible.");
		
		LeavesEmitionArgs castedArgs = (LeavesEmitionArgs) emitionArgs;
		
		int addOrSubtract = rand.nextBoolean() ? 1 : -1;
		int thisTimeSphereRadius = sphereRadius + (addOrSubtract * rand.nextInt(deltaSphereRadius));
		
		if(castedArgs.getCurrentTreeHeight() == castedArgs.getFullTreeSize())
			thisTimeSphereRadius *= 2;
		
		// thisTimeSphereRadius *= (((float)castedArgs.getCurrentTreeHeight() / castedArgs.getFullTreeSize()) * 2);
		
		Sphere leavesSphere = new Sphere(thisTimeSphereRadius, emitionArgs.getEmittedFrom());
		leavesSphere.placeInWorld(position -> 
		{
			if(reader.getBlockState(position) == Blocks.AIR.getDefaultState())
				reader.setBlockState(position, leaves, 2);
		});
	}
}

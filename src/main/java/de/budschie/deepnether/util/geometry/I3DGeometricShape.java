package de.budschie.deepnether.util.geometry;

import java.util.function.Consumer;

import net.minecraft.util.math.BlockPos;

/** Fancy name. **/
public interface I3DGeometricShape
{
	/** This method returns true if the point pos is in this geometric shape with its location. **/
	boolean containsPositionedPoint(BlockPos pos);
	
	/** This method returns true if a point, represented by the three integers, is in this geometric shape centered around the world origin(0, 0, 0). **/
	boolean containsCenteredPoint(int x, int y, int z);
	
	/** This method places this geometric shape into the world. **/
	void placeInWorld(Consumer<BlockPos> placer);
}

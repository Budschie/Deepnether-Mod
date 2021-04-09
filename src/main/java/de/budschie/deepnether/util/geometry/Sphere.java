package de.budschie.deepnether.util.geometry;

import java.util.function.Consumer;

import net.minecraft.util.math.BlockPos;

public class Sphere implements I3DGeometricShape
{
	private int radius;
	private BlockPos spherePosition;
	
	public Sphere(int radius, BlockPos spherePosition)
	{
		this.radius = radius;
		this.spherePosition = spherePosition;
	}
	
	/** This returns true if a point x is in this sphere with its radius and its set position. **/
	@Override
	public boolean containsPositionedPoint(BlockPos position)
	{
		BlockPos distanceToCenter = position.subtract(spherePosition);
		
		return (radius * radius) > (distanceToCenter.getX() * distanceToCenter.getX() + distanceToCenter.getY() * distanceToCenter.getY() + distanceToCenter.getZ() * distanceToCenter.getZ());
	}
	
	/** This returns true if a point x, defined by the three integers x, y and z, is in this sphere with its radius, but centered. **/
	@Override
	public boolean containsCenteredPoint(int x, int y, int z)
	{
		return (radius * radius) > (x * x + y * y + z * z);
	}
	
	@Override
	public void placeInWorld(Consumer<BlockPos> placer)
	{
		for(int x = -radius; x < radius; x++)
		{
			for(int y = -radius; y < radius; y++)
			{
				for(int z = -radius; z < radius; z++)
				{
					if(containsCenteredPoint(x, y, z))
					{
						placer.accept(new BlockPos(x + spherePosition.getX(), y + spherePosition.getY(), z + spherePosition.getZ()));
					}
				}
			}
		}
	}
}

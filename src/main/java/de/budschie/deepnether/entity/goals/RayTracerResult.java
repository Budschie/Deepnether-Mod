package de.budschie.deepnether.entity.goals;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;

public class RayTracerResult
{
	public enum Result
	{
		MISS, BLOCK, ENTITY
	}
	
	public RayTracerResult() {
		// TODO Auto-generated constructor stub
	}

	public Result getResult()
	{
		return result;	
	}
	
	public BlockPos getBlockPos()
	{
		return pos;
	}
	
	public LivingEntity getHitEntity()
	{
		return entity;
	}
	
	Result result;
	BlockPos pos;
	LivingEntity entity;
	
	@Override
	public String toString() {
		String output = "{";
		if(result != null)
		
		output += "Result: " + result;
		else
		output += "Result: NULL";
		
		if(pos != null)
		output += ("; BlockPos: " + Integer.valueOf(pos.getX()) + " " + Integer.valueOf(pos.getY()) + " " + Integer.valueOf(pos.getZ()));
		else
		output += "; BlockPos: NULL";
		
		if(entity != null)
		output += "; entityLiving: " + entity.toString();
		else
		output += "; entityLiving: NULL";
		output += "; }";

		return output;
	}
}


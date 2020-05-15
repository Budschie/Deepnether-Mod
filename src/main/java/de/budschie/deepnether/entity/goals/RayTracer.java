package de.budschie.deepnether.entity.goals;

import java.util.ArrayList;
import java.util.List;

import de.budschie.deepnether.entity.goals.RayTracerResult.Result;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class RayTracer 
{

	Vec3d start,end;

	
	ArrayList<LivingEntity> entityToCheck = new ArrayList<LivingEntity>();
	
	public RayTracer(Vec3d start, Vec3d end, boolean shouldGrow)
	{
		this.start = start;
		this.end = end;
		float amount = 1.0f;
		
		if(shouldGrow)
		{
			start = this.start.add(start.x < 0 ? start.x + amount : start.x - amount, start.y < 0 ? start.y + amount : start.y - amount, start.z < 0 ? start.z + amount : start.z - amount);
			end = this.end.add(end.x < 0 ? end.x - amount : end.x + amount, end.y < 0 ? end.y - amount : end.y + amount, end.z < 0 ? end.z - amount : end.z + amount);
		}
		
		if(end == null || start == null)
		{
			throw new IllegalArgumentException("The parameters of the constructor can't be null");
		}
	}
	
	public void calcEntitiesToCheck(World world)
	{
		AxisAlignedBB aabb = new AxisAlignedBB(start, end);
		aabb = aabb.grow(1,1,1);
		
		if(aabb == null)
		{
			throw new IllegalArgumentException("The AxisAlignedBB wasnt initialized (maybe the start and end var is null?)");
		}
		
		
		if(world.getEntitiesWithinAABB(LivingEntity.class, aabb) != null)
		{
			entityToCheck = (ArrayList<LivingEntity>) world.getEntitiesWithinAABB(LivingEntity.class, aabb);
			System.out.println(entityToCheck.size() + " entities will be checked.");
		}
		else
		{
			//System.out.println("No entity was found!");
		}
		
	}
	
	public void calcEntitiesToCheckExcluedEntity(World world, List<Class> classList)
	{
		AxisAlignedBB aabb = new AxisAlignedBB(start, end);
		aabb = aabb.grow(1,1,1);
		
		if(aabb == null)
		{
			throw new IllegalArgumentException("The AxisAlignedBB wasnt initialized (maybe the start and end var is null?)");
		}
		
		
		if(world.getEntitiesWithinAABB(LivingEntity.class, aabb) != null)
		{
			//entityToCheck = (ArrayList<EntityLivingBase>) Minecraft.getMinecraft().world.getEntitiesWithinAABB(EntityLivingBase.class, aabb);
			for(LivingEntity base : (ArrayList<LivingEntity>) world.getEntitiesWithinAABB(LivingEntity.class, aabb))
			{
				if(!classList.contains(base.getClass()))
				{
					entityToCheck.add(base);
				}
			}
			//System.out.println(entityToCheck.size() + " entities will be checked.");
		}
		else
		{
			//System.out.println("No entity was found!");
		}
		
	}
	
	public RayTracerResult run(World world, float quality)
	{
		RayTracerResult result = new RayTracerResult();
		result.result = Result.MISS;
		for(float i = 0; i <= 1.0f; i+= quality)
		{
			Vec3d positionNew = new Vec3d(interpolate(start.x, end.x, i), interpolate(start.y, end.y, i), interpolate(start.z, end.z, i));
			BlockPos pos = new BlockPos(Math.floor(positionNew.x), Math.floor(positionNew.y), Math.floor(positionNew.z));
			if(world.getBlockState(pos) != Blocks.AIR.getDefaultState())
			{
				result.result = Result.BLOCK;
				result.pos = pos;
				return result;
			}
			for(LivingEntity living : entityToCheck)
			{
			//	System.out.println("Checking for entity... " + living.toString());
				if(living.getBoundingBox().contains(positionNew))
				{
					result.result = Result.ENTITY;
					result.entity = living;
					
					return result;
				}
			}
		}
		
		return result;
	}
	
	public RayTracerResult run(World world, float quality, float longness, boolean doesWaterCount)
	{
		RayTracerResult result = new RayTracerResult();
		result.result = Result.MISS;
		for(float i = 0; i <= longness; i+= quality)
		{
			Vec3d positionNew = new Vec3d(interpolate(start.x, end.x, i), interpolate(start.y, end.y, i), interpolate(start.z, end.z, i));
			BlockPos pos = new BlockPos(Math.floor(positionNew.x), Math.floor(positionNew.y), Math.floor(positionNew.z));
			if(doesWaterCount)
			{
				if(world.getBlockState(pos) != net.minecraft.block.Blocks.AIR.getDefaultState() && world.getBlockState(pos) != net.minecraft.block.Blocks.WATER.getDefaultState())
				{
					result.result = Result.BLOCK;
					result.pos = pos;
					return result;
				}
			}
			else
			{
				if(world.getBlockState(pos) != Blocks.AIR.getDefaultState())
				{
					result.result = Result.BLOCK;
					result.pos = pos;
					return result;
				}
			}

			for(LivingEntity living : entityToCheck)
			{
				//System.out.println("Checking for entity... " + living.toString());
				if(living.getBoundingBox().contains(positionNew))
				{
					result.result = Result.ENTITY;
					result.entity = living;
				//	System.out.println("OUTPUT FOUND!");
					return result;
				}
			}
		}
		
		return result;
	}
	
	public RayTracerResult run(World world, float quality, boolean doesWaterCount, boolean shouldGrowAABBOfTarget)
	{
		RayTracerResult result = new RayTracerResult();
		result.result = Result.MISS;
		for(float i = 0; i <= 1.0f; i+= quality)
		{
			Vec3d positionNew = new Vec3d(interpolate(start.x, end.x, i), interpolate(start.y, end.y, i), interpolate(start.z, end.z, i));
			BlockPos pos = new BlockPos(Math.floor(positionNew.x), Math.floor(positionNew.y), Math.floor(positionNew.z));
			if(doesWaterCount)
			{
				if(world.getBlockState(pos) != Blocks.AIR.getDefaultState() && world.getBlockState(pos) != Blocks.WATER.getDefaultState())
				{
					result.result = Result.BLOCK;
					result.pos = pos;
					return result;
				}
			}
			else
			{
				if(world.getBlockState(pos) != Blocks.AIR.getDefaultState())
				{
					result.result = Result.BLOCK;
					result.pos = pos;
					return result;
				}
			}

			for(LivingEntity living : entityToCheck)
			{
				//System.out.println("Checking for entity... " + living.toString());
				if(living.getBoundingBox().contains(positionNew) && !shouldGrowAABBOfTarget)
				{
					result.result = Result.ENTITY;
					result.entity = living;
					//System.out.println("OUTPUT FOUND!");
					return result;
				}
				else if (living.getBoundingBox().grow(1.0f).contains(positionNew))
				{
					result.result = Result.ENTITY;
					result.entity = living;
					//System.out.println("OUTPUT FOUND!");
					return result;
				}
			}
		}
		
		return result;
	}
	
	public float interpolate(float x, float x2, float pos)
	{
		float output = 0.0f;
		float normalizedValue = 0.0f;
		float xRealSmall = x < x2 ? x : x2;
		float xRealBig = x >= x2 ? x : x2;
		
		if(xRealSmall < 0)
		{
			normalizedValue += (xRealSmall * -1);
		}
		
		output = ((xRealSmall + normalizedValue) * pos + (xRealBig + normalizedValue) * ((pos - 1) * -1));
		
		
		return (output - normalizedValue);
	}
	
	public double interpolate(double x, double x2, float pos)
	{
		double output = 0.0f;
		double normalizedValue = 0.0f;
		double xRealSmall = x < x2 ? x : x2;
		double xRealBig = x >= x2 ? x : x2;
		
		if(xRealSmall < 0)
		{
			normalizedValue += (xRealSmall * -1);
		}
		
		output = ((xRealSmall + normalizedValue) * pos + (xRealBig + normalizedValue) * ((pos - 1) * -1));
		
		
		return (output - normalizedValue);
	}
	
}
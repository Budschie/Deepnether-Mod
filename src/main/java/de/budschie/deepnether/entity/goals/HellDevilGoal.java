package de.budschie.deepnether.entity.goals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.budschie.deepnether.entity.HellDevilEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.server.ServerWorld;

public class HellDevilGoal extends Goal
{
	HellDevilEntity entity;
	
	public HellDevilGoal(HellDevilEntity entity)
	{
		this.entity = entity;
	}
	
	
	
	@Override
	public boolean shouldExecute() {
		// TODO Auto-generated method stub
		return this.entity.getAttackTarget() != null && this.entity.isChallenged();
	}
	
	@Override
	public void tick() 
	{
		// System.out.println("TIIIIIICKEEEEEEEEED!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		//Maybe check if the attack target is null
		boolean shouldDamage = false;
		//Vector vector = entity.getAttackTarget().pos.toVector().subtract(first_location.toVector());
		
		Vec3d vec = entity.getAttackTarget().getPositionVector();
		Vec3d vecEntity = entity.getPositionVector();
		
		float distance = (float) Math.sqrt((Math.pow((vec.x - vecEntity.x), 2.0)+ Math.pow((vec.y - vecEntity.y), 2.0) + Math.pow((vec.z - vecEntity.z), 2.0)));
		//System.out.println("distance is " + distance);
		
		float quality = (1.0f / distance);
		//System.out.println("The quality is " + quality);
		
		RayTracer rayTracer = new RayTracer(vecEntity.add(0, 1, 0), vec.add(0, 1, 0), false);
		ArrayList<Class> list = new ArrayList<>();
		list.add(HellDevilEntity.class);
		rayTracer.calcEntitiesToCheckExcluedEntity(this.entity.world, (List<Class>)list);
		
		
		/** RUN **/
		RayTracerResult result = rayTracer.run(this.entity.world, quality, true, true);
		//System.out.println(result.toString());
		
		
		if(result.entity != null && result.entity.equals(entity.getAttackTarget()))
		{
			shouldDamage = true;
			Vec3d vecSpeed = vec.subtract(vecEntity);
		}
		
		if(shouldDamage)
		{
			entity.getAttackTarget().setFire(2);
			entity.getAttackTarget().attackEntityFrom(DamageSource.GENERIC, 4);
			entity.setIsAttacking();
		}
		else
		{
			System.out.println("COULDNT DAMAGE; RESETING");
			entity.resetAttacking();
		}
	}
}

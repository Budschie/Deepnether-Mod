package de.budschie.deepnether.entity.goals;

import java.util.EnumSet;

import de.budschie.deepnether.entity.HellCreeperEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.CreeperSwellGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.monster.CreeperEntity;

public class HellCreeperSpecialAIGoal extends Goal
{
	private final HellCreeperEntity swellingCreeper;
	   private LivingEntity creeperAttackTarget;

	   public HellCreeperSpecialAIGoal(HellCreeperEntity entitycreeperIn) 
	   {
	      this.swellingCreeper = entitycreeperIn;
	      this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
	   }

	   /**
	    * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
	    * method as well.
	    */
	   public boolean shouldExecute() {
	      LivingEntity livingentity = this.swellingCreeper.getAttackTarget();
	      return this.swellingCreeper.getCreeperState() > 0 || livingentity != null && this.swellingCreeper.getDistanceSq(livingentity) < 9.0D;
	   }

	   /**
	    * Execute a one shot task or start executing a continuous task
	    */
	   public void startExecuting() {
	      this.swellingCreeper.getNavigator().clearPath();
	      this.creeperAttackTarget = this.swellingCreeper.getAttackTarget();
	   }

	   /**
	    * Reset the task's internal state. Called when this task is interrupted by another one
	    */
	   public void resetTask() 
	   {
	      this.creeperAttackTarget = null;
	   }

	   /**
	    * Keep ticking a continuous task that has already been started
	    */
	   public void tick() 
	   {
		  if(this.swellingCreeper.getSpeed() > 8)
		  {
			   this.swellingCreeper.setCreeperState(1);
			   return;
		  }
		   
	      if (this.creeperAttackTarget == null) 
	      {
	         this.swellingCreeper.setCreeperState(-1);
	      } 
	      else if (this.swellingCreeper.getDistanceSq(this.creeperAttackTarget) > 49.0D) 
	      {
	         this.swellingCreeper.setCreeperState(-1);
	      } 
	      else if (!this.swellingCreeper.getEntitySenses().canSee(this.creeperAttackTarget)) 
	      {
	         this.swellingCreeper.setCreeperState(-1);
	      } 
	      else 
	      {
	    	  if(this.swellingCreeper.getCreeperState() != 1)
	    	  {
	 	         this.swellingCreeper.addSpeed(1);
	 	         System.out.println("added speeeeeeeeeeeeeeeeeeed: " + swellingCreeper.getSpeed());
	    	  }
	    	  
	         this.swellingCreeper.setCreeperState(1);
	      }
	   }
}

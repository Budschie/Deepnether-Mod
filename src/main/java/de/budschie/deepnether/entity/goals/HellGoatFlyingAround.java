package de.budschie.deepnether.entity.goals;

import java.util.EnumSet;
import java.util.Random;

import de.budschie.deepnether.entity.HellGoatEntity;
import de.budschie.deepnether.entity.HellGoatEntity.HellGoatMovement;
import net.minecraft.entity.ai.goal.Goal;

public class HellGoatFlyingAround extends Goal
{
	HellGoatEntity entity;
	
	int ticksUntilSearchingChange = 0;
	
	boolean found = false;
	
	public HellGoatFlyingAround(HellGoatEntity entity)
	{
		this.entity = entity;
		this.setMutexFlags(EnumSet.of(Flag.MOVE));
	}
	
	@Override
	public boolean shouldExecute()
	{
		return entity.getMovementPhase() == HellGoatMovement.FLYING;
	}
	
	@Override
	public void startExecuting()
	{
		Random rand = new Random();
		this.entity.getNavigator().tryMoveToXYZ(entity.getPosX() - 5 + rand.nextInt(10), 40 + rand.nextInt(40), entity.getPosZ() - 5 + rand.nextInt(10), 1f);
	}
}

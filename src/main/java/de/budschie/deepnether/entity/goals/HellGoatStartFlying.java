package de.budschie.deepnether.entity.goals;

import java.util.EnumSet;

import de.budschie.deepnether.dimension.DeepnetherChunkGenerator;
import de.budschie.deepnether.entity.HellGoatEntity;
import de.budschie.deepnether.entity.HellGoatEntity.HellGoatBioPhase;
import de.budschie.deepnether.entity.HellGoatEntity.HellGoatMovement;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;

public class HellGoatStartFlying extends Goal
{
	HellGoatEntity entity;
	
	int ticksUntilSearchingChange = 0;
	
	boolean found = false;
	
	public HellGoatStartFlying(HellGoatEntity entity)
	{
		this.entity = entity;
		this.setMutexFlags(EnumSet.of(Flag.MOVE));
	}
	
	@Override
	public boolean shouldExecute()
	{
		return entity.getBioPhase() == HellGoatBioPhase.SEARCHING_FOOD && entity.getMovementPhase() != HellGoatMovement.NONE;
	}
	
	@Override
	public void startExecuting()
	{
		if(entity.getMovementPhase() == HellGoatMovement.WALKING)
			entity.startFlying();
	}
}

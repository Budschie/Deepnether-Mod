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

public class HellGoatFindFood extends Goal
{
	HellGoatEntity entity;
	
	int ticksUntilSearchingChange = 0;
	
	boolean found = false;
	
	public HellGoatFindFood(HellGoatEntity entity)
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
	
	@Override
	public void tick()
	{
		if(!entity.getDataManager().get(HellGoatEntity.FOOD_BLOCK_TARGET).isPresent())
		{
			if(!(entity.getBoundingBox().maxY + 5 >= DeepnetherChunkGenerator.cloudMinHeigth))
			{
				entity.setMotion(entity.getMotion().add(0, .25, 0));
				if(ticksUntilSearchingChange <= 0)
				{
					ticksUntilSearchingChange = 60;
					
					if(!found)
					{
						for(int x = -2; x <= 2; x++)
						{
							for(int y = 1; y <= 3; y++)
							{
								for(int z = -2; z <= 2; z++)
								{
									BlockState block = entity.getEntityWorld().getBlockState(new BlockPos(x + entity.getPosX(), y + entity.getPosY(), z + entity.getPosZ()));
									
									if(block.getBlock() == Blocks.AIR)
									{
										found = true;
									}
								}
							}
						}
					}
				}
				
				ticksUntilSearchingChange--;
				
				if(found)
					entity.setMotion(entity.getMotion().add(0, .3f, 0));
			}
			else
			{
				found = false;
			}
		}
	}
}

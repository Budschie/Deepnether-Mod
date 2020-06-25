package de.budschie.deepnether.entity;

import java.util.Optional;

import de.budschie.deepnether.entity.goals.HellGoatFindFood;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HellGoatEntity extends CreatureEntity
{
	public static final DataParameter<Integer> FOOD_TIME = EntityDataManager.createKey(HellGoatEntity.class, DataSerializers.VARINT);
	public static final DataParameter<Integer> HELL_GOAT_BIO_PHASES = EntityDataManager.createKey(HellGoatEntity.class, DataSerializers.VARINT);
	public static final DataParameter<Integer> HELL_GOAT_MOVEMENT_PHASES = EntityDataManager.createKey(HellGoatEntity.class, DataSerializers.VARINT);
	
	public static final DataParameter<Optional<BlockPos>> FOOD_BLOCK_TARGET = EntityDataManager.createKey(HellGoatEntity.class, DataSerializers.OPTIONAL_BLOCK_POS);
	
	public static final DataParameter<Boolean> STARTED_FLYING = EntityDataManager.createKey(HellGoatEntity.class, DataSerializers.BOOLEAN);
	
	public int ticksLeft = 0;
	
	protected HellGoatEntity(EntityType<HellGoatEntity> type, World worldIn)
	{
		super(type, worldIn);
	}
	
	@Override
	protected void registerGoals()
	{
		//this.goalSelector.addGoal(0, new SwimGoal(this));
		//this.goalSelector.addGoal(1, new WaterAvoidingRandomWalkingGoal(this, getAIMoveSpeed()));
		//this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 5));
		this.goalSelector.addGoal(3, new HellGoatFindFood(this));
		//this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
	}
	
	public void startFlying()
	{
		ticksLeft = 20;
		this.dataManager.set(STARTED_FLYING, true);
		this.setMovementPhase(HellGoatMovement.NONE);
	}
	
	@Override
	public void tick()
	{
		super.tick();
		
		this.getDataManager().set(FOOD_TIME, this.getDataManager().get(FOOD_TIME)-1);
		if(this.dataManager.get(FOOD_TIME) <= 0)
			this.setBioPhase(HellGoatBioPhase.SEARCHING_FOOD);
		
		if(this.dataManager.get(STARTED_FLYING))
		{
			ticksLeft--;
			if(ticksLeft <= 0)
			{
				this.dataManager.set(STARTED_FLYING, false);
				this.setMovementPhase(HellGoatMovement.FLYING);
			}
		}
	}
	
	@Override
	protected void registerData()
	{
		super.registerData();
		
		this.dataManager.register(FOOD_TIME, 10);
		this.dataManager.register(HELL_GOAT_BIO_PHASES, HellGoatBioPhase.IDLE.ordinal());
		this.dataManager.register(HELL_GOAT_MOVEMENT_PHASES, HellGoatMovement.WALKING.ordinal());
		this.dataManager.register(FOOD_BLOCK_TARGET, Optional.empty());
		this.dataManager.register(STARTED_FLYING, false);
	}
	
	@Override
	public float getAIMoveSpeed()
	{
		return 5f;
	}
	
	public HellGoatBioPhase getBioPhase()
	{
		return (this.dataManager.get(HELL_GOAT_BIO_PHASES) >= HellGoatBioPhase.values().length || this.dataManager.get(HELL_GOAT_BIO_PHASES) < 0) ? HellGoatBioPhase.IDLE : HellGoatBioPhase.values()[this.dataManager.get(HELL_GOAT_BIO_PHASES)];
	}
	
	public void setBioPhase(HellGoatBioPhase phase)
	{
		this.dataManager.set(HELL_GOAT_BIO_PHASES, phase.ordinal());
	}
	
	public HellGoatMovement getMovementPhase()
	{
		return (this.dataManager.get(HELL_GOAT_MOVEMENT_PHASES) >= HellGoatBioPhase.values().length || this.dataManager.get(HELL_GOAT_MOVEMENT_PHASES) < 0) ? HellGoatMovement.WALKING : HellGoatMovement.values()[this.dataManager.get(HELL_GOAT_MOVEMENT_PHASES)];
	}
	
	public void setMovementPhase(HellGoatMovement phase)
	{
		this.dataManager.set(HELL_GOAT_MOVEMENT_PHASES, phase.ordinal());
	}
	
	public enum HellGoatBioPhase { IDLE, SEARCHING_FOOD, EATING}
	public enum HellGoatMovement { FLYING, WALKING, NONE }
}


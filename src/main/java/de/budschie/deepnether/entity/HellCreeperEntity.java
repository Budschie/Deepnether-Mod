package de.budschie.deepnether.entity;

import de.budschie.deepnether.entity.goals.HellCreeperSpecialAIGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.CreeperSwellGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

public class HellCreeperEntity extends CreeperEntity
{
	public static final DataParameter<Integer> SPEED = EntityDataManager.createKey(HellCreeperEntity.class, DataSerializers.VARINT);
	
	public HellCreeperEntity(EntityType<? extends CreeperEntity> type, World worldIn)
	{
		super(type, worldIn);
	}
	
	@Override
	protected void registerData()
	{
		super.registerData();
		this.dataManager.register(SPEED, Integer.valueOf(1));
	}
	
	@Override
	public void readAdditional(CompoundNBT compound)
	{
		compound.putInt("speed", this.dataManager.get(SPEED));
		super.readAdditional(compound);
	}
	
	@Override
	public void writeAdditional(CompoundNBT compound)
	{
		this.dataManager.set(SPEED, compound.getInt("speed"));
		super.writeAdditional(compound);
	}
	
	@Override
	public double getAttributeValue(Attribute attribute)
	{
		return super.getAttributeValue(attribute);
	}
	
	@Override
	public float getAIMoveSpeed()
	{
		return this.dataManager.get(SPEED) * 0.15f + 0.45f;
	}
	
	@Override
	protected void registerGoals()
	{
	      this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
	      this.goalSelector.addGoal(2, new HellCreeperSpecialAIGoal(this));
	      this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.8D));
	      this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
	      this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
	      this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
	      this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
	}
	
	public void addSpeed(int amount)
	{
		this.dataManager.set(SPEED, this.dataManager.get(SPEED)+amount);
	}
	
	public int getSpeed()
	{
		return this.dataManager.get(SPEED);
	}
	
	public void resetSpeed()
	{
		this.dataManager.set(SPEED, 1);
	}
}

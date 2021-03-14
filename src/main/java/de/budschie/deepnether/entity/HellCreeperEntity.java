package de.budschie.deepnether.entity;

import java.util.HashMap;

import de.budschie.deepnether.entity.goals.HellCreeperSpecialAIGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;

public class HellCreeperEntity extends CreeperEntity
{
	public static final DataParameter<Integer> SPEED = EntityDataManager.createKey(HellCreeperEntity.class, DataSerializers.VARINT);
	
	public HellCreeperEntity(EntityType<? extends CreeperEntity> type, World worldIn)
	{
		super(type, worldIn);
	}
	
	/** Ahem... I definetly didn't stole this method from Curle... ehmmmm. **/
	public static AttributeModifierMap setupAttributes()
	{
		HashMap<Attribute, ModifiableAttributeInstance> attributeMap = new HashMap<>();
		
		// Lol i didn't knew that you can just put away the () things.
		attributeMap.put(Attributes.MAX_HEALTH, new ModifiableAttributeInstance(Attributes.MAX_HEALTH, inst -> inst.setBaseValue(20)));
		attributeMap.put(Attributes.MOVEMENT_SPEED, new ModifiableAttributeInstance(Attributes.MOVEMENT_SPEED, inst -> inst.setBaseValue(.05)));
		attributeMap.put(Attributes.ATTACK_DAMAGE, new ModifiableAttributeInstance(Attributes.ATTACK_DAMAGE, inst -> inst.setBaseValue(3)));
		attributeMap.put(Attributes.FOLLOW_RANGE, new ModifiableAttributeInstance(Attributes.FOLLOW_RANGE, inst -> inst.setBaseValue(20)));
		attributeMap.put(Attributes.ARMOR, new ModifiableAttributeInstance(Attributes.ARMOR, inst -> inst.setBaseValue(0)));
		attributeMap.put(Attributes.ARMOR_TOUGHNESS, new ModifiableAttributeInstance(Attributes.ARMOR_TOUGHNESS, inst -> inst.setBaseValue(0)));
		attributeMap.put(Attributes.KNOCKBACK_RESISTANCE, new ModifiableAttributeInstance(Attributes.KNOCKBACK_RESISTANCE, inst -> inst.setBaseValue(0)));
		attributeMap.put(ForgeMod.ENTITY_GRAVITY.get(), new ModifiableAttributeInstance(ForgeMod.ENTITY_GRAVITY.get(), inst -> inst.setBaseValue(1)));
        
		AttributeModifierMap modMap = new AttributeModifierMap(attributeMap);
		return modMap;
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

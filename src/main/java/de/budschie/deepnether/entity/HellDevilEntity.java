package de.budschie.deepnether.entity;

import java.util.Random;

import de.budschie.deepnether.entity.goals.HellDevilGoal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class HellDevilEntity extends CreatureEntity
{
	
	public static final DataParameter<Boolean> CHALLENGED = EntityDataManager.createKey(HellDevilEntity.class, DataSerializers.BOOLEAN);
	public static final DataParameter<Boolean> ATTACKING = EntityDataManager.createKey(HellDevilEntity.class, DataSerializers.BOOLEAN);
	public static final DataParameter<String> CHALLENGED_PLAYER = EntityDataManager.createKey(HellDevilEntity.class, DataSerializers.STRING);

	protected HellDevilEntity(EntityType<? extends CreatureEntity> type, World worldIn)
	{
		super(type, worldIn);
	}
	
	public boolean isChallenged()
	{
		return this.dataManager.get(CHALLENGED);
	}
	
	public void setChallenged(boolean value, String playerName)
	{
		this.dataManager.set(CHALLENGED, value);
		this.dataManager.set(CHALLENGED_PLAYER, playerName);
	}
	
	@Override
	protected void registerData()
	{
		super.registerData();
		this.dataManager.register(CHALLENGED, false);
		this.dataManager.register(CHALLENGED_PLAYER, "");
		this.dataManager.register(ATTACKING, false);
	}
	
	@Override
	public void writeAdditional(CompoundNBT compound)
	{
		super.writeAdditional(compound);
		compound.putBoolean("challenged", this.dataManager.get(CHALLENGED));
		compound.putString("challengedBy", this.dataManager.get(CHALLENGED_PLAYER));
	}
	
	@Override
	public void readAdditional(CompoundNBT compound)
	{
		super.readAdditional(compound);
		this.dataManager.set(CHALLENGED, compound.getBoolean("challenged"));
		this.dataManager.set(CHALLENGED_PLAYER, compound.getString("challengedBy"));
	}
	
	@Override
	public float getAIMoveSpeed()
	{
		return 0.25f;
	}
	
	@Override
	protected void registerGoals()
	{
		this.goalSelector.addGoal(1, new HellDevilGoal(this));
		// this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 0.45D, false));
	    this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.35D));
	    this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 18.0F));
	    this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
		
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false));
	}
	
	@Override
	protected void registerAttributes()
	{
		super.registerAttributes();
		this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5);
		this.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1);
		this.getAttribute(SharedMonsterAttributes.ATTACK_KNOCKBACK).setBaseValue(2);
		this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_SPEED).setBaseValue(5);
		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100);
	}
	
	@Override
	public ActionResultType applyPlayerInteraction(PlayerEntity player, Vec3d vec, Hand hand)
	{
		ActionResultType result = (hand == Hand.MAIN_HAND && !this.dataManager.get(CHALLENGED)) ? ActionResultType.SUCCESS : ActionResultType.FAIL;
		if(player.getEntityWorld().isRemote)
			return result;
		
		if(!this.dataManager.get(CHALLENGED))
		{
			player.sendMessage(new StringTextComponent(TextFormatting.RED+"Do you really want that I kill you?"));
			
			this.dataManager.set(CHALLENGED, true);
			this.dataManager.set(CHALLENGED_PLAYER, player.getGameProfile().getName());
		}
		return result;
	}
	
	public void setIsAttacking()
	{
		this.dataManager.set(ATTACKING, true);
	}
	
	@Override
	public void tick()
	{
		super.tick();
    	if(!onGround)
    	{
    		//this.motionY *= fallSpeedMulti;
    		this.setMotion(this.getMotion().x, this.getMotion().y * 0.2, this.getMotion().z);
    	}
	}
	
	public void resetChallenged()
	{
		this.dataManager.set(CHALLENGED, false);
		this.dataManager.set(CHALLENGED_PLAYER, "");
	}
	
	@Override
	public void onKillEntity(LivingEntity entityLivingIn)
	{
		this.resetAttacking();
		this.resetChallenged();
		super.onKillEntity(entityLivingIn);
	}
	
	@Override
	public void baseTick()
	{
		super.baseTick();
		if(world.isRemote)
		{
			if(this.dataManager.get(ATTACKING))
			{
				PlayerEntity pEntity = null;
				
				playerLoop:
				for(PlayerEntity player : Minecraft.getInstance().world.getPlayers())
				{
					if(player.getGameProfile().getName().equals(this.dataManager.get(CHALLENGED_PLAYER)))
					{
						pEntity = player;
						break playerLoop;
					}
				}
				
				if(pEntity == null)
				{
					return;
				}
				
				Vec3d vecEntity = this.getPositionVector().add(0, 1.0, 0);
				Vec3d vecPlayer = pEntity.getPositionVector();
				Vec3d vecSpeed = vecPlayer.subtract(vecEntity);
				
				for(int i = 0; i < 50; i++)
				{
					for(int j = 0; j < 3; j++)
					{
						world.addParticle(ParticleTypes.FLAME, vecEntity.x + new Random().nextFloat() - .5, vecEntity.y + (j * 0.1), vecEntity.z + new Random().nextFloat() - .5, vecSpeed.x / 10, vecSpeed.y / 10 + (j * 0.01) + 0.1, vecSpeed.z / 10);
					}
					//Minecraft.getMinecraft().world.spawnParticle(EnumParticleTypes.FLAME, vecEntity.x + new Random().nextFloat() - .5, vecEntity.y, vecEntity.z + new Random().nextFloat() - .5, vecSpeed.x / 10, vecSpeed.y / 10, vecSpeed.z / 10, new int[] {5});
				}
			}
		}
	}
	
	public void resetAttacking()
	{
		this.dataManager.set(ATTACKING, false);
	}
}

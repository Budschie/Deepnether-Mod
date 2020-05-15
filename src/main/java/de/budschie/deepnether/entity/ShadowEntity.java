package de.budschie.deepnether.entity;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.world.BlockEvent.EntityPlaceEvent;

public class ShadowEntity extends MonsterEntity
{
	protected ShadowEntity(EntityType<? extends MonsterEntity> type, World worldIn)
	{
		super(type, worldIn);
	}

	private static final DataParameter<Byte> TYPE = EntityDataManager.<Byte>createKey(ShadowEntity.class, DataSerializers.BYTE);
	private static final DataParameter<Boolean> IS_CHILD = EntityDataManager.<Boolean>createKey(ShadowEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> SLOWNESS_STRENGTH = EntityDataManager.<Integer>createKey(ShadowEntity.class, DataSerializers.VARINT);
	private static final DataParameter<Boolean> IS_HOAX = EntityDataManager.<Boolean>createKey(ShadowEntity.class, DataSerializers.BOOLEAN);

 
	private float fallSpeedMulti = 0.2f;
	
	public void setFallSpeed(float speed)
	{
		fallSpeedMulti = speed;
	}
	
	public void setShadowType(ShadowType type)
	{
		this.dataManager.set(TYPE, Byte.valueOf((byte)type.ordinal()));
	}
	
	public ShadowType getShadowType()
	{
		//System.out.println("Shadow type: " + ShadowType.values()[getDataManager().get(TYPE)] + " with index " + getDataManager().get(TYPE) + ".");
		return ShadowType.values()[this.dataManager.get(TYPE)];
	}
	
	public boolean isChild()
	{
		return this.dataManager.get(IS_CHILD);
	}
	
	public float getTransparency()
	{		
		return (float) ((Math.sin(ticksExisted/50.0)+1)/12+0.15);
	}
	
	public boolean isHoax()
	{
		return this.dataManager.get(IS_HOAX);
	}
	
	@Override
	public boolean attackEntityAsMob(Entity entityIn) 
	{
		if(isHoax())
			return false;
		
		boolean toReturn = super.attackEntityAsMob(entityIn);
		
		if(entityIn instanceof PlayerEntity && !this.world.isRemote && entityIn == this.getAttackTarget())
		{
			this.dataManager.set(SLOWNESS_STRENGTH, this.dataManager.get(SLOWNESS_STRENGTH)+1);
		}
		
		return toReturn;
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) 
	{
		if(isHoax())
		{
			if(source.getDamageType().equals("player"))
			{
				if(this.getEntityWorld() instanceof ServerWorld)
				{
					ServerWorld server = (ServerWorld) world;
					
					this.onDeath(DamageSource.causePlayerDamage((PlayerEntity) source.getTrueSource()));
					//server.spawnParticle(null, EnumParticleTypes.CLOUD, 20, posX, posY, posZ, 100, 0.1, 0.1, 0.1, 0.1, 0);
					server.spawnParticle(ParticleTypes.CLOUD, getPosX(), getPosY(), getPosZ(), 100, 0.1, 0.1, 0.1, 0.1);
					return true;
				}
			}
			else
				return false;
		}
		else
		{
			if(source.getTrueSource() != null && source.getTrueSource() instanceof PlayerEntity && (source.getTrueSource() == this.getAttackTarget() || ((PlayerEntity)source.getTrueSource()).isCreative()))
			{
				if(!((PlayerEntity)source.getTrueSource()).isCreative())
					this.dataManager.set(SLOWNESS_STRENGTH, this.dataManager.get(SLOWNESS_STRENGTH)+1);
			}
			else
				return false;
		}
		
		return super.attackEntityFrom(source, amount);
	}
	
	@Override
	public void tick() 
	{
		if(this.getEntityWorld().isRemote)
		{
			for(int i = 0; i < 8; i++)
			{
				world.addParticle(new Random().nextInt(4) == 0 ? ParticleTypes.LARGE_SMOKE : ParticleTypes.SMOKE, getPosX() + new Random().nextDouble()-0.5, getPosY(), getPosZ() + new Random().nextDouble()-0.5, 0, 0.002, 0);
			}
		}
		else
		{
			if(this.getAttackTarget() != null && this.getAttackTarget() instanceof PlayerEntity)
			{
				this.getAttackTarget().addPotionEffect(new EffectInstance(Effects.SLOWNESS, 20, this.dataManager.get(SLOWNESS_STRENGTH)+2, true, false));
			}
			else
			{
				this.dataManager.set(SLOWNESS_STRENGTH, 0);
			}
			
			if(this.getLastDamageSource() != null)
			{
				if(this.getLastDamageSource().getTrueSource() != null && this.getLastDamageSource().getTrueSource() instanceof PlayerEntity && !((PlayerEntity)this.getLastDamageSource().getTrueSource()).isCreative())
				{
					/*
					if(world.isRemote)
					{
						for(int i = 0; i < 80; i++)
						{
							world.addParticle(new Random().nextInt(4) == 0 ? ParticleTypes.LARGE_SMOKE : ParticleTypes.SMOKE, this.getLastDamageSource().getTrueSource().getPosX() + new Random().nextDouble()-0.5, this.getLastDamageSource().getTrueSource().getPosY(), this.getLastDamageSource().getTrueSource().getPosZ() + new Random().nextDouble()-0.5, 0, 0.002, 0);
						}
					}
					*/
					
					((ServerPlayerEntity)this.getLastDamageSource().getTrueSource()).addPotionEffect(new EffectInstance(Effects.BLINDNESS, 30, 10, true, false));
				
					ServerWorld worldServer = (ServerWorld) world;
					
					worldServer.spawnParticle(ParticleTypes.LARGE_SMOKE, this.getLastDamageSource().getTrueSource().getPosX(), this.getLastDamageSource().getTrueSource().getPosY(), this.getLastDamageSource().getTrueSource().getPosZ(), 10, 0.1, 0.2, 0.1, 0.1);
					worldServer.spawnParticle(ParticleTypes.SMOKE, this.getLastDamageSource().getTrueSource().getPosX(), this.getLastDamageSource().getTrueSource().getPosY(), this.getLastDamageSource().getTrueSource().getPosZ(), 27, 0.1, 0.2, 0.1, 0.1);
				}
			}
			
		}
		
		super.tick();
	}
	
	
    @Override
    public void livingTick() 
    {
    	if(!onGround && this.getMotion().y < 0)
    	{
    		//this.motionY *= fallSpeedMulti;
    		this.setMotion(this.getMotion().x, this.getMotion().y * fallSpeedMulti, this.getMotion().z);
    	}
    	
    	//if(this.extinguish();)s
    	
    	super.livingTick();
    }
	
	@Override
	public void writeAdditional(CompoundNBT compound) 
	{
		compound.putByte("type", (byte) this.dataManager.get(TYPE));
		compound.putBoolean("child", this.dataManager.get(IS_CHILD));
		compound.putInt("slownessStrength", this.dataManager.get(SLOWNESS_STRENGTH));
		compound.putBoolean("hoax", this.dataManager.get(IS_HOAX));
		
		super.writeAdditional(compound);
	}
	
	@Override
	public void readAdditional(CompoundNBT compound) 
	{
		byte value = compound.getByte("type");
		setShadowType(ShadowType.values()[value]);
		
		this.dataManager.set(IS_CHILD, compound.getBoolean("child"));
		
		this.dataManager.set(SLOWNESS_STRENGTH, compound.getInt("slownessStrength"));
		
		this.dataManager.set(IS_HOAX, compound.getBoolean("hoax"));
		
		super.readAdditional(compound);
	}
	
	@Override
	protected void registerGoals() 
	{
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 0.35F, false));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomWalkingGoal(this, 0.35D));
        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
        
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<PlayerEntity>(this, PlayerEntity.class, true));
	}
	
	@Override
	protected void registerData() 
	{
		super.registerData();
		
		this.dataManager.register(TYPE, Byte.valueOf((byte)new Random().nextInt(ShadowType.values().length)));
		this.dataManager.register(IS_CHILD, new Random().nextInt(5) == 0);
		this.dataManager.register(SLOWNESS_STRENGTH, Integer.valueOf(0));
		this.dataManager.register(IS_HOAX, new Random().nextInt(100) == 0);
	}
}

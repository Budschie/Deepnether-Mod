package de.budschie.deepnether.entity;

import java.util.Random;

import net.minecraft.block.Blocks;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.server.ServerWorld;

public class ShadowTrapEntity extends CreatureEntity
{
	
	protected ShadowTrapEntity(EntityType<? extends CreatureEntity> type, World worldIn)
	{
		super(type, worldIn);
		
	}

	@Override
	public AxisAlignedBB getBoundingBox()
	{
		return super.getBoundingBox();
	}

	@Override
	protected void registerData() 
	{
		super.registerData();
		this.setNoGravity(true);
	}
	
	public static final AxisAlignedBB SEARCHING_RANGE = new AxisAlignedBB(-12, -3, -12, 12, 3, 12);
	
	
	
	@Override
	public void tick() 
	{        
		if(!world.isRemote)
		{	        
	        Random rand = new Random((long) (ticksExisted + getPosX() + getPosZ()));
	        
			if(rand.nextInt(100) == 0)
			{
				loopPlayer:
				for(PlayerEntity player : world.getPlayers())
				{
					if(player.abilities.disableDamage)
						continue loopPlayer;
					if(SEARCHING_RANGE.offset(player.getPosition()).contains(this.getPositionVec()))
					{						
						ServerWorld worldServer = (ServerWorld) world;
						
						if(rand.nextBoolean())
						{
							//worldServer.playSound(SoundEvents.AMBIENT_CAVE, 4.0f, new Random().nextFloat()-0.5f*2f);
							worldServer.playSound(null, player.getPosition(), SoundEvents.AMBIENT_CAVE, SoundCategory.AMBIENT, 4.0f, (new Random().nextFloat()-0.5f)*2f);
						}
						
						worldServer.spawnParticle(ParticleTypes.LARGE_SMOKE, player.getPosX(), player.getPosY(), player.getPosZ(), 50, 0.1, 0.2, 0.1, 0.1);
						worldServer.spawnParticle(ParticleTypes.SMOKE, player.getPosX(), player.getPosY(), player.getPosZ(), 130, 0.1, 0.2, 0.1, 0.1);
						
						player.addPotionEffect(new EffectInstance(Effects.BLINDNESS, 20, 10, true, false));
						
						int shadowAmount = rand.nextInt(2)+1;
						
						for(int i = 0; i < shadowAmount; i++)
						{
							//ShadowEntity shadow = new ShadowEntity(worldServer);
							ShadowEntity shadow = new ShadowEntity(EntityInit.SHADOW, worldServer);
							shadow.setPosition(getPosX() + rand.nextDouble() - 0.5, getPosY(), getPosZ() + rand.nextDouble() - 0.5);
							worldServer.addEntity(shadow);
						}
						
						this.onDeath(DamageSource.GENERIC);
						return;
					}
				}
			}
		}
		
        this.firstUpdate = false;
	}
		
	@Override
	public void baseTick() 
	{
		if(world.isRemote)
			return;
			
		if(world.getBlockState(getPosition()) != Blocks.AIR.getDefaultState())
		{
			this.dead = true;
		}
		else		
			this.baseTick();
		
		if(this.ticksExisted > 500)
			this.dead = true;
	}
}

package de.budschie.deepnether.entity;

import java.util.Random;

import de.budschie.deepnether.main.References;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityType.IFactory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber(bus = Bus.MOD)
public class EntityInit
{
	private static boolean hasAlreadyRegistered = false;
	
	public static final String HELL_CREEPER_ID = "hell_creeper";
	public static final String HELL_DEVIL_ID = "hell_devil";
	public static final String SHADOW_TRAP_ID = "shadow_trap";
	public static final String SHADOW_ID = "shadow";
	
	public static EntityType<HellCreeperEntity> HELL_CREEPER = null;
	public static EntityType<HellDevilEntity> HELL_DEVIL = null;
	public static EntityType<ShadowTrapEntity> SHADOW_TRAP = null;
	public static EntityType<ShadowEntity> SHADOW = null;
	
	public static void createEntityTypes()
	{
		if(hasAlreadyRegistered)
			throw new IllegalStateException("You mustn't create the entity types more than one time!");
		
		hasAlreadyRegistered = true;
		
		HELL_CREEPER = EntityType.Builder.create(new IFactory<HellCreeperEntity>()
		{
			@Override
			public HellCreeperEntity create(EntityType<HellCreeperEntity> type, World world)
			{
				return new HellCreeperEntity(type, world);
			}
		}, 
		EntityClassification.MONSTER)
				.setTrackingRange(20)
				.immuneToFire()
				.build(HELL_CREEPER_ID);
		
		HELL_CREEPER.setRegistryName(new ResourceLocation(References.MODID, HELL_CREEPER_ID));
		
		HELL_DEVIL = EntityType.Builder.create(new IFactory<HellDevilEntity>()
		{
			@Override
			public HellDevilEntity create(EntityType<HellDevilEntity> p_create_1_, World p_create_2_)
			{
				return new HellDevilEntity(p_create_1_, p_create_2_);
			}
		}, EntityClassification.AMBIENT)
				.setTrackingRange(30)
				.immuneToFire()
				.build(HELL_DEVIL_ID);		
		
		HELL_DEVIL.setRegistryName(new ResourceLocation(References.MODID, HELL_DEVIL_ID));
		
		SHADOW_TRAP = EntityType.Builder.create(new IFactory<ShadowTrapEntity>()
		{
			@Override
			public ShadowTrapEntity create(EntityType<ShadowTrapEntity> p_create_1_, World p_create_2_)
			{
				return new ShadowTrapEntity(p_create_1_, p_create_2_);
			}
		}, EntityClassification.MONSTER)
				.setTrackingRange(25)
				.immuneToFire()
				.size(0.0f, 0.0f)
				.build(SHADOW_TRAP_ID);
		
		SHADOW_TRAP.setRegistryName(new ResourceLocation(References.MODID, SHADOW_TRAP_ID));
		
		SHADOW = EntityType.Builder.create(new IFactory<ShadowEntity>()
		{
			@Override
			public ShadowEntity create(EntityType<ShadowEntity> p_create_1_, World p_create_2_)
			{
				return new ShadowEntity(p_create_1_, p_create_2_);
			}
		}, EntityClassification.MONSTER)
				.setTrackingRange(25)
				.immuneToFire()
				.size(0.75f, 2.0f)
				.build(SHADOW_ID);
		
		SHADOW.setRegistryName(new ResourceLocation(References.MODID, SHADOW_ID));
	}
	
	@SubscribeEvent
	public static void onRegisterEntities(RegistryEvent.Register<EntityType<?>> event)
	{
		IForgeRegistry<EntityType<?>> registry = event.getRegistry();
		
		registry.register(HELL_CREEPER);
		registry.register(HELL_DEVIL);
		registry.register(SHADOW);
		registry.register(SHADOW_TRAP);
	}
}

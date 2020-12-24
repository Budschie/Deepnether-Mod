package de.budschie.deepnether.biomes;

import java.util.function.Consumer;

import de.budschie.deepnether.main.References;
import de.budschie.deepnether.util.Util.RGBA;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SmokeParticle;
import net.minecraft.particles.IParticleData;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.ParticleEffectAmbience;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.GenericEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class DeepnetherBiomeRegistry
{
	public static final DeferredRegister<Biome> BIOME_REGISTRY = DeferredRegister.create(ForgeRegistries.BIOMES, References.MODID);
	
	public static final RegistryObject<Biome> TEST_ENTRY = BIOME_REGISTRY.register("deepnether", () -> new Biome.Builder().downfall(0).precipitation(RainType.NONE)
			.temperature(50).category(Category.NETHER).depth(0).scale(0)
			.withGenerationSettings(BiomeGenerationSettings.DEFAULT_SETTINGS).withMobSpawnSettings(new MobSpawnInfo.Builder().copy())
			.setEffects(new BiomeAmbience.Builder().setFogColor(0).setWaterColor(0).setWaterFogColor(0).withSkyColor(0).build())
			.build());
	
	public static final RegistryObject<Biome> GREEN_FOREST_BIOME = BIOME_REGISTRY.register("green_forest_biome", () -> new Biome.Builder().downfall(0).precipitation(RainType.NONE).category(Category.NETHER)
			.depth(0).scale(0).category(Category.FOREST).temperature(50)
			.withGenerationSettings(BiomeGenerationSettings.DEFAULT_SETTINGS).withMobSpawnSettings(new MobSpawnInfo.Builder().copy())
			.setEffects(new BiomeAmbience.Builder().setFogColor(0).setWaterColor(0).setWaterFogColor(0).withSkyColor(0).build())
			.build());

}

package de.budschie.deepnether.biomes;

import de.budschie.deepnether.main.References;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;
import net.minecraft.world.biome.Biome.TemperatureModifier;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BiomeRegistry
{
	public static final DeferredRegister<Biome> REGISTRY = DeferredRegister.create(ForgeRegistries.BIOMES, References.MODID);
	
	public static final RegistryObject<Biome> GREEN_FOREST_BIOME = REGISTRY.register("green_forest_biome", () -> new Biome.Builder().category(Category.FOREST)
			.depth(1).scale(1).downfall(1).precipitation(RainType.RAIN).temperature(30).withGenerationSettings(BiomeGenerationSettings.DEFAULT_SETTINGS)
			.withMobSpawnSettings(new MobSpawnInfo.Builder().copy()).withTemperatureModifier(TemperatureModifier.NONE)
			.setEffects(new BiomeAmbience.Builder().setFogColor(0).setWaterColor(0).setWaterFogColor(0).withSkyColor(0).build()).build());
}

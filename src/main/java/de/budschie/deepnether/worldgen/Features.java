package de.budschie.deepnether.worldgen;

import de.budschie.deepnether.main.References;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Features
{
	public static final DeferredRegister<Feature<?>> FEATURES = new DeferredRegister<>(ForgeRegistries.FEATURES, References.MODID);
	
	//public static final ConfiguredFeature<IFeatureConfig, ?> CRYSTALS_FEATURE = new CrystalsWorldGen(NoFeatureConfig::deserialize).withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
	//public static final ConfiguredFeature<IFeatureConfig, ?> DEEPNETHER_PORTAL_FEATURE = new Deepnether(NoFeatureConfig::deserialize).withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
	
	public static final RegistryObject<TestFeature> TEST = FEATURES.register("test", () -> new TestFeature(NoFeatureConfig::deserialize));
	public static final RegistryObject<CrystalsWorldGen> CRYSTALS_FEATURE = FEATURES.register("crystals", () -> new CrystalsWorldGen(NoFeatureConfig::deserialize));
}

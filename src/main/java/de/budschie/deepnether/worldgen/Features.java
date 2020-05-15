package de.budschie.deepnether.worldgen;

import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class Features
{
	public static final ConfiguredFeature<IFeatureConfig, ?> CRYSTALS_FEATURE = new CrystalsWorldGen(NoFeatureConfig::deserialize).withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
	//public static final ConfiguredFeature<IFeatureConfig, ?> DEEPNETHER_PORTAL_FEATURE = new Deepnether(NoFeatureConfig::deserialize).withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
	public static final ConfiguredFeature<IFeatureConfig, ?> DEEPNETHER_TEST = new TestFeature(NoFeatureConfig::deserialize).withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
}

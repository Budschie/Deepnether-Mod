package de.budschie.deepnether.worldgen;

import de.budschie.deepnether.main.References;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.storage.DerivedWorldInfo;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public class Features
{
	public static final DeferredRegister<Feature<?>> FEATURES = new DeferredRegister<>(ForgeRegistries.FEATURES, References.MODID);
	
	//public static final ConfiguredFeature<IFeatureConfig, ?> CRYSTALS_FEATURE = new CrystalsWorldGen(NoFeatureConfig::deserialize).withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
	//public static final ConfiguredFeature<IFeatureConfig, ?> DEEPNETHER_PORTAL_FEATURE = new Deepnether(NoFeatureConfig::deserialize).withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
	
	public static final RegistryObject<TestFeature> TEST = FEATURES.register("test", () -> new TestFeature(NoFeatureConfig::deserialize));
	public static final RegistryObject<CrystalsWorldGen> CRYSTALS_FEATURE = FEATURES.register("crystals", () -> new CrystalsWorldGen(NoFeatureConfig::deserialize));
	public static final RegistryObject<BigTreeFeature> BIG_TREE_FEATURE = FEATURES.register("bigtree", () -> new BigTreeFeature(NoFeatureConfig::deserialize));
		
	public static final DeferredRegister<Placement<?>> PLACEMENTS = new DeferredRegister<>(ForgeRegistries.DECORATORS, References.MODID);
	public static final RegistryObject<BigTreePlacement> BIG_TREE_PLACEMENT = PLACEMENTS.register("big_tree", () -> new BigTreePlacement(NoPlacementConfig::deserialize));
}

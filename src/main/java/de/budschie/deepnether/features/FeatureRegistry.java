package de.budschie.deepnether.features;

import de.budschie.deepnether.main.References;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FeatureRegistry
{
	public static final DeferredRegister<Feature<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.FEATURES, References.MODID);
	public static final RegistryObject<WitheredTreeFeature> WITHERED_TREE_FEATURE = REGISTRY.register("withered_tree_feature", () -> new WitheredTreeFeature());
}
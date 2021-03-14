package de.budschie.deepnether.features;

import de.budschie.deepnether.block.BlockInit;
import de.budschie.deepnether.dimension.HeightmapChannels;
import de.budschie.deepnether.features.placements.PlacementRegistry;
import de.budschie.deepnether.features.placements.ScatteredPlacementConfig;
import de.budschie.deepnether.main.References;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class ConfiguredFeatureRegistry
{
	public static void registerConfiguredFeatures()
	{
        Registry<ConfiguredFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_FEATURE;
        
        Registry.register(registry, new ResourceLocation(References.MODID, "withered_tree_soul_desert_conf_feature"), FeatureRegistry.WITHERED_TREE_FEATURE.get()
        		.withConfiguration(new WitheredTreeFeatureConfig(
        				new SimpleBlockStateProvider(BlockInit.WITHERED_TREE_LOG.get().getDefaultState().with(RotatedPillarBlock.AXIS, Direction.Axis.Y)), 
        				new SimpleBlockStateProvider(BlockInit.WITHERED_TREE_LOG.get().getDefaultState().with(RotatedPillarBlock.AXIS, Direction.Axis.X)), 
        				new SimpleBlockStateProvider(BlockInit.WITHERED_TREE_LOG.get().getDefaultState().with(RotatedPillarBlock.AXIS, Direction.Axis.Z))))
        		.withPlacement(PlacementRegistry.SCATTERED_HEIGHTMAP_PLACEMENT.get().configure(new ScatteredPlacementConfig(HeightmapChannels.SOUL_DESERT_ISLANDS, 2))));
    }
}

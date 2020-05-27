package de.budschie.deepnether.biomes;

import de.budschie.deepnether.block.BlockInit;
import de.budschie.deepnether.worldgen.Features;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class BiomeFeatureAdder
{
	public static void addFeatures()
	{
		for(Biome biome : ForgeRegistries.BIOMES.getValues())
		{
			if(biome instanceof DeepnetherBiomeBase)
			{
				ConfiguredPlacement<CountRangeConfig> placement = Placement.COUNT_RANGE.configure(new CountRangeConfig(10, 0, 0, 140));
				ConfiguredPlacement<ChanceConfig> placement2 = Placement.DUNGEONS.configure(new ChanceConfig(1));

				biome.addFeature(Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(DeepnetherBiomeBase.COMPRESSED_NETHERRACK, BlockInit.AMYLITHE_ORE.getDefaultState(), 6)).withPlacement(placement));
				// biome.addFeature(Decoration.SURFACE_STRUCTURES, Features.TEST.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(placement2));
			}
		}
		
		ConfiguredPlacement<CountRangeConfig> placementDylithite = Placement.COUNT_RANGE.configure(new CountRangeConfig(7, 50, 0, 140));
		BiomeRegistry.FLOATING_ISLANDS_BIOME.addFeature(Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(DeepnetherBiomeBase.COMPRESSED_NETHERRACK, BlockInit.DYLITHITE_ORE.getDefaultState(), 4)).withPlacement(placementDylithite));
		BiomeRegistry.CRYSTAL_CAVE_BIOME.addFeature(Decoration.VEGETAL_DECORATION, Features.CRYSTALS_FEATURE.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
		BiomeRegistry.FLOATING_ISLANDS_BIOME.addFeature(Decoration.SURFACE_STRUCTURES, Features.BIG_TREE_FEATURE.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Features.BIG_TREE_PLACEMENT.get().configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
	}
}

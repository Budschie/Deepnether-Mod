package de.budschie.deepnether.features;

import de.budschie.deepnether.block.BlockInit;
import de.budschie.deepnether.features.tree_generation.BasicTreeGenerator;
import de.budschie.deepnether.features.tree_generation.TreeTrunkSet;
import de.budschie.deepnether.features.tree_generation.branches.WitheredTreeBranch;
import de.budschie.deepnether.features.tree_generation.trunks.GreenForestTrunk;
import de.budschie.deepnether.features.tree_generation.trunks.WitheredTreeTrunks;
import de.budschie.deepnether.main.References;
import de.budschie.deepnether.util.LazyProvider;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.util.Direction;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FeatureRegistry
{
	public static final DeferredRegister<Feature<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.FEATURES, References.MODID);
	
	private static final LazyProvider<TreeTrunkSet> WITHERED_TREE_TRUNK_SET = new LazyProvider<>(() -> new TreeTrunkSet(BlockInit.WITHERED_TREE_LOG.get().getDefaultState().with(RotatedPillarBlock.AXIS, Direction.Axis.Y), 
			BlockInit.WITHERED_TREE_LOG.get().getDefaultState().with(RotatedPillarBlock.AXIS, Direction.Axis.X), 
			BlockInit.WITHERED_TREE_LOG.get().getDefaultState().with(RotatedPillarBlock.AXIS, Direction.Axis.Z)));
	
	private static final LazyProvider<TreeTrunkSet> SOUL_INFUSED_WITHERED_TREE_TRUNK_SET = new LazyProvider<TreeTrunkSet>(() -> new TreeTrunkSet(BlockInit.SOUL_INFUSED_WITHERED_TREE_LOG.get().getDefaultState().with(RotatedPillarBlock.AXIS, Direction.Axis.Y), 
			BlockInit.SOUL_INFUSED_WITHERED_TREE_LOG.get().getDefaultState().with(RotatedPillarBlock.AXIS, Direction.Axis.X), 
			BlockInit.SOUL_INFUSED_WITHERED_TREE_LOG.get().getDefaultState().with(RotatedPillarBlock.AXIS, Direction.Axis.Z)));

	
	public static final RegistryObject<BasicTreeGenerator> WITHERED_TREE_FEATURE = REGISTRY.register("withered_tree_feature", () -> new BasicTreeGenerator.BasicTreeGeneratorBuilder()
			.setMainTreePart("trunk", new GreenForestTrunk(WITHERED_TREE_TRUNK_SET.get(), .5f, 3))
			.addTreePart("branch", new WitheredTreeBranch(WITHERED_TREE_TRUNK_SET.get())).build());
	
	public static final RegistryObject<BasicTreeGenerator> SOUL_INFUSED_WITHERED_TREE_FEATURE = REGISTRY.register("soul_infused_withered_tree_feature", () -> new BasicTreeGenerator.BasicTreeGeneratorBuilder()
			.setMainTreePart("trunk", new WitheredTreeTrunks(SOUL_INFUSED_WITHERED_TREE_TRUNK_SET.get()))
			.addTreePart("branch", new WitheredTreeBranch(SOUL_INFUSED_WITHERED_TREE_TRUNK_SET.get())).build());

	//public static final RegistryObject<BasicTreeFeature<GreenForestTreeFeatureConfig>> GREEN_FOREST_TREE_FEATURE = REGISTRY.register("green_forest_tree_feature", () -> 
	//new BasicTreeFeature(GreenForestTreeFeatureConfig.CODEC, new GreenForestTrunkPlacer(), new GreenForestLeavesPlacer(), null));
}
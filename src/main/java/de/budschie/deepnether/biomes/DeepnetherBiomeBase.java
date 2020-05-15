package de.budschie.deepnether.biomes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import de.budschie.deepnether.block.BlockInit;
import de.budschie.deepnether.worldgen.Features;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockMatcher;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig.FillerBlockType;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.common.IExtensibleEnum;

public class DeepnetherBiomeBase extends Biome
{
	public FillerBlockType COMPRESSED_NETHERRACK = FillerBlockType.create("COMPRESSED_NETHERRACK", "deepnether:compressed_netherrack", new BlockMatcher(BlockInit.COMPRESSED_NETHERRACK));

	public static final ArrayList<SpawnListEntry> EMPTY = new ArrayList<>();
	
	public DeepnetherBiomeBase(Builder builder) 
	{
		super(builder.depth(0.1F).scale(0.2F).temperature(2.0F).downfall(0.0F).waterColor(4159204).waterFogColor(329011).surfaceBuilder(SurfaceBuilder.NETHER, SurfaceBuilder.NETHERRACK_CONFIG));
		
		ConfiguredPlacement<CountRangeConfig> placement = Placement.COUNT_RANGE.configure(new CountRangeConfig(10, 0, 0, 140));
		this.addFeature(Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(COMPRESSED_NETHERRACK, BlockInit.AMYLITHE_ORE.getDefaultState(), 6)).withPlacement(placement));
		this.addFeature(Decoration.SURFACE_STRUCTURES, Features.DEEPNETHER_TEST.withPlacement(Placement.DUNGEONS.configure(new ChanceConfig(25))));
	}
	
	public int getBaseHeightMap()
	{
		
		return 10;
	}
	
	public BlockState getLavaBlock()
	{
		return Blocks.LAVA.getDefaultState();
	}
	
	public BlockState getTopBlock()
	{
		return BlockInit.COMPRESSED_NETHERRACK.getDefaultState();
	}
	
	public boolean hasParticles()
	{
		return false;
	}
	
	public void summonParticleAt(World world, int x, int y, int z)
	{
		for(int i = 0; i < 200; i++)
		{
			world.addParticle(ParticleTypes.PORTAL, x + new Random().nextInt(150) - 80, y + new Random().nextInt(150) - 80, z + new Random().nextInt(150) - 80, (new Random().nextDouble()-0.5)*4, (new Random().nextDouble()-0.5)*4, (new Random().nextDouble()-0.5)*4);
		}
		//System.out.println("KFJDSLJKLFSDKJLFDSkJLFSDLKJÖFDSKJLÖSFD");
	}
	
	public Vec3d getFogColor()
	{
		return new Vec3d(0.5, 0.2, 0.2);
	}
	
	public void populate(World worldIn, int x, int z)
	{
		
	}
	
	@Override
	public ITextComponent getDisplayName()
	{
		return new StringTextComponent(this.getRegistryName().toString());
	}
}

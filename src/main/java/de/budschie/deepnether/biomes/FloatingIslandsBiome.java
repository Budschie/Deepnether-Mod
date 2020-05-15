package de.budschie.deepnether.biomes;

import java.util.Random;

import de.budschie.deepnether.block.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.pattern.BlockMatcher;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig.FillerBlockType;
import net.minecraft.world.gen.feature.structure.OceanMonumentStructure;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;

public class FloatingIslandsBiome extends DeepnetherBiomeBase
{
	public static class FloatingIslandsBiomeBuilder extends Builder
	{
		public FloatingIslandsBiomeBuilder()
		{
			this.precipitation(RainType.NONE).category(Category.NETHER).temperature(100);
		}
	}
	
	public FloatingIslandsBiome(Builder builder)
	{
		super(builder);
		ConfiguredPlacement<CountRangeConfig> placementDylithite = Placement.COUNT_RANGE.configure(new CountRangeConfig(7, 50, 0, 140));
		this.addFeature(Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(COMPRESSED_NETHERRACK, BlockInit.DYLITHITE_ORE.getDefaultState(), 4)).withPlacement(placementDylithite));
	}
	
	@Override
	public int getBaseHeightMap() {
		// TODO Auto-generated method stub
		return 35;
	}
	
	@Override
	public BlockState getLavaBlock() {
		// TODO Auto-generated method stub
		return BlockInit.HOT_STONE.getDefaultState();
	}
	
	@Override
	public BlockState getTopBlock() {
		// TODO Auto-generated method stub
		return BlockInit.SOUL_DUST.getDefaultState();
	}
	
	@Override
	public void summonParticleAt(World world, int x, int y, int z)
	{
		//System.out.println("OTHER PARTICLES");
		for(int i = 0; i < 200; i++)
		{
			world.addParticle(ParticleTypes.SMOKE, x + new Random().nextInt(300) - 150, y + new Random().nextInt(300) - 150, z + new Random().nextInt(300) - 150, new Random().nextDouble()/5, (new Random().nextDouble() -0.5)*2, new Random().nextDouble()/5);
		}
	}
	
	@Override
	public Vec3d getFogColor()
	{
		
		//3, 219, 252
		return new Vec3d(0.0f, 0.8f, 1.0f);
	}
	
	Random rand = new Random();
	
	@Override
	public boolean hasParticles() {
		// TODO Auto-generated method stub
		return true;
	}
}

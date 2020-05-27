package de.budschie.deepnether.biomes;

import java.util.List;
import java.util.Random;

import de.budschie.deepnether.block.BlockInit;
import de.budschie.deepnether.dimension.DeepnetherChunkGenerator;
import de.budschie.deepnether.dimension.DimensionDeepnether;
import de.budschie.deepnether.entity.EntityInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.pattern.BlockMatcher;
import net.minecraft.entity.EntityClassification;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
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
	public static SpawnListEntry TRAP_ENTRY;
	public static SpawnListEntry SHADOW_ENTRY;
	
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
	}
	
	/*
	@Override
	public List<SpawnListEntry> getSpawnablesPositioned(BlockPos pos, EntityClassification classification)
	{
		List<SpawnListEntry> list = super.getSpawnablesPositioned(pos, classification);
		if(pos.getY() < DeepnetherChunkGenerator.cloudMinHeigth)
		{
			if(classification == EntityClassification.MONSTER)
			{
				if(TRAP_ENTRY == null)
				{
					TRAP_ENTRY = new SpawnListEntry(EntityInit.SHADOW_TRAP, 3, 1, 5);
				}
				
				if(SHADOW_ENTRY == null)
				{
					SHADOW_ENTRY = new SpawnListEntry(EntityInit.SHADOW, 1, 1, 3);
				}
				
				list.add(TRAP_ENTRY);
				list.add(SHADOW_ENTRY);
			}
		}
		
		return list;
	}
	*/
	
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

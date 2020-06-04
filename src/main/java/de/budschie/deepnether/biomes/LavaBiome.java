package de.budschie.deepnether.biomes;

import java.util.Random;

import de.budschie.deepnether.block.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;

public class LavaBiome extends DeepnetherBiomeBase
{
	public static class LavaBiomeBuilder extends Builder
	{
		public LavaBiomeBuilder()
		{
			this.precipitation(RainType.NONE).category(Category.NETHER).temperature(100);
		}
	}
	
	public LavaBiome(Builder builder)
	{
		super(builder);
	}
	
	@Override
	public int getBaseHeightMap() 
	{
		return 18;
	}
	
	@Override
	public boolean hasParticles() 
	{
		return true;
	}
	
	@Override
	public void summonParticleAt(World world, int x, int y, int z)
	{
		for(int i = 0; i < 200; i++)
		{
			world.addParticle(ParticleTypes.FLAME, x + new Random().nextInt(150) - 80, y + new Random().nextInt(150) - 80, z + new Random().nextInt(150) - 80, (new Random().nextDouble()-0.5)*4, (new Random().nextDouble()-0.5)*4, (new Random().nextDouble()-0.5)*4);
		}
		//System.out.println("KFJDSLJKLFSDKJLFDSkJLFSDLKJÖFDSKJLÖSFD");
	}
	
	@Override
	public Vec3d getFogColor()
	{
		return new Vec3d(0.6, 0.1, 0.1);
	}
	
	@Override
	public BlockState getTopBlock()
	{
		return BlockInit.COMPRESSED_NETHERRACK.getDefaultState();
	}
	
	@Override
	public boolean hasNearLavaSoil()
	{
		return false;
	}
}

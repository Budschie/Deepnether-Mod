package de.budschie.deepnether.biomes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.budschie.deepnether.block.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockMatcher;
import net.minecraft.entity.EntityClassification;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.OreFeatureConfig.FillerBlockType;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class DeepnetherBiomeBase extends Biome
{
	public static FillerBlockType COMPRESSED_NETHERRACK = FillerBlockType.create("COMPRESSED_NETHERRACK", "deepnether:compressed_netherrack", new BlockMatcher(BlockInit.COMPRESSED_NETHERRACK));

	public static final ArrayList<SpawnListEntry> EMPTY = new ArrayList<>();
	
	public DeepnetherBiomeBase(Builder builder) 
	{
		super(builder.depth(0.1F).scale(0.2F).temperature(2.0F).downfall(0.0F).waterColor(4159204).waterFogColor(329011).surfaceBuilder(SurfaceBuilder.NETHER, SurfaceBuilder.NETHERRACK_CONFIG));		
	}
	
	public int getBaseHeightMap()
	{
		return 10;
	}
	
	public List<Biome.SpawnListEntry> getSpawnablesPositioned(BlockPos pos, EntityClassification classification)
	{
		return getSpawns(classification);
	}
	
	public BlockState getLavaBlock()
	{
		return Blocks.LAVA.getDefaultState();
	}
	
	public BlockState getTopBlock()
	{
		return BlockInit.COMPRESSED_NETHERRACK.getDefaultState();
	}
	
	public boolean hasNearLavaSoil()
	{
		return true;
	}
	
	public BlockState getLavaSoil()
	{
		return BlockInit.SOUL_DUST.getDefaultState();
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

package de.budschie.deepnether.biomes;

import java.util.List;
import java.util.Random;

import de.budschie.deepnether.block.BlockInit;
import de.budschie.deepnether.worldgen.CrystalsWorldGen;
import de.budschie.deepnether.worldgen.Features;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.ReportedException;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.NetherBiome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.ForestBiome;
import net.minecraft.world.dimension.NetherDimension;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.OverworldChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;

public class CrystalCaveBiome extends DeepnetherBiomeBase
{
	public static class CrystalCaveBiomeBuilder extends Builder
	{
		public CrystalCaveBiomeBuilder()
		{
			this.precipitation(RainType.NONE).category(Category.NETHER).temperature(100);
		}
	}
	
	public CrystalCaveBiome(Builder builder)
	{
		super(builder);
		// .withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		// .withPlacement(Placements.CRYSTALS_PLACEMENT.configure(IPlacementConfig.NO_PLACEMENT_CONFIG))
		/*
		System.out.println("Adding features...");
		this.addFeature(Decoration.VEGETAL_DECORATION, Features.CRYSTALS_FEATURE);
		int size = 0;
		for(List<ConfiguredFeature<?, ?>> feature : features.values())
		{
			size += feature.size();
		}
		System.out.println("Added " + size + " features.");
		*/
	}
	
	@Override
	public int getBaseHeightMap()
	{
		return 60;
	}
	
	@Override
	public void decorate(Decoration stage, ChunkGenerator<? extends GenerationSettings> chunkGenerator, IWorld worldIn, long seed, SharedSeedRandom random, BlockPos pos)
	{
		 int i = 0;

	      for(ConfiguredFeature<?, ?> configuredfeature : this.features.get(stage)) {
	         random.setFeatureSeed(seed, i, stage.ordinal());

	         try {
	        	// System.out.println("Applying feature " + configuredfeature.getClass().getName() + "!");
	            configuredfeature.place(worldIn, chunkGenerator, random, pos);
	         } catch (Exception exception) {
	            CrashReport crashreport = CrashReport.makeCrashReport(exception, "Feature placement");
	            crashreport.makeCategory("Feature").addDetail("Id", Registry.FEATURE.getKey(configuredfeature.feature)).addDetail("Description", () -> {
	               return configuredfeature.feature.toString();
	            });
	            throw new ReportedException(crashreport);
	         }

	         ++i;
	      }
	}

	@Override
	public BlockState getTopBlock() {
		// TODO Auto-generated method stub
		return BlockInit.SOUL_DUST.getDefaultState();
	}
	
    /**
     * <h1><b><i>Copied and slightly modified from the java lib. -Budschie</i></b><br/><br/></h1>
     * Converts the components of a color, as specified by the HSB
     * model, to an equivalent set of values for the default RGB model.
     * <p>
     * The <code>saturation</code> and <code>brightness</code> components
     * should be floating-point values between zero and one
     * (numbers in the range 0.0-1.0).  The <code>hue</code> component
     * can be any floating-point number.  The floor of this number is
     * subtracted from it to create a fraction between 0 and 1.  This
     * fractional number is then multiplied by 360 to produce the hue
     * angle in the HSB color model.
     * <p>
     * The integer that is returned by <code>HSBtoRGB</code> encodes the
     * value of a color in bits 0-23 of an integer value that is the same
     * format used by the method {@link #getRGB() getRGB}.
     * This integer can be supplied as an argument to the
     * <code>Color</code> constructor that takes a single integer argument.
     * @param     hue   the hue component of the color
     * @param     saturation   the saturation of the color
     * @param     brightness   the brightness of the color
     * @return    the RGB value of the color with the indicated hue,
     *                            saturation, and brightness.
     * @see       java.awt.Color#getRGB()
     * @see       java.awt.Color#Color(int)
     * @see       java.awt.image.ColorModel#getRGBdefault()
     * @since     JDK1.0
     */
    public static Vec3d HSBtoRGB(float hue, float saturation, float brightness) {
        int r = 0, g = 0, b = 0;
        if (saturation == 0) {
            r = g = b = (int) (brightness * 255.0f + 0.5f);
        } else {
            float h = (hue - (float)Math.floor(hue)) * 6.0f;
            float f = h - (float)java.lang.Math.floor(h);
            float p = brightness * (1.0f - saturation);
            float q = brightness * (1.0f - saturation * f);
            float t = brightness * (1.0f - (saturation * (1.0f - f)));
            switch ((int) h) {
            case 0:
                r = (int) (brightness * 255.0f + 0.5f);
                g = (int) (t * 255.0f + 0.5f);
                b = (int) (p * 255.0f + 0.5f);
                break;
            case 1:
                r = (int) (q * 255.0f + 0.5f);
                g = (int) (brightness * 255.0f + 0.5f);
                b = (int) (p * 255.0f + 0.5f);
                break;
            case 2:
                r = (int) (p * 255.0f + 0.5f);
                g = (int) (brightness * 255.0f + 0.5f);
                b = (int) (t * 255.0f + 0.5f);
                break;
            case 3:
                r = (int) (p * 255.0f + 0.5f);
                g = (int) (q * 255.0f + 0.5f);
                b = (int) (brightness * 255.0f + 0.5f);
                break;
            case 4:
                r = (int) (t * 255.0f + 0.5f);
                g = (int) (p * 255.0f + 0.5f);
                b = (int) (brightness * 255.0f + 0.5f);
                break;
            case 5:
                r = (int) (brightness * 255.0f + 0.5f);
                g = (int) (p * 255.0f + 0.5f);
                b = (int) (q * 255.0f + 0.5f);
                break;
            }
        }
        return new Vec3d(r/255.0f,g/255.0f,b/255.0f);
    }
    
    private float timeDividor = 50000.0f;
	
	@Override
	public Vec3d getFogColor() {
		// TODO Auto-generated method stub
		//Color.HSBtoRGB((float) Math.sin((Minecraft.getSystemTime()/500.0f)), 0.89f, 0.7f))
		//Color col = Color. Color.getHSBColor((float) ((Math.sin((Minecraft.getSystemTime()/500.0f)+1)/2)), 0.5f, 0.3f);
		//int col = ;
		//col.R
	
		//return BiomeCrystal.HSBtoRGB((float) ((Math.sin(((Minecraft.getSystemTime()/2500.0f)))+1)/2), 0.9f, 0.5f).addVector(1.0f, 1.0f, 1.0f);
		//ParticleBlockDust
		return CrystalCaveBiome.HSBtoRGB((float) ((Math.sin(((System.currentTimeMillis()/timeDividor)))+1)/2), 0.9f, 0.5f);
	}
	
	@Override
	public boolean hasParticles() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public void summonParticleAt(World world, int x, int y, int z) 
	{
		//int col = Color.HSBtoRGB((float) ((Math.sin(((Minecraft.getSystemTime()/25000.0f)))+1)/2), 0.9f, 0.5f);
		Vec3d rgb = CrystalCaveBiome.HSBtoRGB((float) ((Math.cos(((System.currentTimeMillis()/timeDividor)))+1)/2), 0.9f, 0.5f);
		for(int i = 0; i < 20; i++)
		{
			world.addParticle(new RedstoneParticleData((float)rgb.x, (float)rgb.y, (float)rgb.z, (new Random().nextFloat()+1.0f)/2.0f), x-50+new Random().nextInt(100), y-50+new Random().nextInt(100), z-50+new Random().nextInt(100), new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat());
		}
	}
	
	@Override
	public void populate(World worldIn, int x, int z) 
	{
		// TODO Auto-generated method stub
		//crystals.generate(worldIn, new java.util.Random(x*z+x+z+z), new BlockPos(x, 1, z));
		super.populate(worldIn, x, z);
	}
}

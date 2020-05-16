package de.budschie.deepnether.worldgen;

import java.io.FileNotFoundException;
import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import de.budschie.deepnether.structures.PaletteStructure;
import de.budschie.deepnether.structures.StructureBufferObject;
import de.budschie.deepnether.worldgen.structureSaving.StructureDataHandler;
import de.budschie.deepnether.worldgen.structureSaving.StructureDataProviderRegistry;
import de.budschie.deepnether.worldgen.structureSaving.TestArguments;
import de.budschie.deepnether.worldgen.structureSaving.TestStructureData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class TestFeature extends Feature<IFeatureConfig>
{
	public static StructureBufferObject object = null;
	
	public TestFeature(Function<Dynamic<?>, ? extends IFeatureConfig> configFactoryIn)
	{
		super(configFactoryIn);
	}

	@Override
	public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, IFeatureConfig config)
	{
		if(object == null)
		{
			try
			{
				object = new PaletteStructure().loadIntoMemory("soos");
			} 
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
		}

		PaletteStructure.place(object, pos, worldIn.getWorld());
		//TestStructureData data = (TestStructureData) StructureDataProviderRegistry.<TestArguments>getEntry("test").get(args);
		
		//StructureDataHandler.addStructure(data.getWorld(), data);
		
		return true;
	}
}

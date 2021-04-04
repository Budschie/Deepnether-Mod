package de.budschie.deepnether.features.tree_generation;

import java.util.HashMap;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Function;

import com.mojang.serialization.Codec;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;

// TODO: Work out a good concept of generating trees with multiple parts that are switchable
public class BasicTreeGenerator extends Feature<NoFeatureConfig>
{
	private HashMap<String, ITreePart> treeParts;
	ITreePart mainTreePart;
	
	private BasicTreeGenerator(Codec<NoFeatureConfig> codec, HashMap<String, ITreePart> treeParts, ITreePart mainTreePart)
	{
		super(codec);
		this.treeParts = treeParts;
		this.mainTreePart = mainTreePart;
	}
	
	private void emit(String emitionName, TreeEmitionArgs args, ISeedReader reader, ChunkGenerator generator, Random rand)
	{
		// This code... it's awful.
		treeParts.get(emitionName).generate((name, arguments) -> emit(name, arguments, reader, generator, rand), args, reader, generator, rand);
	}

	@Override
	public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos,
			NoFeatureConfig config)
	{
		BiConsumer<String, TreeEmitionArgs> emitionFunction = (name, args) -> emit(name, args, reader, generator, rand);
		
		mainTreePart.generate(emitionFunction, new TreeEmitionArgs(pos), reader, generator, rand);
		
		return false;
	}
	
	public static class BasicTreeGeneratorBuilder
	{
		private HashMap<String, ITreePart> treeParts = new HashMap<>();
		ITreePart mainTreePart;
		
		public BasicTreeGeneratorBuilder setMainTreePart(String name, ITreePart part)
		{
			this.mainTreePart = part;
			this.treeParts.put(name, part);
			return this;
		}
		
		public BasicTreeGeneratorBuilder addTreePart(String name, ITreePart part)
		{
			this.treeParts.put(name, part);
			return this;
		}
		
		public BasicTreeGenerator build()
		{
			return new BasicTreeGenerator(NoFeatureConfig.field_236558_a_, treeParts, mainTreePart);
		}
	}
}
package de.budschie.deepnether.features.tree_generation;

import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Function;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;

public interface ITreePart
{
	void generate(BiConsumer<String, TreeEmitionArgs> emit, TreeEmitionArgs emitionArgs, ISeedReader reader, ChunkGenerator generator, Random rand);
}

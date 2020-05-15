package de.budschie.deepnether.dimension;

import java.util.function.BiFunction;

import de.budschie.deepnether.main.References;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.NetherBiome;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ModDimension;

public class DeepnetherModDimension extends ModDimension
{
	public static final ResourceLocation MOD_DIM_RL = new ResourceLocation(References.MODID, "deepnether_dim");
	
	@Override
	public BiFunction<World, DimensionType, ? extends Dimension> getFactory()
	{
		return new BiFunction<World, DimensionType, Dimension>()
		{
			@Override
			public Dimension apply(World world, DimensionType type)
			{
				if(type == null)
					throw new NullPointerException("Dimension type was null. This is a bug. Please report it.");
				if(world == null)
					throw new NullPointerException("World was null. This is a bug. Please report it.");
				
				return new DimensionDeepnether(world, type);
			}
		};
	}
}

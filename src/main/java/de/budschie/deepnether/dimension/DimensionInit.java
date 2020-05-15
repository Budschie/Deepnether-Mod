package de.budschie.deepnether.dimension;

import de.budschie.deepnether.main.References;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DimensionInit
{
	public static final DeferredRegister<ModDimension> MOD_DIM_DREG = new DeferredRegister<>(ForgeRegistries.MOD_DIMENSIONS, References.MODID);
	
	public static final RegistryObject<ModDimension> DEEPNETHER_MOD_DIM = MOD_DIM_DREG.register(DeepnetherModDimension.MOD_DIM_RL.getPath(), DeepnetherModDimension::new);
}

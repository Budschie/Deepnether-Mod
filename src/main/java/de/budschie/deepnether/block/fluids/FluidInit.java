package de.budschie.deepnether.block.fluids;

import java.util.function.Supplier;

import de.budschie.deepnether.main.References;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.LavaFluid;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber(bus = Bus.FORGE)
public class FluidInit
{
	public static final DeferredRegister<Fluid> DEF_REG_FLUID = DeferredRegister.create(ForgeRegistries.FLUIDS, References.MODID);
	
	public static final RegistryObject<Fluid> HOT_JELLY_FLUID = DEF_REG_FLUID.register("hot_jelly", HotJellyFluid.Source::new);
	public static final RegistryObject<Fluid> HOT_JELLY_FLOWING_FLUID = DEF_REG_FLUID.register("hot_jelly_flowing", HotJellyFluid.Flowing::new);
}

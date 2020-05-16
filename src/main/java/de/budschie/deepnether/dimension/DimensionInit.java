package de.budschie.deepnether.dimension;

import de.budschie.deepnether.main.References;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber(bus = Bus.FORGE, modid = References.MODID)
public class DimensionInit
{
	public static final DeferredRegister<ModDimension> MOD_DIM_DREG = new DeferredRegister<>(ForgeRegistries.MOD_DIMENSIONS, References.MODID);
	
	public static final RegistryObject<ModDimension> DEEPNETHER_MOD_DIM = MOD_DIM_DREG.register(DeepnetherModDimension.MOD_DIM_RL.getPath(), DeepnetherModDimension::new);

	@SubscribeEvent
	public static void onRegisterDimType(RegisterDimensionsEvent event)
	{
		DimensionManager.registerOrGetDimension(DEEPNETHER_MOD_DIM.getId(), DEEPNETHER_MOD_DIM.get(), null, false);
		
	}
}

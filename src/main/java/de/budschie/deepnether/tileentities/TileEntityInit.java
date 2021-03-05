package de.budschie.deepnether.tileentities;

import java.util.Set;

import de.budschie.deepnether.block.BlockInit;
import de.budschie.deepnether.main.References;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber(bus = Bus.MOD)
public class TileEntityInit
{
	public static TileEntityType<?> DEEP_NETHER_BLAST_FURNACE_TYPE;
	
	@SubscribeEvent
	public static void registerTETypes(RegistryEvent.Register<TileEntityType<?>> event)
	{
		IForgeRegistry<TileEntityType<?>> registry = event.getRegistry();
		
		DEEP_NETHER_BLAST_FURNACE_TYPE = TileEntityType.Builder.create(DeepNetherBlastFurnaceTileEntity::new, BlockInit.DEEP_NETHER_BLAST_FURNACE).build(null).setRegistryName(new ResourceLocation(References.MODID, "deep_nether_blast_furnace_tileentity"));
		registry.register(DEEP_NETHER_BLAST_FURNACE_TYPE);
	}
}

package de.budschie.deepnether.worldgen.structureSaving;

import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.HandSide;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE)
public class SavingEventHandler
{
	@SubscribeEvent
	public static void onLoadChunk(ChunkEvent.Load event)
	{
		if(event.getWorld() == null)
			return;
		if(event.getWorld().isRemote())
			return;
		System.out.println("Loading structures...");
		StructureDataHandler.readChunk(event.getChunk().getPos(), event.getChunk().getWorldForge());
	}
	
	@SubscribeEvent
	public static void onSaveChunk(ChunkEvent.Unload event)
	{
		if(event.getWorld().isRemote())
			return;
		System.out.println("Saving structures...");
		StructureDataHandler.removeChunk(event.getChunk().getPos(), event.getChunk().getWorldForge());
	}
	
	@SubscribeEvent
	public static void onLoadWorld(WorldEvent.Load event)
	{
		
		if(event.getWorld().isRemote())
			return;
		System.out.println("Loading headers...");
		StructureDataHandler.loadHeaders(((ServerWorld)event.getWorld()).getSaveHandler().getWorldDirectory().getAbsolutePath().toString());
	}
	
	@SubscribeEvent
	public static void onSaveWorld(WorldEvent.Save event)
	{
		if(event.getWorld().isRemote())
			return;
		System.out.println("Saving world...");
		StructureDataHandler.onSaveEntireWorld(((ServerWorld)event.getWorld()).getSaveHandler(), event.getWorld(), false);
	}
	
	@SubscribeEvent
	public static void onUnloadWorld(WorldEvent.Unload event)
	{
		if(event.getWorld().isRemote())
			return;
		System.out.println("Saving structures...");
		StructureDataHandler.onUnloadWorld(((ServerWorld)event.getWorld().getWorld()).getSaveHandler(), event.getWorld().getWorld());
	}
}

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
	public static boolean loadedWorld = false;
	public static boolean isSavingEntireWorld = false;
	
	@SubscribeEvent
	public static void onLoadChunk(ChunkEvent.Load event)
	{
		if(event.getWorld() == null)
			return;
		if(event.getWorld().isRemote())
			return;
		StructureDataHandler.readChunk(event.getChunk().getPos(), event.getWorld());
	}
	
	@SubscribeEvent
	public static void onSaveChunk(ChunkEvent.Unload event)
	{
		if(event.getWorld().isRemote())
			return;
		System.out.println("Saving structures...");
		if(isSavingEntireWorld) throw new IllegalStateException("This REALLY shouldnt happen");
		StructureDataHandler.removeChunk(event.getChunk().getPos(), event.getChunk().getWorldForge());
	}
	
	@SubscribeEvent
	public static void onLoadWorld(WorldEvent.Load event)
	{
		if(event.getWorld().isRemote())
			return;
		System.out.println("Loading headers...");
		StructureDataHandler.loadHeaders(((ServerWorld)event.getWorld()).getSaveHandler().getWorldDirectory().getAbsolutePath().toString());
		loadedWorld = true;
	}
	
	@SubscribeEvent
	public static void onSaveWorld(WorldEvent.Save event)
	{
		if(event.getWorld().isRemote())
			return;
		isSavingEntireWorld = true;
		System.out.println("Saving world...");
		StructureDataHandler.onSaveEntireWorld(((ServerWorld)event.getWorld()).getSaveHandler(), event.getWorld(), false);
		isSavingEntireWorld = false;
	}
	
	@SubscribeEvent
	public static void onUnloadWorld(WorldEvent.Unload event)
	{
		if(event.getWorld().isRemote())
			return;
		isSavingEntireWorld = true;
		System.out.println("Saving structures...");
		StructureDataHandler.onUnloadWorld(((ServerWorld)event.getWorld().getWorld()).getSaveHandler(), event.getWorld().getWorld());
		isSavingEntireWorld = false;
	}
}

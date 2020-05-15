package de.budschie.deepnether.main;

import de.budschie.deepnether.dimension.DimensionDeepnether;
import de.budschie.deepnether.networking.DeepnetherPacketHandler;
import de.budschie.deepnether.networking.FogSeedMessageRecieve;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.network.PacketDistributor;

@EventBusSubscriber(bus = Bus.FORGE, modid = References.MODID, value = Dist.CLIENT)
public class ClientBaseEvents
{
	/*
	@SubscribeEvent
	public static void onPlayerJoin( event)
	{
		if(!event.getPlayer().isServerWorld())
			return;
		
		FogSeedMessage message = new FogSeedMessage();
		
		message.seed = event.getPlayer().world.getSeed();
		
		DeepnetherPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), message);
	}
	*/
	
	@SubscribeEvent
	public static void onTick(TickEvent.WorldTickEvent tickEvent)
	{
		if(Minecraft.getInstance().player == null)
			return;
		if(Minecraft.getInstance().player.world == null)
			return;
		
		if(Minecraft.getInstance().player.world.getDimension() instanceof DimensionDeepnether)
		{
			DimensionDeepnether deep_nether = (DimensionDeepnether)Minecraft.getInstance().player.world.getDimension();
			
			deep_nether.spawnAmbientParticles();
		}
	}
}

package de.budschie.deepnether.main;

import java.util.function.Supplier;

import de.budschie.deepnether.networking.DeepnetherPacketHandler;
import de.budschie.deepnether.networking.StructureIDPacket;
import de.budschie.deepnether.worldgen.structureSaving.StructureDataHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.network.PacketDistributor;

@EventBusSubscriber
public class ServerBaseEvents
{
	static int i = 0;
	@SubscribeEvent
	public static void onTick(TickEvent.ServerTickEvent event)
	{
		i++;
		if(i > 5)
		{
			for(ServerPlayerEntity player : DeepnetherMain.server.getPlayerList().getPlayers())
			{
				StructureIDPacket packet = new StructureIDPacket();
				packet.id = StructureDataHandler.getStructureAtPosition(player.getPosition(), player.getEntityWorld()) == null ? -1 : StructureDataHandler.getStructureAtPosition(player.getPosition(), player.getEntityWorld()).getID();
				DeepnetherPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(new Supplier<ServerPlayerEntity>()
				{
					
					@Override
					public ServerPlayerEntity get()
					{
						return player;
					}
				}), packet);
				
			}
		}
	}
}

package de.budschie.deepnether.networking;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

import de.budschie.deepnether.dimension.RecievedSeedEvent;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.network.NetworkEvent;

public class FogSeedMessageRecieve
{
	public long seed = 0;
	
	public static void encodeAtServer(FogSeedMessageRecieve messageData, PacketBuffer buffer)
	{
		buffer.writeLong(messageData.seed);
		System.out.println("Successfully encoded seed!");
	}
	
	public static FogSeedMessageRecieve decodeAtClient(PacketBuffer buffer)
	{
		FogSeedMessageRecieve message = new FogSeedMessageRecieve();
		message.seed = buffer.readLong();
		System.out.println("Successfully decoded seed!");

		return message;
	}
	
	public static void handleAtClient(FogSeedMessageRecieve messageData, Supplier<NetworkEvent.Context> contextSupplier)
	{
		System.out.println("Got seed: " + messageData.seed);
		
		RecievedSeedEvent event = new RecievedSeedEvent();
		event.seed = messageData.seed;
		
		contextSupplier.get().enqueueWork(new Runnable()
		{
			@Override
			public void run()
			{
				MinecraftForge.EVENT_BUS.post(event);
			}
		});
		
		System.out.println("Successfully posted event!");
		
		contextSupplier.get().setPacketHandled(true);
	}
}

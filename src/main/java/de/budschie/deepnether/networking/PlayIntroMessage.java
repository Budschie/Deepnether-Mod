package de.budschie.deepnether.networking;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

import de.budschie.deepnether.dimension.RecievedSeedEvent;
import de.budschie.deepnether.gui.intro.IntroRecievedEvent;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.network.NetworkEvent;

public class PlayIntroMessage
{
	public static void encodeAtServer(PlayIntroMessage messageData, PacketBuffer buffer)
	{
	}
	
	public static PlayIntroMessage decodeAtClient(PacketBuffer buffer)
	{
		PlayIntroMessage message = new PlayIntroMessage();
		return message;
	}
	
	public static void handleAtClient(PlayIntroMessage messageData, Supplier<NetworkEvent.Context> contextSupplier)
	{		
		IntroRecievedEvent event = new IntroRecievedEvent();
		
		contextSupplier.get().enqueueWork(new Runnable()
		{
			@Override
			public void run()
			{
				MinecraftForge.EVENT_BUS.post(event);
			}
		});
		
		System.out.println("Successfully playing event!");
		
		contextSupplier.get().setPacketHandled(true);
	}
}

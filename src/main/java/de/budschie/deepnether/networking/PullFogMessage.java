package de.budschie.deepnether.networking;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

public class PullFogMessage
{
	public static void pull(PullFogMessage object, PacketBuffer buffer)
	{
		
	}
	
	public static PullFogMessage pulled(PacketBuffer buffer)
	{
		return new PullFogMessage();
	}
	
	public static void handleAtServer(PullFogMessage messageData, Supplier<NetworkEvent.Context> contextSupplier)
	{
		FogSeedMessageRecieve message = new FogSeedMessageRecieve();
		
		message.seed = contextSupplier.get().getSender().getEntityWorld().getSeed();
		
		DeepnetherPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(()->contextSupplier.get().getSender()), message);
		System.out.println("Successfully pulled!!!");
		contextSupplier.get().setPacketHandled(true);
	}
}

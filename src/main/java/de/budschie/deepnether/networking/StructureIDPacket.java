package de.budschie.deepnether.networking;

import java.util.function.Supplier;

import de.budschie.deepnether.dimension.RecievedSeedEvent;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.network.NetworkEvent;

public class StructureIDPacket
{
	public int id = 0;
	
	public static int currentId = -2;
	
	public static void encodeAtServer(StructureIDPacket messageData, PacketBuffer buffer)
	{
		buffer.writeInt(messageData.id);
	}
	
	public static StructureIDPacket decodeAtClient(PacketBuffer buffer)
	{
		StructureIDPacket message = new StructureIDPacket();
		message.id = buffer.readInt();

		return message;
	}
	
	public static void handleAtClient(StructureIDPacket messageData, Supplier<NetworkEvent.Context> contextSupplier)
	{		
		contextSupplier.get().enqueueWork(new Runnable()
		{
			@Override
			public void run()
			{
				currentId = messageData.id;
			}
		});
		
		
		contextSupplier.get().setPacketHandled(true);
	}
}

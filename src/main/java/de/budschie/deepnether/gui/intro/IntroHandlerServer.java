package de.budschie.deepnether.gui.intro;

import de.budschie.deepnether.networking.DeepnetherPacketHandler;
import de.budschie.deepnether.networking.PlayIntroMessage;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.PacketDistributor.PacketTarget;

public class IntroHandlerServer
{
	public static void startIntroForPlayerOnServer(ServerPlayerEntity player)
	{
		DeepnetherPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new PlayIntroMessage());
	}
}

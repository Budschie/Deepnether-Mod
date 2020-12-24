package de.budschie.deepnether.commands;

import java.util.Random;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;

import de.budschie.deepnether.gui.intro.IntroHandlerServer;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class CommandPlayIntro
{
	public static void register(CommandDispatcher<CommandSource> dispatcher)
	{
		LiteralCommandNode<CommandSource> literalcommandnode = dispatcher
				.register(Commands.literal("playintro").requires((p_198740_0_) ->
				{
					return p_198740_0_.hasPermissionLevel(2);
				}).executes((cmd) ->
				{
					IntroHandlerServer.startIntroForPlayerOnServer(cmd.getSource().asPlayer());
					return 0;
				}));
	}
}

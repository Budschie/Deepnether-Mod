package de.budschie.deepnether.commands;

import java.util.UUID;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class CommandToggleSel
{
	public static boolean isSelectionActive = false;
	
	public static void register(CommandDispatcher<CommandSource> dispatcher)
	{
		LiteralCommandNode<CommandSource> literalcommandnode = dispatcher
				.register(Commands.literal("togglesel").requires((p_198740_0_) ->
				{
					return p_198740_0_.hasPermissionLevel(2);
				}).executes((cmd) ->
				{
					isSelectionActive = !isSelectionActive;
					cmd.getSource().asPlayer().sendMessage(new StringTextComponent(TextFormatting.GREEN + "Changed mode to " + (isSelectionActive ? "ACTIVE" : "NONE") + "."), new UUID(0, 0));
					return 0;
				}));
	}
}

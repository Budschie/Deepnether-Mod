package de.budschie.deepnether.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;

import de.budschie.deepnether.structures.StructureBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class CommandPlaceStructure
{
	public static void registerCommandTpDim(CommandDispatcher<CommandSource> dispatcher)
	{
		LiteralCommandNode<CommandSource> literalcommandnode = dispatcher
				.register(Commands.literal("place").requires((p_198740_0_) ->
				{
					return p_198740_0_.hasPermissionLevel(2);
				}).then(Commands.argument("type", StringArgumentType.word()).then(Commands.argument("filename", StringArgumentType.word()).executes((cmd) ->
				{
					return placeStructure(cmd);
				}))));
	}
	
	private static int placeStructure(CommandContext<CommandSource> ctx)
	{
		StructureBase structureType = CommandSaveSelection.getStructure(ctx.getArgument("type", String.class));
		if(structureType == null)
			throw new CommandException(new StringTextComponent(TextFormatting.RED + "The command couldn't be executed.\nReason: The structure type was invalid. Valid types: ['palette']"));
		
		try
		{
			ServerPlayerEntity player = ctx.getSource().asPlayer();
			structureType.readData(ctx.getArgument("filename", String.class), player.getServerWorld(), player.getPosition());
		} 
		catch (CommandSyntaxException e)
		{
			e.printStackTrace();
			throw new CommandException(new StringTextComponent(TextFormatting.RED + "The command couldn't be executed.\nReason: CMD-Source is not a player."));
		}
		
		return 0;
	}
}

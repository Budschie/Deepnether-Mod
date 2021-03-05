package de.budschie.deepnether.commands;

import java.util.Random;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class CommandPlaceTree
{
	public static void register(CommandDispatcher<CommandSource> dispatcher)
	{
		LiteralCommandNode<CommandSource> literalcommandnode = dispatcher
				.register(Commands.literal("placetree").requires((p_198740_0_) ->
				{
					return p_198740_0_.hasPermissionLevel(2);
				}).executes((cmd) ->
				{
					//Features.BIG_TREE_FEATURE.get().func_230362_a_(cmd.getSource().asPlayer().getEntityWorld(), null, null, new Random(), new BlockPos(cmd.getSource().asPlayer().getPosX(), cmd.getSource().asPlayer().getPosY(), cmd.getSource().asPlayer().getPosZ()), IFeatureConfig.NO_FEATURE_CONFIG);
					return 0;
				}));
	}
}

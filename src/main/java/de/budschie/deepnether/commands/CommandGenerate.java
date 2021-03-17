package de.budschie.deepnether.commands;

import java.util.Random;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;

import de.budschie.deepnether.commands.argumenttypes.ConfiguredFeatureArgument;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.ILocationArgument;
import net.minecraft.command.arguments.Vec3Argument;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DecoratedFeature;
import net.minecraft.world.gen.feature.DecoratedFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class CommandGenerate
{
	public static void registerCommandGenerate(CommandDispatcher<CommandSource> dispatcher)
	{		
		LiteralCommandNode<CommandSource> literalcommandnode = dispatcher.register(Commands.literal("generate")
				.requires(sender -> sender.hasPermissionLevel(2))
				.then(Commands.argument("position", new Vec3Argument(false))
				.then(Commands.argument("feature", new ConfiguredFeatureArgument()).executes(CommandGenerate::generateFeature))));
	}
	
	private static int generateFeature(CommandContext<CommandSource> ctx)
	{
		try
		{
			BlockPos location = ctx.getArgument("position", ILocationArgument.class).getBlockPos(ctx.getSource());
			ConfiguredFeature<? extends IFeatureConfig, ?> configuredFeature = ctx.getArgument("feature", ConfiguredFeature.class);
			
			while(configuredFeature.config instanceof DecoratedFeatureConfig)
			{
				configuredFeature = ((DecoratedFeatureConfig)configuredFeature.config).feature.get();
			}
			
			configuredFeature.generate(ctx.getSource().getWorld(), ctx.getSource().getWorld().getChunkProvider().getChunkGenerator(), new Random(location.getX() ^ location.getY() ^ location.getZ()), location);
			//configuredFeature.feature.generate(ctx.getSource().getWorld(), ctx.getSource().getWorld().getChunkProvider().getChunkGenerator(), new Random(location.getX() ^ location.getY() ^ location.getZ()), location, configuredFeature.config);
		}
		catch (Exception e) 
		{
			throw new CommandException(new StringTextComponent(e.getMessage()));
		}
		
		return 0;
	}
}

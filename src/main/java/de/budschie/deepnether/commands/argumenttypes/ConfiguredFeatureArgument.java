package de.budschie.deepnether.commands.argumenttypes;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import net.minecraft.command.ISuggestionProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class ConfiguredFeatureArgument implements ArgumentType<ConfiguredFeature<?, ?>>
{
	@Override
	public ConfiguredFeature<?, ?> parse(StringReader reader) throws CommandSyntaxException
	{
		ResourceLocation resourceLocation = ResourceLocation.read(reader);
		Optional<ConfiguredFeature<?, ?>> optionalFeature = WorldGenRegistries.CONFIGURED_FEATURE.getOptional(resourceLocation);
		
		if(optionalFeature.isPresent())
		{
			return optionalFeature.get();
		}
		else
			throw new SimpleCommandExceptionType(new StringTextComponent(TextFormatting.RED + "THe given feature " + resourceLocation + " doesn't exist.")).create();
	}
	
	@Override
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder)
	{
		return ISuggestionProvider.suggest(WorldGenRegistries.CONFIGURED_FEATURE.getEntries().stream().map(input -> input.getKey().getLocation().toString()), builder);
	}
}

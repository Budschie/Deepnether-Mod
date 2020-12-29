package de.budschie.deepnether.item.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.minecraft.block.CraftingTableBlock;
import net.minecraft.client.gui.screen.inventory.CraftingScreen;
import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.ShapedRecipe.Serializer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class ToolRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ToolRecipe>
{
	@Override
	public ToolRecipe read(ResourceLocation recipeId, JsonObject json)
	{
		JsonArray array = json.get("crafting_recipe").getAsJsonArray();
		
		String[] str = new String[array.size()];
		
		for(int i = 0; i < array.size(); i++)
		{
			str[i] = array.get(i).getAsString();
		}
		
		return new ToolRecipe(Matcher.fromString(str), ToolType.get(json.get("tool_type").getAsString()), recipeId);
	}

	@Override
	public ToolRecipe read(ResourceLocation recipeId, PacketBuffer buffer)
	{
		String[] str = new String[3];
		
		str[0] = buffer.readString(3);
		str[1] = buffer.readString(3);
		str[2] = buffer.readString(3);
		
		return new ToolRecipe(Matcher.fromString(str), ToolType.get(buffer.readString()), recipeId);
	}

	@Override
	public void write(PacketBuffer buffer, ToolRecipe recipe)
	{
		String[] array = recipe.getMatcher().toStringForNetworking();
		
		for(String str : array)
			buffer.writeString(str);
		
		buffer.writeString(recipe.getToolType().getName());
	}
}

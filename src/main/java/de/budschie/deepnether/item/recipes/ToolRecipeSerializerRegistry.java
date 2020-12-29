package de.budschie.deepnether.item.recipes;

import de.budschie.deepnether.main.References;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ToolRecipeSerializerRegistry
{
	public static final DeferredRegister<IRecipeSerializer<?>> DEF_REG_RECIPE = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, References.MODID);
	
	public static final RegistryObject<IRecipeSerializer<?>> TOOL_RECIPE = DEF_REG_RECIPE.register("dyn_tool_serializer", ToolRecipeSerializer::new);
}

package de.budschie.deepnether.item;

import static de.budschie.deepnether.item.ModItemTiers.CNR_TIER;
import static de.budschie.deepnether.item.ModItemTiers.DYL_TIER;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import org.apache.logging.log4j.LogManager;

import de.budschie.deepnether.block.BlockInit;
import de.budschie.deepnether.block.CompressedNetherrackBlockItem;
import de.budschie.deepnether.entity.EntityInit;
import de.budschie.deepnether.item.rendering.DynamicCommonToolRendering;
import de.budschie.deepnether.main.References;
import de.budschie.deepnether.util.ModItemGroups;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber(modid = References.MODID, bus = Bus.MOD)
public class ItemInit 
{
	public static ArrayList<Item> MOD_ITEMS = new ArrayList<>();
	
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, References.MODID);
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		EntityInit.createEntityTypes();
		
		IForgeRegistry<Item> reg = event.getRegistry();
		reg.register(new CompressedNetherrackBlockItem(BlockInit.COMPRESSED_NETHERRACK).setRegistryName(new ResourceLocation(References.MODID, "compressed_netherrack")));
		
		for(Item item : MOD_ITEMS)
		{
			reg.register(item);
			LogManager.getLogger().info("Registered item " + item.getRegistryName().toString() + ".");
		}
		
		reg.register(createModSpawnEgg(EntityInit.HELL_GOAT, 0x000d5e, 0x212436));
		reg.register(createModSpawnEgg(EntityInit.SHADOW, 0x212436, 0x000d5e));
	}
	
	private static SpawnEggItem createModSpawnEgg(EntityType<? extends Entity> type, int primaryColor, int secondaryColor)
	{
		return (SpawnEggItem) new SpawnEggItem(type, primaryColor, secondaryColor, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(new ResourceLocation(type.getRegistryName().toString()+"_spawn_egg"));
	}
	
	public static final ToolGroup COMPRESSED_NETHERRACK_TOOL_GROUP = new ToolGroup(REGISTRY, "cnr", CNR_TIER.addDescriptionElement(ToolMaterialDescriptionElement.HOT).addDescriptionElement(ToolMaterialDescriptionElement.ROUGH), (int) CNR_TIER.getAttackDamage(), .25f, new Item.Properties().group(ModItemGroups.MOD_TOOLS), new Item.Properties().group(ModItemGroups.MOD_TOOLS), new Item.Properties().group(ModItemGroups.MOD_TOOLS), new Item.Properties().group(ModItemGroups.MOD_TOOLS));
	public static final ToolGroup DYLITHITE_TOOL_GROUP = new ToolGroup(REGISTRY, "dyl", DYL_TIER, (int) DYL_TIER.getAttackDamage(), 5.0f, new Item.Properties().group(ModItemGroups.MOD_TOOLS), new Item.Properties().group(ModItemGroups.MOD_TOOLS), new Item.Properties().group(ModItemGroups.MOD_TOOLS), new Item.Properties().group(ModItemGroups.MOD_TOOLS));
	
	public static final ArmorSet COMPRESSED_NETHERRACK_ARMOR = new ArmorSet("cnr", ModArmorMaterials.CNR_ARMOR_MATERIAL, new Item.Properties().group(ModItemGroups.MOD_ARMOR));	
	public static final ArmorSet DYLITHITE_ARMOR = new ArmorSet("dyl", ModArmorMaterials.DYL_ARMOR_MATERIAL, new Item.Properties().group(ModItemGroups.MOD_ARMOR));	
	
	public static final BaseItem AMYLITHE_SHARD = new BaseItem("amylithe_shard", new Item.Properties().group(ModItemGroups.MOD_INGREDIENTS));
	public static final BaseItem AMYLITHE_ORB = new BaseItem("amylithe_orb", new Item.Properties().group(ModItemGroups.MOD_INGREDIENTS));
	
	public static final BaseItem DYLITHITE_INGOT = new BaseItem("dylithite_ingot", new Item.Properties().group(ModItemGroups.MOD_INGREDIENTS));
	
	/*
	 * .setISTER(() -> new Callable<ItemStackTileEntityRenderer>()
	{
		DynamicCommonToolRendering r = null;
		
		@Override
		public ItemStackTileEntityRenderer call() throws Exception
		{
			if(r == null)
				r = new DynamicCommonToolRendering();
			
			return r;
		}
		
	})
	 */
	public static final CommonTool COMMON_TOOL = new CommonTool(new Item.Properties());
}

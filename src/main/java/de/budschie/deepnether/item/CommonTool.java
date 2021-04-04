package de.budschie.deepnether.item;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.electronwill.nightconfig.core.io.CharsWrapper.Builder;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

import de.budschie.deepnether.capabilities.IToolDefinition;
import de.budschie.deepnether.capabilities.ToolDefinitionCapability;
import de.budschie.deepnether.item.toolModifiers.Stats;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.registries.ForgeRegistries;

public class CommonTool extends Item
{	
	public CommonTool(Properties properties)
	{
		super(properties);
	}
	
	@Override
	public int getMaxDamage(ItemStack stack)
	{
		LazyOptional<IToolDefinition> optional = stack.getCapability(ToolDefinitionCapability.TOOL_DEF_CAP);
		Stats stats = optional.resolve().get().constructStats(this, stack);
		
		return stats.getDurability();
	}
	
	@Override
	public boolean canPlayerBreakBlockWhileHolding(BlockState state, World worldIn, BlockPos pos, PlayerEntity player)
	{
		ItemStack currentItemStack = player.getHeldItemMainhand();
		LazyOptional<IToolDefinition> toolDefinition = currentItemStack.getCapability(ToolDefinitionCapability.TOOL_DEF_CAP);
		
		return !toolDefinition.isPresent() || !(toolDefinition.resolve().get().getToolType() == ToolType.get("sword") && player.isCreative());
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack)
	{
		return 1;
	}
	
	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state)
	{
		return state.getHarvestTool().getName().equals(stack.getCapability(ToolDefinitionCapability.TOOL_DEF_CAP).resolve().get().getToolType().getName()) ? stack.getCapability(ToolDefinitionCapability.TOOL_DEF_CAP).orElse(null).constructStats(this, stack).getDestroySpeed() : 1f;
	}
	
	@Override
	public int getHarvestLevel(ItemStack stack, ToolType tool, PlayerEntity player, BlockState blockState)
	{
		LazyOptional<IToolDefinition> optional = stack.getCapability(ToolDefinitionCapability.TOOL_DEF_CAP);
		Stats stats = optional.resolve().get().constructStats(this, stack);
		return optional.resolve().get().getToolType().getName().equals(tool.getName()) ? stats.getHarvestLevel() : -1;
	}
	
	@Override
	public Set<ToolType> getToolTypes(ItemStack stack)
	{
		return ImmutableSet.of(stack.getCapability(ToolDefinitionCapability.TOOL_DEF_CAP).orElse(null).getToolType());
	}
	
	@Override
	public ITextComponent getDisplayName(ItemStack stack)
	{
		String txt = (new TranslationTextComponent("tooltype." + stack.getCapability(ToolDefinitionCapability.TOOL_DEF_CAP).orElseGet(null).getToolType().getName())).getString();
		//String txt = new TranslationTextComponent("item.deepnether.dylithite_ingot").getFormattedText();
		return new StringTextComponent(ForgeRegistries.ITEMS.getValue(new ResourceLocation(stack.getCapability(ToolDefinitionCapability.TOOL_DEF_CAP).orElseGet(null).getHead().getBoundItem())).getName().getString() + " " + txt);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		LazyOptional<IToolDefinition> optional = stack.getCapability(ToolDefinitionCapability.TOOL_DEF_CAP);
		
		if(optional.isPresent())
		{
			tooltip.addAll(optional.orElse(null).getToolTip());
		}
	}
	
	private HashMap<CommonToolDescriptor, Multimap<Attribute, AttributeModifier>> attributeMap = new HashMap<>();
	
	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack)
	{
		//Multimap<Attribute, AttributeModifier> basicAttributes = stack.getAttributeModifiers(slot);
		
		if(slot == EquipmentSlotType.MAINHAND)
		{
			IToolDefinition toolDefinition = stack.getCapability(ToolDefinitionCapability.TOOL_DEF_CAP).resolve().get();
			
			return attributeMap.computeIfAbsent(new CommonToolDescriptor(toolDefinition.getHead(), toolDefinition.getStick()), descriptor ->
			{
				com.google.common.collect.ImmutableListMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableListMultimap.builder();
				Stats stats = toolDefinition.constructStats(this, stack);
				stats.getAttributesToApply().forEach((attribute) -> builder.put(attribute.name, attribute.modifier));
				builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier("attackDamage", stats.getAttackDamage(), Operation.ADDITION));
				return builder.build();
			});
		}
		
		return super.getAttributeModifiers(slot, stack);
	}
	
//	@Override
//	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack)
//	{
//		Multimap<Attribute, AttributeModifier> map = super.getAttributeModifiers(slot, stack);
//		if(slot != EquipmentSlotType.MAINHAND)
//			return map;
//		
//		LazyOptional<IToolDefinition> optional = stack.getCapability(ToolDefinitionCapability.TOOL_DEF_CAP);
//		Stats stats = optional.resolve().get().constructStats(this, stack);
//		
//		// We're not allowed to do this!
////		tupelLoop:
////		for(AttributeTulpel tulpel : stats.getAttributesToApply())
////		{
////			if(tulpel.name == null || tulpel.op == null || tulpel.modifier == null)
////			{
////				System.out.println("We encountered a bug. Because I'm merciful, I won't crash minecraft, but please report this to the mod dev.");
////				continue tupelLoop;
////			}
////			
////			map.put(tulpel.name, tulpel.modifier);
////		}
////		
////		map.put(Attributes.ATTACK_DAMAGE, new AttributeModifier("attackDamage", stats.getAttackDamage(), Operation.ADDITION));
//		return map;
//	}
}

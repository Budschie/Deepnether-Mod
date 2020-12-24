package de.budschie.deepnether.item;

import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;

import de.budschie.deepnether.capabilities.IToolDefinition;
import de.budschie.deepnether.capabilities.ToolDefinitionCapability;
import de.budschie.deepnether.item.toolModifiers.Stats;
import de.budschie.deepnether.item.toolModifiers.Stats.AttributeTulpel;
import de.budschie.deepnether.main.References;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
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
		
		this.setRegistryName(new ResourceLocation(References.MODID, "common_tool"));
		
		ItemInit.MOD_ITEMS.add(this);
	}
	
	@Override
	public int getMaxDamage(ItemStack stack)
	{
		LazyOptional<IToolDefinition> optional = stack.getCapability(ToolDefinitionCapability.TOOL_DEF_CAP);
		Stats stats = optional.orElse(null).constructStats(this, stack);
		
		return stats.getDurability();
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack)
	{
		return 1;
	}
	
	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state)
	{
		return state.getHarvestTool().getName().equals(stack.getCapability(ToolDefinitionCapability.TOOL_DEF_CAP).orElse(null).getToolType().getName()) ? stack.getCapability(ToolDefinitionCapability.TOOL_DEF_CAP).orElse(null).constructStats(this, stack).getDestroySpeed() : 1f;
	}
	
	@Override
	public int getHarvestLevel(ItemStack stack, ToolType tool, PlayerEntity player, BlockState blockState)
	{
		LazyOptional<IToolDefinition> optional = stack.getCapability(ToolDefinitionCapability.TOOL_DEF_CAP);
		Stats stats = optional.orElse(null).constructStats(this, stack);
		return optional.orElse(null).getToolType().getName().equals(tool.getName()) ? stats.getHarvestLevel() : -1;
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
	
	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack)
	{
		Multimap<Attribute, AttributeModifier> map = super.getAttributeModifiers(slot, stack);
		if(slot != EquipmentSlotType.MAINHAND)
			return map;
		
		LazyOptional<IToolDefinition> optional = stack.getCapability(ToolDefinitionCapability.TOOL_DEF_CAP);
		Stats stats = optional.orElse(null).constructStats(this, stack);
		
		for(AttributeTulpel tulpel : stats.getAttributesToApply())
		{
			map.put(tulpel.name, tulpel.modifier);
		}
		
		map.put(Attributes.ATTACK_DAMAGE, new AttributeModifier("attackDamage", stats.getAttackDamage(), Operation.ADDITION));
		return map;
	}
}

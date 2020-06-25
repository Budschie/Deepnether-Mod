package de.budschie.deepnether.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.registries.ObjectHolder;

public class ModItemGroups 
{
	@ObjectHolder(value = "deepnether:amylithe_ore")
	public static final Item itemBlockAmylithe = null;
	
	@ObjectHolder(value = "deepnether:pickaxe_cnr")
	public static final Item itemPickaxeCNR = null;
	
	@ObjectHolder(value = "deepnether:chest_cnr")
	public static final Item itemArmorCNR = null;
	
	@ObjectHolder(value = "deepnether:amylithe_shard")
	public static final Item itemAmylitheShard = null;
	
	public static final ItemGroup MOD_BLOCKS = new ItemGroup("deepnetherblocks") {
		
		@Override
		public ItemStack createIcon() 
		{
			return new ItemStack(new IItemProvider() {
				
				@Override
				public Item asItem() 
				{
					return itemBlockAmylithe;
				}
			}, 1);
		}
	};
	
	public static final ItemGroup MOD_TOOLS = new ItemGroup("deepnethertools") {
		
		@Override
		public ItemStack createIcon() 
		{
			return new ItemStack(new IItemProvider() {
				
				@Override
				public Item asItem() 
				{
					return itemPickaxeCNR;
				}
			}, 1);
		}
	};
	
	public static final ItemGroup MOD_ARMOR = new ItemGroup("deepnetherarmor") {
		
		@Override
		public ItemStack createIcon() 
		{
			return new ItemStack(new IItemProvider() {
				
				@Override
				public Item asItem() 
				{
					return itemArmorCNR;
				}
			}, 1);
		}
	};
	
	public static final ItemGroup MOD_INGREDIENTS = new ItemGroup("deepnetheringredients") {
		
		@Override
		public ItemStack createIcon() 
		{
			return new ItemStack(new IItemProvider() {
				
				@Override
				public Item asItem() 
				{
					return itemAmylitheShard;
				}
			}, 1);
		}
	};
}

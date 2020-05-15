package de.budschie.deepnether.block;

import de.budschie.deepnether.item.ItemInit;
import de.budschie.deepnether.main.References;
import net.minecraft.block.BlockState;
import net.minecraft.block.LogBlock;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class AncientLog extends LogBlock
{
	public AncientLog(String name, ItemGroup itemGroup)
	{
		super(MaterialColor.ORANGE_TERRACOTTA, ModProperties.MOD_LOG_BLOCK);
		this.setRegistryName(new ResourceLocation(References.MODID, name));
		
		BlockInit.MOD_BLOCKS.add(this);
		ItemInit.MOD_ITEMS.add(new BlockItem(this, new Item.Properties().group(itemGroup)).setRegistryName(getRegistryName()));
	}
	
	@Override
	public boolean isBurning(BlockState state, IBlockReader world, BlockPos pos)
	{
		return true;
	}
}

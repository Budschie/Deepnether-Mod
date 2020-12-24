package de.budschie.deepnether.block;

import java.util.Optional;
import java.util.function.Predicate;

import de.budschie.deepnether.item.ItemInit;
import de.budschie.deepnether.main.References;
import net.minecraft.block.BlockState;
import net.minecraft.block.MushroomBlock;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class ModMushroom extends MushroomBlock
{
	Optional<Predicate<BlockState>> validGround;
	
	public ModMushroom(Properties props, String name, ItemGroup group, Predicate<BlockState> validGround)
	{
		super(props);
		this.setRegistryName(new ResourceLocation(References.MODID, name));
		
		BlockInit.MOD_BLOCKS.add(this);
		ItemInit.MOD_ITEMS.add(new BlockItem(this, new Item.Properties().group(group)).setRegistryName(getRegistryName()));
		
		this.validGround = Optional.of(validGround);
	}
	
	public ModMushroom(Properties props, String name, ItemGroup group)
	{
		super(props);
		this.setRegistryName(new ResourceLocation(References.MODID, name));
		
		BlockInit.MOD_BLOCKS.add(this);
		ItemInit.MOD_ITEMS.add(new BlockItem(this, new Item.Properties().group(group)).setRegistryName(getRegistryName()));
		
		this.validGround = Optional.empty();
	}
	
	@Override
	protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos)
	{
		return validGround.isEmpty() ? super.isValidGround(state, worldIn, pos) : validGround.get().test(state);
	}
}

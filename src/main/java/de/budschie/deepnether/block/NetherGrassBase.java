package de.budschie.deepnether.block;

import de.budschie.deepnether.item.ItemInit;
import de.budschie.deepnether.main.DeepnetherMain;
import de.budschie.deepnether.main.References;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BushBlock;
import net.minecraft.block.GrassBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class NetherGrassBase extends BushBlock
{
	BlockState soil;
	
	protected NetherGrassBase(String name, Properties properties, ItemGroup group, BlockState soil)
	{
		super(properties);
		
		this.setRegistryName(new ResourceLocation(References.MODID, name));
		
		BlockInit.MOD_BLOCKS.add(this);
		ItemInit.MOD_ITEMS.add(new BlockItem(this, new Item.Properties().group(group)).setRegistryName(getRegistryName()));
		
		this.soil = soil;
	}
	
	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		return VoxelShapes.empty();
	}
	
	@Override
	public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type)
	{
		return true;
	}
	
	@Override
	protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos)
	{
		return state == soil;
	}
	
	@Override
	public boolean isTransparent(BlockState state)
	{
		return true;
	}
	
	// normal cube = false
}

package de.budschie.deepnether.block;

import net.minecraft.block.IceBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class ModProperties 
{
	/** Rocks **/
	public static final Properties MOD_STONE_PROPERTY = Properties.create(Material.ROCK).hardnessAndResistance(2.0f, 5.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0);
	public static final Properties MOD_STRONG_STONE_PROPERTY = Properties.create(Material.ROCK).hardnessAndResistance(2.5f, 15.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0);
	public static final Properties MOD_ORE_BASE = Properties.create(Material.ROCK).hardnessAndResistance(5.0f, 15.0f).harvestTool(ToolType.PICKAXE).harvestLevel(2);
	
	/** Dust and sand etc. **/
	public static final Properties MOD_BASE_SAND = Properties.create(Material.SAND).hardnessAndResistance(0.65f, 2.5f).harvestTool(ToolType.SHOVEL).harvestLevel(0).sound(SoundType.SAND);
	
	/** Glass and transparent **/
	public static final Properties MOD_CRYSTAL = Properties.create(Material.ROCK).hardnessAndResistance(1.0f, 10000.0f).variableOpacity().notSolid();
	public static final Properties MOD_GLASS_BASE = Properties.create(Material.GLASS).variableOpacity().notSolid().hardnessAndResistance(1.0f, 2.45f);
	
	/** Grass **/
	public static final Properties MOD_GRASS_BLOCK = Properties.create(Material.EARTH).hardnessAndResistance(.65f, 2.5f).variableOpacity();
	public static final Properties MOD_GRASS = Properties.create(Material.PLANTS).hardnessAndResistance(0f, 0f).notSolid().sound(SoundType.PLANT);
	
	/** Planks **/
	public static final Properties MOD_LOG_BLOCK = Properties.create(Material.ROCK).harvestLevel(0).hardnessAndResistance(.65f, 5f);
	
	/** Leaf and small transparent props **/
	public static final Properties MOD_LEAVES = Properties.create(Material.ICE).harvestLevel(0).hardnessAndResistance(0.65f, 30.0f).variableOpacity().notSolid();
	public static final Properties MOD_MUSHROOM = Properties.create(Material.PLANTS).notSolid().hardnessAndResistance(0).sound(SoundType.PLANT);
	
	/** Fluids **/
	public static final Properties MOD_LAVA = Properties.create(Material.LAVA).doesNotBlockMovement().notSolid().noDrops().variableOpacity();
}

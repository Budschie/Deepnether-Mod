package de.budschie.deepnether.block;

import java.util.ArrayList;
import java.util.Random;

import org.apache.logging.log4j.LogManager;

import de.budschie.deepnether.entity.damagesource.DamageSourceLiveSucking;
import de.budschie.deepnether.item.ModToolTypes;
import de.budschie.deepnether.item.ToolGroup;
import de.budschie.deepnether.main.References;
import de.budschie.deepnether.util.ModItemGroups;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.GlassBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.Block.Properties;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.feature.OreFeature;
import net.minecraft.world.gen.feature.structure.OceanMonumentStructure;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.Tags.Blocks;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber(modid = References.MODID, bus = Bus.MOD)
public class BlockInit 
{
	public static ArrayList<Block> MOD_BLOCKS = new ArrayList<>();
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		IForgeRegistry<Block> reg = event.getRegistry();
		
		for(Block block : MOD_BLOCKS)
		{
			reg.register(block);
			
			LogManager.getLogger().info("Registered block " + block.getRegistryName().toString() + ".");
		}
	}
	
	/*
	@SubscribeEvent
	public static void onModelRegistry(ModelRegistryEvent event)
	{
		for(Block block : MOD_BLOCKS)
		{
			ModelLoader.addSpecialModel(new ResourceLocation(block.getRegistryName().getNamespace()+":"+"block/"+block.getRegistryName().getPath()));
		}
	}
	*/
	
	public static final BaseBlock COMPRESSED_NETHERRACK = new BaseBlock(ModProperties.MOD_STONE_PROPERTY, "compressed_netherrack", null, false);
	public static final BaseBlock AMYLITHE_ORE = new BaseBlock(ModProperties.MOD_ORE_BASE, "amylithe_ore", ModItemGroups.MOD_BLOCKS);
	public static final BaseBlock DYLITHITE_ORE = new BaseBlock(ModProperties.MOD_ORE_BASE.harvestTool(ModToolTypes.MOD_PICKAXE).harvestLevel(0), "dylithite_ore", ModItemGroups.MOD_BLOCKS);
	public static final BaseBlock SOUL_DUST = new BaseBlock(ModProperties.MOD_BASE_SAND, "soul_dust", ModItemGroups.MOD_BLOCKS);
	public static final BaseBlock SOUL_BRICK = new BaseBlock(ModProperties.MOD_STRONG_STONE_PROPERTY, "soul_brick", ModItemGroups.MOD_BLOCKS);
	public static final BaseBlock BROKEN_SOUL_BRICK = new BaseBlock(ModProperties.MOD_STONE_PROPERTY, "broken_soul_brick", ModItemGroups.MOD_BLOCKS)
	{
		public void onEntityWalk(net.minecraft.world.World worldIn, net.minecraft.util.math.BlockPos pos, net.minecraft.entity.Entity entityIn) 
		{
			if(!worldIn.isRemote)
			{
				if(new Random(pos.getX()+pos.getY()+pos.getZ()+entityIn.ticksExisted).nextInt(5) == 0)
				{
					destroyBlock(worldIn, pos);
				}
			}
		};
		
		public void onFallenUpon(World worldIn, BlockPos pos, net.minecraft.entity.Entity entityIn, float fallDistance) 
		{
			if(!worldIn.isRemote)
			{
				destroyBlock(worldIn, pos);
			}
		};
		
		private void destroyBlock(World worldIn, BlockPos pos)
		{
			ServerWorld worldServer = (ServerWorld)worldIn;
			worldServer.destroyBlock(pos, false);
			worldServer.playSound(null, pos, SoundEvents.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, SoundCategory.BLOCKS, 1.5f, (new Random().nextFloat()-0.5f)*2);
		}
	};
	
	public static final BaseBlock STRONG_SOUL_BRICK = new BaseBlock(ModProperties.MOD_STRONG_STONE_PROPERTY, "strong_soul_brick", ModItemGroups.MOD_BLOCKS)
	{
		@Override
		public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) 
		{
			for(int i = 0; i <rand.nextInt(3)+1; i++)
				worldIn.addParticle(ParticleTypes.LARGE_SMOKE, pos.getX() + rand.nextFloat(), pos.getY()+1, pos.getZ() + rand.nextFloat(), 0, rand.nextDouble()/4, 0);
			
			for(int j = 0; j < rand.nextInt(30); j++)
				worldIn.addParticle(ParticleTypes.SMOKE, pos.getX() + rand.nextFloat(), pos.getY()+1, pos.getZ() + rand.nextFloat(), 0, rand.nextDouble()/4, 0);
		};
		
		public void onEntityWalk(World worldIn, BlockPos pos, net.minecraft.entity.Entity entityIn) 
		{
			if(worldIn.isRemote)
				return;
			
			Random rand = new Random();
			
			if(rand.nextInt(5) > 0)
			{
				if(entityIn instanceof ServerPlayerEntity)
				{
					ServerPlayerEntity player = (ServerPlayerEntity) entityIn;
					
					if(!player.isCreative())
					{
						int health = (new Random(pos.getX() * pos.getZ() + pos.getY()).nextInt(7) + 1);
						player.attackEntityFrom(new DamageSourceLiveSucking(), health);
					}
				}
				else
				{
					int health = (new Random(pos.getX() * pos.getZ() + pos.getY()).nextInt(2) + 1);
					entityIn.attackEntityFrom(new DamageSourceLiveSucking(), health);
				}
			}
		};
	};
	
	public static final BaseBlock HOT_SOUL_BRICK = new BaseBlock(ModProperties.MOD_STRONG_STONE_PROPERTY, "hot_soul_brick", ModItemGroups.MOD_BLOCKS)
	{
		public void onEntityWalk(net.minecraft.world.World worldIn, net.minecraft.util.math.BlockPos pos, net.minecraft.entity.Entity entityIn) 
		{
			entityIn.setFire(2);
		};
	};
	
	public static final BaseBlock HOT_STONE = new BaseBlock(ModProperties.MOD_STONE_PROPERTY, "hot_stone", ModItemGroups.MOD_BLOCKS)
	{
		
		public void onEntityWalk(World worldIn, BlockPos pos, net.minecraft.entity.Entity entityIn) 
		{
			entityIn.setFire(1);
		};
	};
	
	public static final ModGlassBaseBlock FIREPROOF_GLASS = new ModGlassBaseBlock(ModProperties.MOD_GLASS_BASE.hardnessAndResistance(1.0f, 101000.0f), "fireproof_glass", ModItemGroups.MOD_BLOCKS);
	public static final ModGlassBaseBlock SHINING_FIREPROOF_GLASS = new ModGlassBaseBlock(ModProperties.MOD_GLASS_BASE.lightValue(14), "shining_fireproof_glass", ModItemGroups.MOD_BLOCKS);
	
	public static final NetherCrystalBlockBase BLOCK_NETHER_CRYSTAL_RED = new NetherCrystalBlockBase("red");
	public static final NetherCrystalBlockBase BLOCK_NETHER_CRYSTAL_BLUE = new NetherCrystalBlockBase("blue");
	public static final NetherCrystalBlockBase BLOCK_NETHER_CRYSTAL_GREEN = new NetherCrystalBlockBase("green");
	public static final NetherCrystalBlockBase BLOCK_NETHER_CRYSTAL_PURPLE = new NetherCrystalBlockBase("purple");
	public static final NetherCrystalBlockBase BLOCK_NETHER_CRYSTAL_YELLOW = new NetherCrystalBlockBase("yellow");
	
	public static final NetherGrassBlockBase NETHER_DUST_GRASS_BLOCK = new NetherGrassBlockBase(ModProperties.MOD_GRASS_BLOCK.sound(SoundType.SAND), "nether_dust_grass_block", ModItemGroups.MOD_BLOCKS)
	{

		@Override
		public boolean isSoil(BlockState state)
		{
			return state == SOUL_DUST.getDefaultState();
		}

		@Override
		public void ungrow(BlockPos pos, World worldIn)
		{
			worldIn.setBlockState(pos, SOUL_DUST.getDefaultState());
		}

		@Override
		public void grow(BlockPos pos, World worldIn)
		{
			worldIn.setBlockState(pos, NETHER_DUST_GRASS_BLOCK.getDefaultState());
		}
	};
	
	public static final NetherBlastFurnaceBlock DEEP_NETHER_BLAST_FURNACE = new NetherBlastFurnaceBlock(ModProperties.MOD_STONE_PROPERTY, "deep_nether_blast_furnace", ModItemGroups.MOD_BLOCKS);
	public static final NetherGrassBase NETHER_DUST_GRASS = new NetherGrassBase("nether_dust_grass", ModProperties.MOD_GRASS, ModItemGroups.MOD_BLOCKS, NETHER_DUST_GRASS_BLOCK.getDefaultState());
	public static final AncientLog ANCIENT_LOG = new AncientLog("ancient_log", ModItemGroups.MOD_BLOCKS);
	public static final ModLeavesBlock ANCIENT_LEAVES = new ModLeavesBlock(ModProperties.MOD_LEAVES, "ancient_leaves", ModItemGroups.MOD_BLOCKS);
	public static final ModLeavesBlock ANCIENT_WITHERED_LEAVES = new ModLeavesBlock(ModProperties.MOD_LEAVES, "ancient_leaves_withered", ModItemGroups.MOD_BLOCKS);
}

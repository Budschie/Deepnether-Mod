package de.budschie.deepnether.block;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;

import de.budschie.deepnether.block.fluids.FluidInit;
import de.budschie.deepnether.entity.damagesource.DamageSourceLiveSucking;
import de.budschie.deepnether.item.ModToolTypes;
import de.budschie.deepnether.main.References;
import de.budschie.deepnether.util.ModItemGroups;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SoundType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber(modid = References.MODID, bus = Bus.MOD)
public class BlockInit 
{
	public static ArrayList<Block> MOD_BLOCKS = new ArrayList<>();
	
	public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, References.MODID);
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{		
		// Test
		IForgeRegistry<Block> reg = event.getRegistry();
		
		for(Block block : MOD_BLOCKS)
		{
			reg.register(block);
			
			LogManager.getLogger().info("Registered block " + block.getRegistryName().toString() + ".");
		}
		
		reg.register(HOT_JELLY_FLUID_BLOCK.setRegistryName(new ResourceLocation(References.MODID, "hot_jelly_fluid_block")));
	}
	
	public static final RegistryObject<Block> WITHERED_TREE_LOG = REGISTRY.register("withered_tree_log", () -> new RotatedPillarBlock(Properties.create(Material.ROCK).sound(SoundType.HYPHAE).hardnessAndResistance(2, 0.25f)));
	public static final RegistryObject<Block> SOUL_INFUSED_WITHERED_TREE_LOG = REGISTRY.register("soul_infused_withered_tree_log", () -> new RotatedPillarBlock(Properties.create(Material.ORGANIC).sound(SoundType.HYPHAE).hardnessAndResistance(5, 15f)));
	public static final RegistryObject<ModLeavesBlock> GREEN_FOREST_LEAVES = REGISTRY.register("green_forest_leaves", () -> new ModLeavesBlock(ModProperties.MOD_LEAVES.sound(SoundType.NETHER_VINE_LOWER_PITCH)));

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
		public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) 
		{
			if(!worldIn.isRemote)
			{
				if(new Random(pos.getX()+pos.getY()+pos.getZ()+entityIn.ticksExisted).nextInt(5) == 0)
				{
					destroyBlock(worldIn, pos);
				}
			}
		};
		
		public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) 
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
		
		public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) 
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
		public void onEntityWalk(net.minecraft.world.World worldIn, net.minecraft.util.math.BlockPos pos, Entity entityIn) 
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
	// light level 14
	public static final ModGlassBaseBlock SHINING_FIREPROOF_GLASS = new ModGlassBaseBlock(ModProperties.MOD_GLASS_BASE, "shining_fireproof_glass", ModItemGroups.MOD_BLOCKS);
	
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
	
	public static final BaseBlock FERTILIUM = new BaseBlock(ModProperties.MOD_BASE_SAND, "fertilium_block", ModItemGroups.MOD_BLOCKS);
	
	public static final NetherGrassBlockBase GREEN_FOREST_CNR_GRASS_BLOCK = new NetherGrassBlockBase(ModProperties.MOD_STONE_PROPERTY.sound(SoundType.WET_GRASS), "green_forest_compressed_netherrack_grass_block", ModItemGroups.MOD_BLOCKS)
	{

		@Override
		public boolean isSoil(BlockState state)
		{
			return state == COMPRESSED_NETHERRACK.getDefaultState();
		}

		@Override
		public void ungrow(BlockPos pos, World worldIn)
		{
			worldIn.setBlockState(pos, COMPRESSED_NETHERRACK.getDefaultState());
		}

		@Override
		public void grow(BlockPos pos, World worldIn)
		{
			worldIn.setBlockState(pos, GREEN_FOREST_CNR_GRASS_BLOCK.getDefaultState());
		}
		
		@Override
		public boolean canSpread(BlockState stateBlock, BlockState stateAbove, BlockPos stateBlockPos, ServerWorld worldIn)
		{
			return (stateAbove == Blocks.AIR.getDefaultState() || (stateAbove != null && stateAbove.isTransparent())) && worldIn.getServer().func_244267_aX().getRegistry(Registry.BIOME_KEY).getKey(worldIn.getBiome(stateBlockPos)).equals(new ResourceLocation("deepnether:green_forest_biome"));
		}
	};
	
	public static final NetherGrassBlockBase GREEN_FOREST_FERTILIUM_GRASS_BLOCK = new NetherGrassBlockBase(ModProperties.MOD_GRASS_BLOCK.sound(SoundType.SAND), "green_forest_fertilium_grass_block", ModItemGroups.MOD_BLOCKS)
	{

		@Override
		public boolean isSoil(BlockState state)
		{
			return state == FERTILIUM.getDefaultState();
		}

		@Override
		public void ungrow(BlockPos pos, World worldIn)
		{
			worldIn.setBlockState(pos, FERTILIUM.getDefaultState());
		}

		@Override
		public void grow(BlockPos pos, World worldIn)
		{
			worldIn.setBlockState(pos, GREEN_FOREST_FERTILIUM_GRASS_BLOCK.getDefaultState());
		}
	};
	
	public static final NetherBlastFurnaceBlock DEEP_NETHER_BLAST_FURNACE = new NetherBlastFurnaceBlock(ModProperties.MOD_STONE_PROPERTY, "deep_nether_blast_furnace", ModItemGroups.MOD_BLOCKS);
	public static final NetherGrassBase NETHER_DUST_GRASS = new NetherGrassBase("nether_dust_grass", ModProperties.MOD_GRASS, ModItemGroups.MOD_BLOCKS, NETHER_DUST_GRASS_BLOCK.getDefaultState());
	public static final AncientLog ANCIENT_LOG = new AncientLog("ancient_log", ModItemGroups.MOD_BLOCKS);
	
	public static final RegistryObject<ModLeavesBlock> ANCIENT_LEAVES = REGISTRY.register("ancient_leaves", () -> new ModLeavesBlock(ModProperties.MOD_LEAVES));
	public static final RegistryObject<ModLeavesBlock> ANCIENT_WITHERED_LEAVES = REGISTRY.register("ancient_leaves_withered", () -> new ModLeavesBlock(ModProperties.MOD_LEAVES));
	
	public static final FlowingFluidBlock HOT_JELLY_FLUID_BLOCK = new FlowingFluidBlock(new Supplier<FlowingFluid>()
	{

		@Override
		public FlowingFluid get()
		{
			return (FlowingFluid)FluidInit.HOT_JELLY_FLUID.get();
		}
		
	}, ModProperties.MOD_LAVA);
	
	public static final ModMushroom ANCIENT_MUSHROOM = new ModMushroom(ModProperties.MOD_MUSHROOM, "ancient_mushroom", ModItemGroups.MOD_INGREDIENTS, new Predicate<BlockState>()
	{
		
		@Override
		public boolean test(BlockState t)
		{
			// Singletons -> == test
			return t.getBlock() == BlockInit.COMPRESSED_NETHERRACK;
		}
	});
}

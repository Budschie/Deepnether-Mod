package de.budschie.deepnether.main;

import static de.budschie.deepnether.networking.DeepnetherPacketHandler.INSTANCE;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.budschie.deepnether.biomes.BiomeRegistry;
import de.budschie.deepnether.biomes.biome_data_handler.BiomeDataHandler;
import de.budschie.deepnether.biomes.biome_data_handler.DeepnetherBiomeData;
import de.budschie.deepnether.biomes.biome_data_handler.GreenForestBiomeData;
import de.budschie.deepnether.biomes.biome_data_handler.SoulDesertBiomeData;
import de.budschie.deepnether.block.BlockInit;
import de.budschie.deepnether.block.fluids.FluidInit;
import de.budschie.deepnether.capabilities.ToolDefinitionCapability;
import de.budschie.deepnether.container.DeepNetherBlastFurnaceContainer;
import de.budschie.deepnether.dimension.DeepnetherBiomeProvider;
import de.budschie.deepnether.dimension.DeepnetherChunkGenerator;
import de.budschie.deepnether.entity.renders.EntityRenderInit;
import de.budschie.deepnether.features.ConfiguredFeatureRegistry;
import de.budschie.deepnether.features.FeatureRegistry;
import de.budschie.deepnether.features.placements.PlacementRegistry;
import de.budschie.deepnether.gui.NetherBlastFurnaceGUI;
import de.budschie.deepnether.item.ItemInit;
import de.budschie.deepnether.item.ToolUsableItemRegistry;
import de.budschie.deepnether.item.recipes.ToolRecipeSerializerRegistry;
import de.budschie.deepnether.networking.FogSeedMessageRecieve;
import de.budschie.deepnether.networking.PlayIntroMessage;
import de.budschie.deepnether.networking.PullFogMessage;
import de.budschie.deepnether.networking.StructureIDPacket;
import de.budschie.deepnether.tileentities.RecipesDeepnetherBlastFurnace;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.Features;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(References.MODID)
public class DeepnetherMain
{
	
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public DeepnetherMain() 
    {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        
       // DimensionInit.MOD_DIM_DREG.register(FMLJavaModLoadingContext.get().getModEventBus());
        
      //  Features.FEATURES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ToolRecipeSerializerRegistry.DEF_REG_RECIPE.register(FMLJavaModLoadingContext.get().getModEventBus());
        FluidInit.DEF_REG_FLUID.register(FMLJavaModLoadingContext.get().getModEventBus());
        BiomeRegistry.REGISTRY.register(FMLJavaModLoadingContext.get().getModEventBus());
        BlockInit.REGISTRY.register(FMLJavaModLoadingContext.get().getModEventBus());
        ItemInit.REGISTRY.register(FMLJavaModLoadingContext.get().getModEventBus());
        FeatureRegistry.REGISTRY.register(FMLJavaModLoadingContext.get().getModEventBus());
        PlacementRegistry.REGISTRY.register(FMLJavaModLoadingContext.get().getModEventBus());
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        
        // Features.BLUE_ICE
     }
    
    int disc = 0;
    //preinit
    private void setup(final FMLCommonSetupEvent event)
    {
    	System.out.println("Setup...");
    	ToolDefinitionCapability.register();
    	
        // some preinit code
    	INSTANCE.<FogSeedMessageRecieve>registerMessage(disc++, FogSeedMessageRecieve.class, FogSeedMessageRecieve::encodeAtServer, FogSeedMessageRecieve::decodeAtClient, FogSeedMessageRecieve::handleAtClient);
    	INSTANCE.<PullFogMessage>registerMessage(disc++, PullFogMessage.class, PullFogMessage::pull, PullFogMessage::pulled, PullFogMessage::handleAtServer);
    	INSTANCE.<StructureIDPacket>registerMessage(disc++, StructureIDPacket.class, StructureIDPacket::encodeAtServer, StructureIDPacket::decodeAtClient, StructureIDPacket::handleAtClient);
    	INSTANCE.<PlayIntroMessage>registerMessage(disc++, PlayIntroMessage.class, PlayIntroMessage::encodeAtServer, PlayIntroMessage::decodeAtClient, PlayIntroMessage::handleAtClient);
    	//LOGGER.info("HELLO FROM PREINIT");
        //LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    	RecipesDeepnetherBlastFurnace.registerModRecipes();
    	
    	Registry.register(Registry.CHUNK_GENERATOR_CODEC, "deepnether:deepnether_generator", DeepnetherChunkGenerator.CODEC);
    	Registry.register(Registry.BIOME_PROVIDER_CODEC, "deepnether:deepnether_biome_provider", DeepnetherBiomeProvider.CODEC);
    	
    	ConfiguredFeatureRegistry.registerConfiguredFeatures();
    	
    	// RegistryKey.getOrCreateKey(Registry.BIOME_KEY, new ResourceLocation(References.MODID, "green_forest_biome"));
    	
    	ToolUsableItemRegistry.init();
    	
    	BiomeDataHandler.addBiomeData(new ResourceLocation(References.MODID, "green_forest_biome"), new GreenForestBiomeData());
    	BiomeDataHandler.addBiomeData(new ResourceLocation(References.MODID, "deepnether_biome"), new DeepnetherBiomeData());
    	BiomeDataHandler.addBiomeData(new ResourceLocation(References.MODID, "soul_desert_biome"), new SoulDesertBiomeData());
    }

    private void doClientStuff(final FMLClientSetupEvent event) 
    {
    	RenderTypeLookup.setRenderLayer(BlockInit.NETHER_DUST_GRASS_BLOCK, RenderType.getCutoutMipped());
    	RenderTypeLookup.setRenderLayer(BlockInit.GREEN_FOREST_FERTILIUM_GRASS_BLOCK, RenderType.getCutoutMipped());
    	RenderTypeLookup.setRenderLayer(BlockInit.GREEN_FOREST_CNR_GRASS_BLOCK, RenderType.getCutoutMipped());
    	RenderTypeLookup.setRenderLayer(BlockInit.NETHER_DUST_GRASS, RenderType.getCutoutMipped());
    	RenderTypeLookup.setRenderLayer(BlockInit.BLOCK_NETHER_CRYSTAL_BLUE, RenderType.getTranslucent());
    	RenderTypeLookup.setRenderLayer(BlockInit.BLOCK_NETHER_CRYSTAL_BLUE, RenderType.getTranslucent());
    	RenderTypeLookup.setRenderLayer(BlockInit.BLOCK_NETHER_CRYSTAL_GREEN, RenderType.getTranslucent());
    	RenderTypeLookup.setRenderLayer(BlockInit.BLOCK_NETHER_CRYSTAL_PURPLE, RenderType.getTranslucent());
    	RenderTypeLookup.setRenderLayer(BlockInit.BLOCK_NETHER_CRYSTAL_RED, RenderType.getTranslucent());
    	RenderTypeLookup.setRenderLayer(BlockInit.BLOCK_NETHER_CRYSTAL_YELLOW, RenderType.getTranslucent());
    	RenderTypeLookup.setRenderLayer(BlockInit.ANCIENT_LEAVES, RenderType.getTranslucent());
    	RenderTypeLookup.setRenderLayer(BlockInit.ANCIENT_WITHERED_LEAVES, RenderType.getTranslucent());
    	RenderTypeLookup.setRenderLayer(BlockInit.ANCIENT_MUSHROOM, RenderType.getCutout());
    	
    	ScreenManager.registerFactory(DeepNetherBlastFurnaceContainer.TYPE, NetherBlastFurnaceGUI.FACTORY);
    	
    	EntityRenderInit.registerEntityRenders();   	
    	
        // do something that can only be done on the client
        //LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        //InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
    	/*
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
                */
    }
    
    public static MinecraftServer server;
    
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) 
    {
    	server = event.getServer();
    }
}

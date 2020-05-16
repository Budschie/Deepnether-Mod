package de.budschie.deepnether.main;

import static de.budschie.deepnether.networking.DeepnetherPacketHandler.INSTANCE;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.budschie.deepnether.biomes.BiomeFeatureAdder;
import de.budschie.deepnether.block.BlockInit;
import de.budschie.deepnether.commands.CommandPlaceStructure;
import de.budschie.deepnether.commands.CommandSaveSelection;
import de.budschie.deepnether.commands.CommandToggleSel;
import de.budschie.deepnether.container.DeepNetherBlastFurnaceContainer;
import de.budschie.deepnether.dimension.DimensionInit;
import de.budschie.deepnether.entity.renders.EntityRenderInit;
import de.budschie.deepnether.gui.NetherBlastFurnaceGUI;
import de.budschie.deepnether.networking.FogSeedMessageRecieve;
import de.budschie.deepnether.networking.PullFogMessage;
import de.budschie.deepnether.tileentities.RecipesDeepnetherBlastFurnace;
import de.budschie.deepnether.worldgen.Features;
import de.budschie.deepnether.worldgen.structureSaving.StructureDataProviderRegistry;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLConfig;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLLoader;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(References.MODID)
public class DeepnetherMain
{
	
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public DeepnetherMain() 
    {
        Features.FEATURES.register(FMLJavaModLoadingContext.get().getModEventBus());

        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        
        DimensionInit.MOD_DIM_DREG.register(FMLJavaModLoadingContext.get().getModEventBus());

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    int disc = 0;
    //preinit
    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
    	INSTANCE.<FogSeedMessageRecieve>registerMessage(disc++, FogSeedMessageRecieve.class, FogSeedMessageRecieve::encodeAtServer, FogSeedMessageRecieve::decodeAtClient, FogSeedMessageRecieve::handleAtClient);
    	INSTANCE.<PullFogMessage>registerMessage(disc++, PullFogMessage.class, PullFogMessage::pull, PullFogMessage::pulled, PullFogMessage::handleAtServer);
    	//LOGGER.info("HELLO FROM PREINIT");
        //LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    	RecipesDeepnetherBlastFurnace.registerModRecipes();
    	
    	StructureDataProviderRegistry.addEntriesFromRegistry();
    	
    	BiomeFeatureAdder.addFeatures();
    }

    private void doClientStuff(final FMLClientSetupEvent event) 
    {
    	RenderTypeLookup.setRenderLayer(BlockInit.NETHER_DUST_GRASS_BLOCK, RenderType.getCutoutMipped());
    	RenderTypeLookup.setRenderLayer(BlockInit.NETHER_DUST_GRASS, RenderType.getCutoutMipped());
    	RenderTypeLookup.setRenderLayer(BlockInit.BLOCK_NETHER_CRYSTAL_BLUE, RenderType.getTranslucent());
    	RenderTypeLookup.setRenderLayer(BlockInit.BLOCK_NETHER_CRYSTAL_BLUE, RenderType.getTranslucent());
    	RenderTypeLookup.setRenderLayer(BlockInit.BLOCK_NETHER_CRYSTAL_GREEN, RenderType.getTranslucent());
    	RenderTypeLookup.setRenderLayer(BlockInit.BLOCK_NETHER_CRYSTAL_PURPLE, RenderType.getTranslucent());
    	RenderTypeLookup.setRenderLayer(BlockInit.BLOCK_NETHER_CRYSTAL_RED, RenderType.getTranslucent());
    	RenderTypeLookup.setRenderLayer(BlockInit.BLOCK_NETHER_CRYSTAL_YELLOW, RenderType.getTranslucent());
    	
    	EntityRenderInit.registerEntityRenders();
    	
    	ScreenManager.registerFactory(DeepNetherBlastFurnaceContainer.TYPE, NetherBlastFurnaceGUI.FACTORY);
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
        // do something when the server starts
        //LOGGER.info("HELLO from server starting");
    	server = event.getServer();
    	CommandToggleSel.register(event.getCommandDispatcher());
    	CommandSaveSelection.registerCommandTpDim(event.getCommandDispatcher());
    	CommandPlaceStructure.registerCommandTpDim(event.getCommandDispatcher());
    }
}

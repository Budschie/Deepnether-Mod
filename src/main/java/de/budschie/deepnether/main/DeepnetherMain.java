package de.budschie.deepnether.main;

import static de.budschie.deepnether.networking.DeepnetherPacketHandler.INSTANCE;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.budschie.deepnether.biomes.BiomeFeatureAdder;
import de.budschie.deepnether.block.BlockInit;
import de.budschie.deepnether.block.fluids.FluidInit;
import de.budschie.deepnether.capabilities.ToolDefinitionCapability;
import de.budschie.deepnether.commands.CommandPlaceStructure;
import de.budschie.deepnether.commands.CommandPlaceTree;
import de.budschie.deepnether.commands.CommandPlayIntro;
import de.budschie.deepnether.commands.CommandSaveSelection;
import de.budschie.deepnether.commands.CommandToggleSel;
import de.budschie.deepnether.container.DeepNetherBlastFurnaceContainer;
import de.budschie.deepnether.dimension.DimensionInit;
import de.budschie.deepnether.entity.renders.EntityRenderInit;
import de.budschie.deepnether.gui.NetherBlastFurnaceGUI;
import de.budschie.deepnether.item.ItemInit;
import de.budschie.deepnether.item.ToolUsableItemRegistry;
import de.budschie.deepnether.item.recipes.ToolRecipeSerializerRegistry;
import de.budschie.deepnether.networking.FogSeedMessageRecieve;
import de.budschie.deepnether.networking.PlayIntroMessage;
import de.budschie.deepnether.networking.PullFogMessage;
import de.budschie.deepnether.networking.StructureIDPacket;
import de.budschie.deepnether.tileentities.RecipesDeepnetherBlastFurnace;
import de.budschie.deepnether.worldgen.BigTreeFeature;
import de.budschie.deepnether.worldgen.Features;
import de.budschie.deepnether.worldgen.structureSaving.StructureDataProviderRegistry;
import net.minecraft.block.IceBlock;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.GameRegistry;
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
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        
        DimensionInit.MOD_DIM_DREG.register(FMLJavaModLoadingContext.get().getModEventBus());
        
        Features.FEATURES.register(FMLJavaModLoadingContext.get().getModEventBus());
        Features.PLACEMENTS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ToolRecipeSerializerRegistry.DEF_REG_RECIPE.register(FMLJavaModLoadingContext.get().getModEventBus());
        FluidInit.DEF_REG_FLUID.register(FMLJavaModLoadingContext.get().getModEventBus());

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
     }
    
    int disc = 0;
    //preinit
    private void setup(final FMLCommonSetupEvent event)
    {
    	ToolDefinitionCapability.register();

        // some preinit code
    	INSTANCE.<FogSeedMessageRecieve>registerMessage(disc++, FogSeedMessageRecieve.class, FogSeedMessageRecieve::encodeAtServer, FogSeedMessageRecieve::decodeAtClient, FogSeedMessageRecieve::handleAtClient);
    	INSTANCE.<PullFogMessage>registerMessage(disc++, PullFogMessage.class, PullFogMessage::pull, PullFogMessage::pulled, PullFogMessage::handleAtServer);
    	INSTANCE.<StructureIDPacket>registerMessage(disc++, StructureIDPacket.class, StructureIDPacket::encodeAtServer, StructureIDPacket::decodeAtClient, StructureIDPacket::handleAtClient);
    	INSTANCE.<PlayIntroMessage>registerMessage(disc++, PlayIntroMessage.class, PlayIntroMessage::encodeAtServer, PlayIntroMessage::decodeAtClient, PlayIntroMessage::handleAtClient);
    	//LOGGER.info("HELLO FROM PREINIT");
        //LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    	RecipesDeepnetherBlastFurnace.registerModRecipes();
    	
    	StructureDataProviderRegistry.addEntriesFromRegistry();
    	
    	BiomeFeatureAdder.addFeatures();
    	
    	ToolUsableItemRegistry.init();
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
    	RenderTypeLookup.setRenderLayer(BlockInit.ANCIENT_LEAVES, RenderType.getTranslucent());
    	RenderTypeLookup.setRenderLayer(BlockInit.ANCIENT_WITHERED_LEAVES, RenderType.getTranslucent());
    	
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
        // do something when the server starts
        //LOGGER.info("HELLO from server starting");
    	server = event.getServer();
    	CommandToggleSel.register(event.getCommandDispatcher());
    	CommandSaveSelection.registerCommandTpDim(event.getCommandDispatcher());
    	CommandPlaceStructure.registerCommandTpDim(event.getCommandDispatcher());
    	CommandPlaceTree.register(event.getCommandDispatcher());
    	CommandPlayIntro.register(event.getCommandDispatcher());
    }
}

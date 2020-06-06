package de.budschie.deepnether.gui.intro;

import java.awt.Point;

import de.budschie.deepnether.gui.budschiegui.BlendStep;
import de.budschie.deepnether.gui.budschiegui.EmptyStep;
import de.budschie.deepnether.gui.budschiegui.FillScreenStep;
import de.budschie.deepnether.gui.budschiegui.GuiEndedEvent;
import de.budschie.deepnether.gui.budschiegui.GuiWithSteps;
import de.budschie.deepnether.gui.budschiegui.ImageAnimatedStep;
import de.budschie.deepnether.gui.budschiegui.ImageStep;
import de.budschie.deepnether.main.References;
import de.budschie.deepnether.util.Util.RGBA;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = References.MODID, bus = Bus.FORGE, value = Dist.CLIENT)
public class IntroHandlerClient
{
	static boolean introCurrentlyPlaying = false;
	static GuiWithSteps gui;
	
	@SubscribeEvent
	public static void onPlayIntro(IntroRecievedEvent event)
	{
		/** **/
		if(!introCurrentlyPlaying)
		{
			gui = new GuiWithSteps();
			
			FillScreenStep first = new FillScreenStep();
			first
			.setRGB(new RGBA(0, 0, 0, 255))
			.setFrameDuration(1)
			.attach(new ImageStep()).setAnimatedColor(new RGBA(255, 255, 255, 0), new RGBA(255, 255, 255, 255)).setLocation(new Point(0, 0))
			.setScale(() -> new Point(Minecraft.getInstance().getMainWindow().getScaledWidth(), Minecraft.getInstance().getMainWindow().getScaledHeight()))
			.setImageSize(new Point(800, 450))
			.setImage(new ResourceLocation(References.MODID, "textures/gui/intro/budschie_logo.png"))
			.disableStackAdding()
			.setFrameDuration(60*2)
			.attach(new ImageStep()).setAnimatedColor(new RGBA(255, 255, 255, 255), new RGBA(255, 255, 255, 0)).setLocation(new Point(0, 0))
			.setScale(() -> new Point(Minecraft.getInstance().getMainWindow().getScaledWidth(), Minecraft.getInstance().getMainWindow().getScaledHeight()))
			.setImageSize(new Point(800, 450))
			.setImage(new ResourceLocation(References.MODID, "textures/gui/intro/budschie_logo.png"))
			.setFrameDuration(60)
			.disableStackAdding()
			.attach(new ImageAnimatedStep()).setImage(new ResourceLocation(References.MODID, "textures/gui/intro/presents.png"))
			.setImageSize(new Point(512, 256))
			.setAnimatedColor(new RGBA(255, 255, 255, 0), new RGBA(255, 255, 255, 255))
			.setLocation(() -> new Point(Minecraft.getInstance().getMainWindow().getScaledWidth() / 2  - 450/2, (int) (Minecraft.getInstance().getMainWindow().getScaledHeight() * -0.2f)), () -> new Point(Minecraft.getInstance().getMainWindow().getScaledWidth() / 2 - 450/2, Minecraft.getInstance().getMainWindow().getScaledHeight() / 2))
			.setScale(() -> new Point(Minecraft.getInstance().getMainWindow().getScaledWidth() / 2, Minecraft.getInstance().getMainWindow().getScaledHeight() / 2), () -> new Point(Minecraft.getInstance().getMainWindow().getScaledWidth() / 2, Minecraft.getInstance().getMainWindow().getScaledHeight() / 2))
			.setFrameDuration(60*2)
			.attach(new EmptyStep())
			.setFrameDuration(20)
			.attach(new BlendStep())
			.setAnimatedColor(new RGBA(0, 0, 0, 0), new RGBA(0, 0, 0, 255))
			.setFrameDuration(60*2)
			.attach(new ImageStep())
			.setImage(new ResourceLocation(References.MODID, "textures/gui/intro/deepnether_logo.png"))
			.setAnimatedColor(new RGBA(0, 0, 0, 255), new RGBA(255, 255, 255, 255))
			.setLocation(new Point(0, 0))
			.setImageSize(new Point(800, 450))
			.setScale(() -> new Point(Minecraft.getInstance().getMainWindow().getScaledWidth(), Minecraft.getInstance().getMainWindow().getScaledHeight()))
			.clearScene()
			.disableStackAdding()
			.setFrameDuration(60*8)
			.attach(new ImageStep())
			.setImage(new ResourceLocation(References.MODID, "textures/gui/intro/deepnether_logo.png"))
			.setLocation(new Point(0, 0))
			.setImageSize(new Point(800, 450))
			.setScale(() -> new Point(Minecraft.getInstance().getMainWindow().getScaledWidth(), Minecraft.getInstance().getMainWindow().getScaledHeight()))
			.setFrameDuration(60*2)
			.disableStackAdding()
			.attach(new ImageStep())
			.setImage(new ResourceLocation(References.MODID, "textures/gui/intro/deepnether_logo.png"))
			.setLocation(new Point(0, 0))
			.setImageSize(new Point(800, 450))
			.setAnimatedColor(new RGBA(255, 255, 255, 255), new RGBA(255, 255, 255, 0))
			.setScale(() -> new Point(Minecraft.getInstance().getMainWindow().getScaledWidth(), Minecraft.getInstance().getMainWindow().getScaledHeight()))
			.setFrameDuration(60)
			.disableStackAdding();
			
			gui.playStep(first);
			
			introCurrentlyPlaying = true;
		}
	}
	
	@SubscribeEvent
	public static void onDrawGui(RenderGameOverlayEvent.Post event)
	{
		if(introCurrentlyPlaying)
		{
			if(gui != null)
			{
				//event.setCanceled(true);
				
				if(event.getType() == ElementType.HOTBAR)
				{
					gui.drawWithUpdate();
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void onGuiEndedPlaying(GuiEndedEvent event)
	{
		if(event.gui == gui && gui != null)
		{
			gui = null;
			introCurrentlyPlaying = false;
			System.out.println("ENDED");
		}
	}
}

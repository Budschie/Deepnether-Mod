package de.budschie.deepnether.gui;

import de.budschie.deepnether.networking.StructureIDPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeBuffers;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(value = Dist.CLIENT)
public class GuiEvents
{
	@SubscribeEvent
	public static void onDrawGui(RenderGameOverlayEvent.Post event)
	{
		// Minecraft.getInstance().fontRenderer.renderString(Integer.valueOf(StructureIDPacket.currentId).toString(), .5f, .25f, 15218744, false, event.getMatrixStack().getLast().getMatrix(), RenderType.getText(new ResourceLocation("")), false, 0, 0);
	}
}

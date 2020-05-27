package de.budschie.deepnether.worldgen.structureSaving;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.vertex.VertexBuilderUtils;

import de.budschie.deepnether.main.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE, modid = References.MODID, value = Dist.CLIENT)
public class AABBDebug
{
	public static void onRender(RenderWorldLastEvent event)
	{
		if(Minecraft.getInstance().world != null)
		{
			// System.out.println("Rendering...");
			ArrayList<StructureData> structures = StructureDataHandler.getStructures(Minecraft.getInstance().world);
			if(structures == null)
				return;
			for(StructureData data : structures)
			{
				AxisAlignedBB aabb = data.getTranslatedAABB();
				RenderSystem.lineWidth(10.0f);
		        IRenderTypeBuffer.Impl buffer = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();
		        if(buffer == null)
		        	return;
		        IVertexBuilder builder = buffer.getBuffer(RenderType.LINES);
		        MatrixStack matrixStack = event.getMatrixStack();
		        PlayerEntity player = Minecraft.getInstance().player;
		        double x = player.lastTickPosX + (player.getPosX() - player.lastTickPosX) * event.getPartialTicks();
		        double y = player.lastTickPosY + (player.getPosY() - player.lastTickPosY) * event.getPartialTicks();
		        double z = player.lastTickPosZ + (player.getPosZ() - player.lastTickPosZ) * event.getPartialTicks();

		        matrixStack.push();
		        matrixStack.translate(-x, -y, -z);
		        WorldRenderer.drawBoundingBox(matrixStack, builder, aabb.minX, aabb.minY, aabb.minZ, aabb.maxX, aabb.maxY, aabb.maxZ, 1.0f, 1.0f, 1.0f, 1.0f);
		        matrixStack.pop();
		        buffer.finish(RenderType.LINES);
			}
		}
	}
}

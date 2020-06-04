package de.budschie.deepnether.item.rendering;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import de.budschie.deepnether.capabilities.IToolDefinition;
import de.budschie.deepnether.capabilities.ToolDefinitionCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.MapItemRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class DynamicCommonToolRendering extends ItemStackTileEntityRenderer
{
	DynamicTextureCache cache = new DynamicTextureCache();
	
	
	@Override
	public void render(ItemStack itemStackIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
	{
		// MapItemRenderer
		Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();
		IToolDefinition data = itemStackIn.getCapability(ToolDefinitionCapability.TOOL_DEF_CAP).orElse(null);
		DynamicTexture tex = cache.add(data.getStick(), data.getHead(), data.getToolType());
		IVertexBuilder builder = bufferIn.getBuffer(RenderType.getText(Minecraft.getInstance().getTextureManager().getDynamicTextureLocation("tex_dyn_" + data.getStick().getBoundItem().replace(':', '_') + data.getHead().getBoundItem().replace(':', '_'), tex)));
		matrixStackIn.push();
		/*
        builder.pos(matrix4f, 0.0F, .5F, -0.5F).color(255, 255, 255, 255).tex(0.0F, 1.0F).lightmap(combinedLightIn).normal(0, 1, 0).endVertex();
        builder.pos(matrix4f, .5F, .5F, -0.5F).color(255, 255, 255, 255).tex(1.0F, 1.0F).lightmap(combinedLightIn).normal(1, 1, 0).endVertex();
        builder.pos(matrix4f, .5F, 0.0F, -0.5F).color(255, 255, 255, 255).tex(1.0F, 0.0F).lightmap(combinedLightIn).normal(1, 0, 0).endVertex();
        builder.pos(matrix4f, 0.0F, 0.0F, -0.5F).color(255, 255, 255, 255).tex(0.0F, 0.0F).lightmap(combinedLightIn).normal(0, -1, 0).endVertex();
        */
		DynamicItemCreator c = new DynamicItemCreator();
		c.build(tex, builder, 1f, combinedLightIn, matrix4f);
        matrixStackIn.pop();
	}
}

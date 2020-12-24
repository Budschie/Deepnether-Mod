package de.budschie.deepnether.item.rendering;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import de.budschie.deepnether.capabilities.IToolDefinition;
import de.budschie.deepnether.capabilities.ToolDefinitionCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;

public class DynamicCommonToolRendering extends ItemStackTileEntityRenderer
{
	DynamicTextureCache cache = new DynamicTextureCache();
	public static final boolean twoD = false;
	
	//render
	@Override
	public void func_239207_a_(ItemStack itemStackIn, TransformType transformerType, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
	{
		// MapItemRenderer
		IToolDefinition data = itemStackIn.getCapability(ToolDefinitionCapability.TOOL_DEF_CAP).orElse(null);
		DynamicTexture tex = cache.add(data.getStick(), data.getHead(), data.getToolType());
		IVertexBuilder builder = bufferIn.getBuffer(RenderType.getEntityTranslucent(Minecraft.getInstance().getTextureManager().getDynamicTextureLocation("tex_dyn_" + data.getStick().getBoundItem().replace(':', '_') + data.getHead().getBoundItem().replace(':', '_'), tex)));
		matrixStackIn.push();
		matrixStackIn.translate(0, 1, .35);
		matrixStackIn.rotate(new Quaternion(90, 0, 0, true));
		Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();
		Matrix3f m3f = matrixStackIn.getLast().getNormal();
		
		/*
        builder.pos(matrix4f, 0.0F, .5F, -0.5F).color(255, 255, 255, 255).tex(0.0F, 1.0F).lightmap(combinedLightIn).normal(0, 1, 0).endVertex();
        builder.pos(matrix4f, .5F, .5F, -0.5F).color(255, 255, 255, 255).tex(1.0F, 1.0F).lightmap(combinedLightIn).normal(1, 1, 0).endVertex();
        builder.pos(matrix4f, .5F, 0.0F, -0.5F).color(255, 255, 255, 255).tex(1.0F, 0.0F).lightmap(combinedLightIn).normal(1, 0, 0).endVertex();
        builder.pos(matrix4f, 0.0F, 0.0F, -0.5F).color(255, 255, 255, 255).tex(0.0F, 0.0F).lightmap(combinedLightIn).normal(0, -1, 0).endVertex();
        */
		if(twoD)
		{
			/*
			 * 			builder.pos(m4f, pos1.getX(), pos1.getY(), pos1.getZ()).color(255, 255, 255, 255).tex(u[0], v[0]).overlay(overlay).lightmap(lightmap).normal(m3f, normal.getX(), normal.getY(), normal.getZ()).endVertex();
			builder.pos(m4f, pos4.getX(), pos4.getY(), pos4.getZ()).color(255, 255, 255, 255).tex(u[3], v[3]).overlay(overlay).lightmap(lightmap).normal(m3f, normal.getX(), normal.getY(), normal.getZ()).endVertex();
			builder.pos(m4f, pos3.getX(), pos3.getY(), pos3.getZ()).color(255, 255, 255, 255).tex(u[2], v[2]).overlay(overlay).lightmap(lightmap).normal(m3f, normal.getX(), normal.getY(), normal.getZ()).endVertex();
			builder.pos(m4f, pos2.getX(), pos2.getY(), pos2.getZ()).color(255, 255, 255, 255).tex(u[1], v[1]).overlay(overlay).lightmap(lightmap).normal(m3f, normal.getX(), normal.getY(), normal.getZ()).endVertex();

			 */
			
			
			
			builder.pos(matrix4f, 0, 0, 0).color(255, 255, 255, 255).tex(0, 0).overlay(combinedOverlayIn).lightmap(combinedLightIn).normal(0, 1, 0).endVertex();
			builder.pos(matrix4f, 0, 0, 1).color(255, 255, 255, 255).tex(0, 1).overlay(combinedOverlayIn).lightmap(combinedLightIn).normal(0, 1, 0).endVertex();
			builder.pos(matrix4f, 1, 0, 1).color(255, 255, 255, 255).tex(1, 1).overlay(combinedOverlayIn).lightmap(combinedLightIn).normal(0, 1, 0).endVertex();
			builder.pos(matrix4f, 1, 0, 0).color(255, 255, 255, 255).tex(1, 0).overlay(combinedOverlayIn).lightmap(combinedLightIn).normal(0, 1, 0).endVertex();
		}
		else
		{
			DynamicItemCreator c = new DynamicItemCreator();
			c.build(tex, builder, 1f/16f, combinedLightIn, matrix4f, m3f, combinedOverlayIn);
		}
        matrixStackIn.pop();
	}
}

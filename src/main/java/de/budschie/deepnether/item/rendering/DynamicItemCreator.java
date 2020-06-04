package de.budschie.deepnether.item.rendering;

import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;

public class DynamicItemCreator
{
	public void build(DynamicTexture texture, IVertexBuilder builder, float size, int combinedLightIn, Matrix4f matrix)
	{
		// To strips
		for(int y = 0; y < texture.getTextureData().getHeight(); y++)
		{
			int fromX = -1;
			int toX = -1;
			
			for(int x = 0; x < texture.getTextureData().getWidth(); x++)
			{
				texture.getTextureData();
				if(NativeImage.getAlpha(texture.getTextureData().getPixelRGBA(x, y)) > 100)
				{
					if(fromX == -1)
					{
						fromX = x;
					}
					else
					{
						toX = x;
					}
				}
				else
				{
					if(fromX != -1)
					{
						float uAdd = 1f / texture.getTextureData().getWidth();
						float vAdd = 1f / texture.getTextureData().getHeight();
						beginAndEnd(matrix, fromX, toX, y, builder, size, uAdd * x, vAdd * y, uAdd, vAdd, combinedLightIn);
					}
				}
			}
		}
	}
	
	public void beginAndEnd(Matrix4f matrix, int fromX, int toX, int y, IVertexBuilder builder, float size, float uStart, float vStart, float uAdd, float vAdd, int combinedLightIn)
	{
		/*
		builder.pos(matrix, fromX * size, 0, y * size).color(255, 255, 255, 255).tex(uStart, vStart).lightmap(combinedLightIn).endVertex();
		builder.pos(matrix, fromX * size + size, 0, y * size).color(255, 255, 255, 255).tex(uStart + uAdd, vStart).lightmap(combinedLightIn).endVertex();
		builder.pos(matrix, fromX * size, 0, y * size + size).color(255, 255, 255, 255).tex(uStart, vStart + vAdd).lightmap(combinedLightIn).endVertex();
		builder.pos(matrix, fromX * size + size, 0, y * size + size).color(255, 255, 255, 255).tex(uStart + uAdd, vStart + vAdd).lightmap(combinedLightIn).endVertex();
		
		builder.pos(matrix, fromX * size, size, y * size).color(255, 255, 255, 255).tex(uStart, vStart).lightmap(combinedLightIn).endVertex();
		builder.pos(matrix, fromX * size + size, size, y * size).color(255, 255, 255, 255).tex(uStart + uAdd, vStart).lightmap(combinedLightIn).endVertex();
		builder.pos(matrix, fromX * size, size, y * size + size).color(255, 255, 255, 255).tex(uStart, vStart + vAdd).lightmap(combinedLightIn).endVertex();
		builder.pos(matrix, fromX * size + size, size, y * size + size).color(255, 255, 255, 255).tex(uStart + uAdd, vStart + vAdd).lightmap(combinedLightIn).endVertex();
		
		builder.pos(matrix, fromX * size, 0, y * size).color(255, 255, 255, 255).tex(uStart, vStart).lightmap(combinedLightIn).endVertex();
		builder.pos(matrix, fromX * size + size, 0, y * size).color(255, 255, 255, 255).tex(uStart + uAdd, vStart).lightmap(combinedLightIn).endVertex();
		builder.pos(matrix, fromX * size, size, y * size).color(255, 255, 255, 255).tex(uStart + uAdd, vStart).lightmap(combinedLightIn).endVertex();
		builder.pos(matrix, fromX * size + size, size, y * size).color(255, 255, 255, 255).tex(uStart + uAdd, vStart).lightmap(combinedLightIn).endVertex();
		
		builder.pos(matrix, fromX * size, 0, y * size + size).color(255, 255, 255, 255).tex(uStart, vStart + vAdd).lightmap(combinedLightIn).endVertex();
		builder.pos(matrix, fromX * size, 0, y * size).color(255, 255, 255, 255).tex(uStart, vStart).lightmap(combinedLightIn).endVertex();
		builder.pos(matrix, fromX * size, size, y * size).color(255, 255, 255, 255).tex(uStart, vStart).lightmap(combinedLightIn).endVertex();
		builder.pos(matrix, fromX * size, size, y * size + size).color(255, 255, 255, 255).tex(uStart, vStart + vAdd).lightmap(combinedLightIn).endVertex();
		*/
	}
}

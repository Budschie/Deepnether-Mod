package de.budschie.deepnether.item.rendering;

import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.model.ZombieModel;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.ItemModelGenerator;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.model.ModelRenderer.ModelBox;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.monster.VexEntity;
import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraftforge.client.model.ItemLayerModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.client.model.pipeline.BakedQuadBuilder;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.server.command.ForgeCommand;

public class DynamicItemCreator
{
	public void build(DynamicTexture texture, IVertexBuilder builder, float size, int combinedLightIn, Matrix4f matrix)
	{
		for(int y = 0; y < texture.getTextureData().getHeight(); y++)
		{
			int fromX = -1;
			int toX = -1;
			
			for(int x = 0; x < texture.getTextureData().getWidth(); x++)
			{
				if(x == (texture.getTextureData().getWidth()-1))
				{
					if(fromX != -1)
					{
						float uAdd = 1f / texture.getTextureData().getWidth();
						float vAdd = 1f / texture.getTextureData().getHeight();
						beginAndEnd(matrix, fromX, toX, y, builder, size, uAdd * x, vAdd * y, uAdd, vAdd, combinedLightIn);
						fromX = -1;
						toX = -1;
					}
				}
				else if(NativeImage.getAlpha(texture.getTextureData().getPixelRGBA(x, y)) > 100)
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
						fromX = -1;
						toX = -1;
					}
				}
			}
		}
	}
	
	public void beginAndEnd(Matrix4f matrix, int fromX, int toX, int y, IVertexBuilder builder, float size, float uStart, float vStart, float uAdd, float vAdd, int combinedLightIn)
	{
		float minX = fromX * size;
		float minY = 0;
		float minZ = y * size;
		
		float maxX = toX * size;
		float maxY = size;
		float maxZ = y * size + size;
		
		/*
		builder.pos(matrix, minX, minY, minZ).color(255, 255, 255, 255).tex(uStart, vStart).lightmap(combinedLightIn).endVertex();
		builder.pos(matrix, minX, minY, maxZ).color(255, 255, 255, 255).tex(uStart, vStart).lightmap(combinedLightIn).endVertex();
		builder.pos(matrix, maxX, minY, minZ).color(255, 255, 255, 255).tex(uStart + uAdd * (maxX - minX), vStart).lightmap(combinedLightIn).endVertex();
		builder.pos(matrix, maxX, minY, maxZ).color(255, 255, 255, 255).tex(uStart + uAdd * (maxX - minX), vStart).lightmap(combinedLightIn).endVertex();
		
		builder.pos(matrix, minX, maxY, minZ).color(255, 255, 255, 255).tex(uStart, vStart).lightmap(combinedLightIn).endVertex();
		builder.pos(matrix, minX, maxY, maxZ).color(255, 255, 255, 255).tex(uStart, vStart).lightmap(combinedLightIn).endVertex();
		builder.pos(matrix, maxX, maxY, minZ).color(255, 255, 255, 255).tex(uStart + uAdd * (maxX - minX), vStart).lightmap(combinedLightIn).endVertex();
		builder.pos(matrix, maxX, maxY, maxZ).color(255, 255, 255, 255).tex(uStart + uAdd * (maxX - minX), vStart).lightmap(combinedLightIn).endVertex();
		*/
		
		//Left right
		Vector3f n1 = Vector3f.XN;
		builder.pos(matrix, minX, maxY, minZ).color(255, 255, 255, 255).tex(uStart, vStart).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(n1.getX(), n1.getY(), n1.getZ()).endVertex();
		builder.pos(matrix, minX, maxY, maxZ).color(255, 255, 255, 255).tex(uStart, vStart).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(n1.getX(), n1.getY(), n1.getZ()).endVertex();
		builder.pos(matrix, minX, minY, maxZ).color(255, 255, 255, 255).tex(uStart, vStart).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(n1.getX(), n1.getY(), n1.getZ()).endVertex();
		builder.pos(matrix, minX, minY, minZ).color(255, 255, 255, 255).tex(uStart, vStart).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(n1.getX(), n1.getY(), n1.getZ()).endVertex();
		
		Vector3f n2 = Vector3f.XP;
		builder.pos(matrix, maxX, maxY, minZ).color(255, 255, 255, 255).tex(uStart + uAdd * (toX - fromX), vStart).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(n2.getX(), n2.getY(), n2.getZ()).endVertex();
		builder.pos(matrix, maxX, maxY, maxZ).color(255, 255, 255, 255).tex(uStart + uAdd * (toX - fromX), vStart).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(n2.getX(), n2.getY(), n2.getZ()).endVertex();
		builder.pos(matrix, maxX, minY, maxZ).color(255, 255, 255, 255).tex(uStart + uAdd * (toX - fromX), vStart).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(n2.getX(), n2.getY(), n2.getZ()).endVertex();
		builder.pos(matrix, maxX, minY, minZ).color(255, 255, 255, 255).tex(uStart + uAdd * (toX - fromX), vStart).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(n2.getX(), n2.getY(), n2.getZ()).endVertex();
		
		
		//Up down
		Vector3f n3 = Vector3f.YN;
		builder.pos(matrix, minX, minY, minZ).color(255, 255, 255, 255).tex(uStart, vStart).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(n3.getX(), n3.getY(), n3.getZ()).endVertex();
		builder.pos(matrix, maxX, minY, minZ).color(255, 255, 255, 255).tex(uStart + uAdd * (toX - fromX), vStart).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(n3.getX(), n3.getY(), n3.getZ()).endVertex();
		builder.pos(matrix, maxX, minY, maxZ).color(255, 255, 255, 255).tex(uStart + uAdd * (toX - fromX), vStart).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(n3.getX(), n3.getY(), n3.getZ()).endVertex();
		builder.pos(matrix, minX, minY, maxZ).color(255, 255, 255, 255).tex(uStart, vStart).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(n3.getX(), n3.getY(), n3.getZ()).endVertex();
		
		Vector3f n4 = Vector3f.YP;
		builder.pos(matrix, minX, maxY, minZ).color(255, 255, 255, 255).tex(uStart, vStart).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(n4.getX(), n4.getY(), n4.getZ()).endVertex();
		builder.pos(matrix, maxX, maxY, minZ).color(255, 255, 255, 255).tex(uStart + uAdd * (toX - fromX), vStart).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(n4.getX(), n4.getY(), n4.getZ()).endVertex();
		builder.pos(matrix, maxX, maxY, maxZ).color(255, 255, 255, 255).tex(uStart + uAdd * (toX - fromX), vStart).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(n4.getX(), n4.getY(), n4.getZ()).endVertex();
		builder.pos(matrix, minX, maxY, maxZ).color(255, 255, 255, 255).tex(uStart, vStart).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(n4.getX(), n4.getY(), n4.getZ()).endVertex();
		
		//Other left right
		Vector3f n5 = Vector3f.ZN;
		builder.pos(matrix, minX, minY, minZ).color(255, 255, 255, 255).tex(uStart, vStart).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(n5.getX(), n5.getY(), n5.getZ()).endVertex();
		builder.pos(matrix, minX, maxY, minZ).color(255, 255, 255, 255).tex(uStart, vStart).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(n5.getX(), n5.getY(), n5.getZ()).endVertex();
		builder.pos(matrix, maxX, maxY, minZ).color(255, 255, 255, 255).tex(uStart + uAdd * (toX - fromX), vStart).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(n5.getX(), n5.getY(), n5.getZ()).endVertex();
		builder.pos(matrix, maxX, minY, minZ).color(255, 255, 255, 255).tex(uStart + uAdd * (toX - fromX), vStart).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(n5.getX(), n5.getY(), n5.getZ()).endVertex();
		
		Vector3f n6 = Vector3f.ZP;
		builder.pos(matrix, minX, minY, maxZ).color(255, 255, 255, 255).tex(uStart, vStart).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(n6.getX(), n6.getY(), n6.getZ()).endVertex();
		builder.pos(matrix, minX, maxY, maxZ).color(255, 255, 255, 255).tex(uStart, vStart).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(n6.getX(), n6.getY(), n6.getZ()).endVertex();
		builder.pos(matrix, maxX, maxY, maxZ).color(255, 255, 255, 255).tex(uStart + uAdd * (toX - fromX), vStart).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(n6.getX(), n6.getY(), n6.getZ()).endVertex();
		builder.pos(matrix, maxX, minY, maxZ).color(255, 255, 255, 255).tex(uStart + uAdd * (toX - fromX), vStart).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(n6.getX(), n6.getY(), n6.getZ()).endVertex();
		
		/*
		builder.pos(matrix, maxX, minY, maxZ).color(255, 255, 255, 255).tex(uStart + vAdd * (maxX - minX), vStart).lightmap(combinedLightIn).endVertex();
		
		builder.pos(matrix, minX, maxY, minZ).color(255, 255, 255, 255).tex(uStart, vStart).lightmap(combinedLightIn).endVertex();
		builder.pos(matrix, minX, maxY, maxZ).color(255, 255, 255, 255).tex(uStart, vStart).lightmap(combinedLightIn).endVertex();
		builder.pos(matrix, maxX, maxY, minZ).color(255, 255, 255, 255).tex(uStart + vAdd * (maxX - minX), vStart).lightmap(combinedLightIn).endVertex();
		builder.pos(matrix, maxX, maxY, maxZ).color(255, 255, 255, 255).tex(uStart + vAdd * (maxX - minX), vStart).lightmap(combinedLightIn).endVertex();
		*/
		
		/*
		builder.pos(matrix, fromX * size, 0, y * size).color(255, 255, 255, 255).tex(uStart, vStart).lightmap(combinedLightIn).endVertex();
		builder.pos(matrix, toX * size, 0, y * size).color(255, 255, 255, 255).tex(uStart + uAdd, vStart).lightmap(combinedLightIn).endVertex();
		builder.pos(matrix, fromX * size, 0, y * size + size).color(255, 255, 255, 255).tex(uStart, vStart + vAdd).lightmap(combinedLightIn).endVertex();
		builder.pos(matrix, toX * size, 0, y * size + size).color(255, 255, 255, 255).tex(uStart + uAdd, vStart + vAdd).lightmap(combinedLightIn).endVertex();
		
		WorldRenderer.drawBoundingBox(bufferIn, minX, minY, minZ, maxX, maxY, maxZ, red, green, blue, alpha);
		
		builder.pos(matrix, fromX * size, size, y * size).color(255, 255, 255, 255).tex(uStart, vStart).lightmap(combinedLightIn).endVertex();
		builder.pos(matrix, toX * size, size, y * size).color(255, 255, 255, 255).tex(uStart + uAdd, vStart).lightmap(combinedLightIn).endVertex();
		builder.pos(matrix, fromX * size, size, y * size + size).color(255, 255, 255, 255).tex(uStart, vStart + vAdd).lightmap(combinedLightIn).endVertex();
		builder.pos(matrix, toX * size, size, y * size + size).color(255, 255, 255, 255).tex(uStart + uAdd, vStart + vAdd).lightmap(combinedLightIn).endVertex();
		
		builder.pos(matrix, fromX * size, 0, y * size).color(255, 255, 255, 255).tex(uStart, vStart).lightmap(combinedLightIn).endVertex();
		builder.pos(matrix, toX * size, 0, y * size).color(255, 255, 255, 255).tex(uStart + uAdd, vStart).lightmap(combinedLightIn).endVertex();
		builder.pos(matrix, fromX * size, size, y * size).color(255, 255, 255, 255).tex(uStart + uAdd, vStart).lightmap(combinedLightIn).endVertex();
		builder.pos(matrix, toX * size, size, y * size).color(255, 255, 255, 255).tex(uStart + uAdd, vStart).lightmap(combinedLightIn).endVertex();
		
		builder.pos(matrix, fromX * size, 0, y * size + size).color(255, 255, 255, 255).tex(uStart, vStart + vAdd).lightmap(combinedLightIn).endVertex();
		builder.pos(matrix, fromX * size, 0, y * size).color(255, 255, 255, 255).tex(uStart, vStart).lightmap(combinedLightIn).endVertex();
		builder.pos(matrix, fromX * size, size, y * size).color(255, 255, 255, 255).tex(uStart, vStart).lightmap(combinedLightIn).endVertex();
		builder.pos(matrix, fromX * size, size, y * size + size).color(255, 255, 255, 255).tex(uStart, vStart + vAdd).lightmap(combinedLightIn).endVertex();
		*/
		
	}
}

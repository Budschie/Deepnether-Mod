package de.budschie.deepnether.item.rendering;

import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector4f;

public class DynamicItemCreator
{
	/** I love my old code. -Budschie, 2021 **/
	public void build(DynamicTexture texture, IVertexBuilder builder, float size, int combinedLightIn, Matrix4f matrix, Matrix3f m3f, int overlay)
	{
		for(int y = 0; y < texture.getTextureData().getHeight(); y++)
		{
			int fromX = -1;
			
			for(int x = 0; x < texture.getTextureData().getWidth(); x++)
			{
				boolean isPixelDrawable = NativeImage.getAlpha(texture.getTextureData().getPixelRGBA(x, y)) > 100;
				
				if(isPixelDrawable && fromX == -1)
				{
					fromX = x;
				}
				
				// (If we reach the end of the texture or the pixel is not drawable) and fromX is not -1
				if(fromX != -1 && (x == (texture.getTextureData().getHeight() - 1) || !isPixelDrawable))
				{
					// There is currently a bug where this doesn't render.
					int toX = isPixelDrawable ? x + 1 : x;
					
					beginAndEnd(matrix, fromX, toX, y, builder, size, (float)fromX/texture.getTextureData().getWidth(), (float)y/texture.getTextureData().getHeight(), (float)toX/texture.getTextureData().getWidth(), (float)y/texture.getTextureData().getHeight(), combinedLightIn, m3f, overlay);
					fromX = -1;
				}
			}
		}
	}
	
	public void beginAndEnd(Matrix4f m4f, int fromX, int toX, int y, IVertexBuilder builder, float size, float uStart, float vStart, float uEnd, float vEnd, int combinedLightIn, Matrix3f m3f, int overlay)
	{		
		float minX = fromX * size;
		float minY = size*2;
		float minZ = y * size;
		
		float maxX = toX * size;
		float maxY = size*3;
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
		
		/*
		try (MemoryStack memorystack = MemoryStack.stackPush()) 
		{
			ByteBuffer bytebuffer = memorystack.malloc(DefaultVertexFormats.BLOCK.getSize());
			//Left right
			Vector3f n1 = Direction.EAST.toVector3f();
			n1.transform(m3f);
			builder.pos(m4f, minX, maxY, minZ).color(255, 255, 255, 255).tex(uStart, vStart).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(m3f, n1.getX(), n1.getY(), n1.getZ()).endVertex();
			builder.pos(m4f, minX, maxY, maxZ).color(255, 255, 255, 255).tex(uStart, vStart + vAdd).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(m3f, n1.getX(), n1.getY(), n1.getZ()).endVertex();
			builder.pos(m4f, minX, minY, maxZ).color(255, 255, 255, 255).tex(uStart, vStart + vAdd).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(m3f, n1.getX(), n1.getY(), n1.getZ()).endVertex();
			builder.pos(m4f, minX, minY, minZ).color(255, 255, 255, 255).tex(uStart, vStart).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(m3f, n1.getX(), n1.getY(), n1.getZ()).endVertex();
			builder.applyBakedNormals(n1, bytebuffer, m3f);
			
			Vector3f n2 = Direction.WEST.toVector3f();
			n2.transform(m3f);
			builder.pos(m4f, maxX, maxY, minZ).color(255, 255, 255, 255).tex(uStart + uAdd * (toX - fromX), vStart).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(m3f, n2.getX(), n2.getY(), n2.getZ()).endVertex();
			builder.pos(m4f, maxX, maxY, maxZ).color(255, 255, 255, 255).tex(uStart + uAdd * (toX - fromX), vStart + vAdd).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(m3f, n2.getX(), n2.getY(), n2.getZ()).endVertex();
			builder.pos(m4f, maxX, minY, maxZ).color(255, 255, 255, 255).tex(uStart + uAdd * (toX - fromX), vStart + vAdd).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(m3f, n2.getX(), n2.getY(), n2.getZ()).endVertex();
			builder.pos(m4f, maxX, minY, minZ).color(255, 255, 255, 255).tex(uStart + uAdd * (toX - fromX), vStart).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(m3f, n2.getX(), n2.getY(), n2.getZ()).endVertex();
			builder.applyBakedNormals(n2, bytebuffer, m3f);
			
			//Up down
			Vector3f n3 = Direction.DOWN.toVector3f();
			n3.transform(m3f);
			builder.pos(m4f, minX, minY, minZ).color(255, 255, 255, 255).tex(uStart, vStart).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(m3f, n3.getX(), n3.getY(), n3.getZ()).endVertex();
			builder.pos(m4f, maxX, minY, minZ).color(255, 255, 255, 255).tex(uStart + uAdd * (toX - fromX), vStart).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(m3f, n3.getX(), n3.getY(), n3.getZ()).endVertex();
			builder.pos(m4f, maxX, minY, maxZ).color(255, 255, 255, 255).tex(uStart + uAdd * (toX - fromX), vStart + vAdd).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(m3f, n3.getX(), n3.getY(), n3.getZ()).endVertex();
			builder.pos(m4f, minX, minY, maxZ).color(255, 255, 255, 255).tex(uStart, vStart + vAdd).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(m3f, n3.getX(), n3.getY(), n3.getZ()).endVertex();
			builder.applyBakedNormals(n3, bytebuffer, m3f);
			
			Vector3f n4 = Direction.UP.toVector3f();
			n4.transform(m3f);
			builder.pos(m4f, minX, maxY, minZ).color(255, 255, 255, 255).tex(uStart, vStart).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(m3f, n4.getX(), n4.getY(), n4.getZ()).endVertex();
			builder.pos(m4f, maxX, maxY, minZ).color(255, 255, 255, 255).tex(uStart + uAdd * (toX - fromX), vStart).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(m3f, n4.getX(), n4.getY(), n4.getZ()).endVertex();
			builder.pos(m4f, maxX, maxY, maxZ).color(255, 255, 255, 255).tex(uStart + uAdd * (toX - fromX), vStart).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(m3f, n4.getX(), n4.getY(), n4.getZ()).endVertex();
			builder.pos(m4f, minX, maxY, maxZ).color(255, 255, 255, 255).tex(uStart, vStart).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(m3f, n4.getX(), n4.getY(), n4.getZ()).endVertex();
			builder.applyBakedNormals(n4, bytebuffer, m3f);
			
			//Other left right
			Vector3f n5 = Direction.SOUTH.toVector3f();
			n5.transform(m3f);
			builder.pos(m4f, minX, minY, minZ).color(255, 255, 255, 255).tex(uStart, vStart).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(m3f, n5.getX(), n5.getY(), n5.getZ()).endVertex();
			builder.pos(m4f, minX, maxY, minZ).color(255, 255, 255, 255).tex(uStart, vStart).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(m3f, n5.getX(), n5.getY(), n5.getZ()).endVertex();
			builder.pos(m4f, maxX, maxY, minZ).color(255, 255, 255, 255).tex(uStart + uAdd * (toX - fromX), vStart).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(m3f, n5.getX(), n5.getY(), n5.getZ()).endVertex();
			builder.pos(m4f, maxX, minY, minZ).color(255, 255, 255, 255).tex(uStart + uAdd * (toX - fromX), vStart).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(m3f, n5.getX(), n5.getY(), n5.getZ()).endVertex();
			builder.applyBakedNormals(n5, bytebuffer, m3f);
			
			Vector3f n6 = Direction.NORTH.toVector3f();
			n6.transform(m3f);
			builder.pos(m4f, minX, minY, maxZ).color(255, 255, 255, 255).tex(uStart, vStart + vAdd).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(m3f, n6.getX(), n6.getY(), n6.getZ()).endVertex();
			builder.pos(m4f, minX, maxY, maxZ).color(255, 255, 255, 255).tex(uStart, vStart + vAdd).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(m3f, n6.getX(), n6.getY(), n6.getZ()).endVertex();
			builder.pos(m4f, maxX, maxY, maxZ).color(255, 255, 255, 255).tex(uStart + uAdd * (toX - fromX), vStart + vAdd).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(m3f, n6.getX(), n6.getY(), n6.getZ()).endVertex();
			builder.pos(m4f, maxX, minY, maxZ).color(255, 255, 255, 255).tex(uStart + uAdd * (toX - fromX), vStart + vAdd).overlay(OverlayTexture.NO_OVERLAY).lightmap(combinedLightIn).normal(m3f, n6.getX(), n6.getY(), n6.getZ()).endVertex();
			builder.applyBakedNormals(n6, bytebuffer, m3f);
		}
		*/
		
		Plane up = Plane.getXZPlane(minX, maxX, maxY, minZ, maxZ);
		Plane down = Plane.getXZPlane(minX, maxX, minY, minZ, maxZ);
		Plane east = Plane.getYZPlane(maxX, minY, maxY, minZ, maxZ);
		Plane west = Plane.getYZPlane(minX, minY, maxY, minZ, maxZ);
		Plane south = Plane.getXYPlane(minX, maxX, minY, maxY, maxZ);
		Plane north = Plane.getXYPlane(minX, maxX, minY, maxY, minZ);
		
		addQuad(up, builder, m4f, m3f, new float[] {uStart, uStart, uEnd, uEnd}, new float[] {vStart, vEnd, vEnd, vStart}, combinedLightIn, overlay, (byte)0);
		addQuad(down, builder, m4f, m3f, new float[] {uStart, uStart, uEnd, uEnd}, new float[] {vStart, vEnd, vEnd, vStart}, combinedLightIn, overlay, (byte)0);
		addQuad(east, builder, m4f, m3f, new float[] {uEnd - 0.001f, uEnd - 0.001f, uEnd - 0.001f, uEnd - 0.001f}, new float[] {vStart, vStart, vStart, vStart}, combinedLightIn, overlay, (byte)0);
		addQuad(west, builder, m4f, m3f, new float[] {uStart, uStart, uStart, uStart}, new float[] {vStart, vStart, vStart, vStart}, combinedLightIn, overlay, (byte)0);
		addQuad(south, builder, m4f, m3f, new float[] {uStart, uStart, uEnd, uEnd}, new float[] {vEnd, vEnd, vEnd, vEnd}, combinedLightIn, overlay, (byte)0);
		addQuad(north, builder, m4f, m3f, new float[] {uStart, uStart, uEnd, uEnd}, new float[] {vStart, vStart, vStart, vStart}, combinedLightIn, overlay, (byte)0);

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
	
	public static void addQuad(Plane plane, IVertexBuilder builder, Matrix4f m4f, Matrix3f m3f, float[] u, float[] v, int lightmap, int overlay, byte debug)
	{
		Vector3f pos1 = plane.getFirstVertex();
		Vector3f pos2 = plane.getSecondVertex();
		Vector3f pos3 = plane.getThirdVertex();
		Vector3f pos4 = plane.getFourthVertex();
		
		//Matrix4f newMatrix = new Matrix4f(m3fMat.);
		
		//pos1.transform(m4f);
		//pos2.transform(m4f);
		//pos3.transform(m4f);
		//pos4.transform(m4f);
		
		Vector3f normal = plane.getNormal();
		
//		builder.pos(m4f, pos1.getX(), pos1.getY(), pos1.getZ()).color(255, 255, 255, 255).tex(u[0], v[0]).overlay(overlay).lightmap(lightmap).normal(m3f, normal.getX(), normal.getY(), normal.getZ()).endVertex();

		Matrix4f matrix = new Matrix4f(new float[] {1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1});
		
		float cR = (debug == 1) ? .5f : 1, cG = (debug == 2) ? 0 : 1, cB = (debug != 0) ? 3 : 1;
		
		builder.pos(m4f, pos1.getX(), pos1.getY(), pos1.getZ()).color(cR, cG, cB, 1f).tex(u[0],  v[0]).overlay(overlay).lightmap(lightmap).normal(m3f, normal.getX(), normal.getY(), normal.getZ()).endVertex();
		builder.pos(m4f, pos2.getX(), pos2.getY(), pos2.getZ()).color(cR, cG, cB, 1f).tex(u[1],  v[1]).overlay(overlay).lightmap(lightmap).normal(m3f, normal.getX(), normal.getY(), normal.getZ()).endVertex();
		builder.pos(m4f, pos3.getX(), pos3.getY(), pos3.getZ()).color(cR, cG, cB, 1f).tex(u[2],  v[2]).overlay(overlay).lightmap(lightmap).normal(m3f, normal.getX(), normal.getY(), normal.getZ()).endVertex();
		builder.pos(m4f, pos4.getX(), pos4.getY(), pos4.getZ()).color(cR, cG, cB, 1f).tex(u[3],  v[3]).overlay(overlay).lightmap(lightmap).normal(m3f, normal.getX(), normal.getY(), normal.getZ()).endVertex();

//		Vector3f normal = facing.toVector3f();
//		
//		Vector4f pos1 = null;
//		Vector4f pos2 = null;
//		Vector4f pos3 = null;
//		Vector4f pos4 = null;
//		
//		/*
//		if(facing == Direction.NORTH || facing == Direction.SOUTH)
//		{
//			pos1 = new Vector4f(fromX, maxY, fromZ, 1.0f);
//			pos2 = new Vector4f(toX, maxY, fromZ, 1.0f);
//			pos3 = new Vector4f(toX, minY, toZ, 1.0f);
//			pos4 = new Vector4f(fromX, minY, toZ, 1.0f);
//		}
//		*/
//		//{
//		if(facing == Direction.SOUTH || facing == Direction.NORTH)
//		{
//			pos1 = new Vector4f(minX, minY, minZ, 1.0f);
//			pos2 = new Vector4f(maxX, maxY, minZ, 1.0f);
//			pos3 = new Vector4f(maxX, maxY, maxZ, 1.0f);
//			pos4 = new Vector4f(minX, minY, maxZ, 1.0f);
//		}
//		else
//		{
//			pos1 = new Vector4f(minX, minY, minZ, 1.0f);
//			pos2 = new Vector4f(maxX, minY, minZ, 1.0f);
//			pos3 = new Vector4f(maxX, maxY, maxZ, 1.0f);
//			pos4 = new Vector4f(minX, maxY, maxZ, 1.0f);
//		}
//		//}
//		
//		/*
//		Vector4f pos1 = new Vector4f(switchPos ? toX : fromX, switchPos ? minY : maxY, switchPos ? toZ : fromZ, 1.0f);
//		Vector4f pos2 = new Vector4f(switchPos ? fromX : toX, switchPos ? minY : maxY, switchPos ? toZ : fromZ, 1.0f);
//		Vector4f pos3 = new Vector4f(switchPos ? fromZ : toX, switchPos ? maxY : minY, switchPos ? fromZ : toZ, 1.0f);
//		Vector4f pos4 = new Vector4f(switchPos ? toX : fromX, switchPos ? maxY : minY, switchPos ? fromZ : toZ, 1.0f);
//		*/
//		 
//		
//		//ModelRenderer
//		
//		/*
//		pos1.transform(m4f);
//		pos2.transform(m4f);
//		pos3.transform(m4f);
//		pos4.transform(m4f);
//		
//		
//		builder.addVertex(pos1.getX(), pos1.getY(), pos1.getZ(), 255, 255, 255, 255, u[0], v[0], overlay, lightmap, normal.getX(), normal.getY(), normal.getZ());
//		builder.addVertex(pos2.getX(), pos2.getY(), pos2.getZ(), 255, 255, 255, 255, u[1], v[1], overlay, lightmap, normal.getX(), normal.getY(), normal.getZ());
//		builder.addVertex(pos3.getX(), pos3.getY(), pos3.getZ(), 255, 255, 255, 255, u[2], v[2], overlay, lightmap, normal.getX(), normal.getY(), normal.getZ());
//		builder.addVertex(pos4.getX(), pos4.getY(), pos4.getZ(), 255, 255, 255, 255, u[3], v[3], overlay, lightmap, normal.getX(), normal.getY(), normal.getZ());
//		*/
//		
//		if(switchPos)
//		{
//			builder.pos(m4f, pos1.getX(), pos1.getY(), pos1.getZ()).color(255, 255, 255, 255).tex(u[0], v[0]).overlay(overlay).lightmap(lightmap).normal(m3f, normal.getX(), normal.getY(), normal.getZ()).endVertex();
//			builder.pos(m4f, pos4.getX(), pos4.getY(), pos4.getZ()).color(255, 255, 255, 255).tex(u[3], v[3]).overlay(overlay).lightmap(lightmap).normal(m3f, normal.getX(), normal.getY(), normal.getZ()).endVertex();
//			builder.pos(m4f, pos3.getX(), pos3.getY(), pos3.getZ()).color(255, 255, 255, 255).tex(u[2], v[2]).overlay(overlay).lightmap(lightmap).normal(m3f, normal.getX(), normal.getY(), normal.getZ()).endVertex();
//			builder.pos(m4f, pos2.getX(), pos2.getY(), pos2.getZ()).color(255, 255, 255, 255).tex(u[1], v[1]).overlay(overlay).lightmap(lightmap).normal(m3f, normal.getX(), normal.getY(), normal.getZ()).endVertex();
//		}
//		else
//		{
//			builder.pos(m4f, pos1.getX(), pos1.getY(), pos1.getZ()).color(255, 255, 255, 255).tex(u[0], v[0]).overlay(overlay).lightmap(lightmap).normal(m3f, normal.getX(), normal.getY(), normal.getZ()).endVertex();
//			builder.pos(m4f, pos2.getX(), pos2.getY(), pos2.getZ()).color(255, 255, 255, 255).tex(u[1], v[1]).overlay(overlay).lightmap(lightmap).normal(m3f, normal.getX(), normal.getY(), normal.getZ()).endVertex();
//			builder.pos(m4f, pos3.getX(), pos3.getY(), pos3.getZ()).color(255, 255, 255, 255).tex(u[2], v[2]).overlay(overlay).lightmap(lightmap).normal(m3f, normal.getX(), normal.getY(), normal.getZ()).endVertex();
//			builder.pos(m4f, pos4.getX(), pos4.getY(), pos4.getZ()).color(255, 255, 255, 255).tex(u[3], v[3]).overlay(overlay).lightmap(lightmap).normal(m3f, normal.getX(), normal.getY(), normal.getZ()).endVertex();
//		}
	}
}

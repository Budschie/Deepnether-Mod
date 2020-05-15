package de.budschie.deepnether.entity.renders;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class EmptyRenderer extends EntityRenderer<Entity>
{

	protected EmptyRenderer(EntityRendererManager renderManager)
	{
		super(renderManager);
	}
	
	@Override
	public void render(Entity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn)
	{
		
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity)
	{
		return null;
	}
}

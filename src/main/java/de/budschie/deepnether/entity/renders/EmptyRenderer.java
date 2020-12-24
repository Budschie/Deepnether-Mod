package de.budschie.deepnether.entity.renders;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;

public class EmptyRenderer extends EntityRenderer<Entity>
{

	protected EmptyRenderer(EntityRendererManager renderManager)
	{
		super(renderManager);
		this.shadowSize = 0;
	}
	
	@Override
	public void render(Entity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn)
	{
		
	}
	
	@Override
	public boolean shouldRender(Entity livingEntityIn, ClippingHelper camera, double camX, double camY, double camZ)
	{
		return false;
	}
	
	@Override
	protected void renderName(Entity entityIn, ITextComponent displayNameIn, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int packedLightIn)
	{
		
	}
	
	@Override
	public Vector3d getRenderOffset(Entity entityIn, float partialTicks)
	{
		return super.getRenderOffset(entityIn, partialTicks);
	}
	
	

	@Override
	public ResourceLocation getEntityTexture(Entity entity)
	{
		return new ResourceLocation("");
	}
	
	
}

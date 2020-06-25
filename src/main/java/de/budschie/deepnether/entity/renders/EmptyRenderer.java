package de.budschie.deepnether.entity.renders;

import com.mojang.blaze3d.matrix.MatrixStack;

import de.budschie.deepnether.entity.ShadowTrapEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.culling.ClippingHelperImpl;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

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
	public boolean shouldRender(Entity livingEntityIn, ClippingHelperImpl camera, double camX, double camY, double camZ)
	{
		return false;
	}
	
	@Override
	protected void renderName(Entity entityIn, String displayNameIn, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int packedLightIn)
	{
		
	}
	
	@Override
	public Vec3d getRenderOffset(Entity entityIn, float partialTicks)
	{
		return super.getRenderOffset(entityIn, partialTicks);
	}
	
	

	@Override
	public ResourceLocation getEntityTexture(Entity entity)
	{
		return new ResourceLocation("");
	}
	
	
}

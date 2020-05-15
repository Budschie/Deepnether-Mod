package de.budschie.deepnether.entity.renders;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;

import de.budschie.deepnether.entity.HellDevilEntity;
import de.budschie.deepnether.main.References;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;

public class HellDevilRenderer extends LivingRenderer<HellDevilEntity, HellDevilModel<HellDevilEntity>>
{
	public static final ResourceLocation HELL_DEVIL = new ResourceLocation(References.MODID, "textures/entity/hell_devil/hell_devil.png");
	
	public HellDevilRenderer(EntityRendererManager rendererManager)
	{
		super(rendererManager, new HellDevilModel<HellDevilEntity>(), 1.0f);
	}

	@Override
	public ResourceLocation getEntityTexture(HellDevilEntity entity)
	{
		return HELL_DEVIL;
	}
	
	@Override
	public void render(HellDevilEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int packedLightIn)
	{
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}
	
	@Override
	protected void applyRotations(HellDevilEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks,
			float rotationYaw, float partialTicks)
	{
		super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
	}
}

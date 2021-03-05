package de.budschie.deepnether.entity.renders;

import javax.annotation.Nullable;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import de.budschie.deepnether.entity.ShadowEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.AbstractZombieModel;
import net.minecraft.client.renderer.entity.model.CreeperModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class ShadowRenderer extends LivingRenderer<ShadowEntity, EntityModel<ShadowEntity>>
{
	   private static final ResourceLocation CREEPER_TEXTURES = new ResourceLocation("textures/entity/creeper/creeper.png");
	   private static final ResourceLocation ZOMBIE_PIGMAN_TEXTURES = new ResourceLocation("textures/entity/zombie_pigman.png");
	   private static final ResourceLocation ZOMBIE_TEXTURES = new ResourceLocation("textures/entity/zombie/zombie.png");
	   private static final ResourceLocation SKELETON_TEXTURES = new ResourceLocation("textures/entity/skeleton/skeleton.png");
	   
	public ShadowRenderer(EntityRendererManager rendererManager)
	{
		super(rendererManager, new CreeperModel<>(), 1.0f);
	}
	
	@Override
	public void render(ShadowEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn)
	{
		switch(entityIn.getShadowType())
		{
			case CREEPER:
			{
				this.entityModel = new CreeperModel<>();
			}
			break;
			case SKELETON:
			{
				this.entityModel = new SkeletonModelModified(0.0f, false);
			}
			break;
			default:
			{
				this.entityModel = new AbstractZombieModel<ShadowEntity>(0.0F, 0.0F, 64, 64)
				{
					@Override
					public boolean isAggressive(ShadowEntity entityIn)
					{
						return false;
					}
				};
			}
			break;
		}

		float r = 0.2f;
		float g = 0.2f;
		float b = 1.0f;
		float a = entityIn.getTransparency();
		
		if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Pre<ShadowEntity, EntityModel<ShadowEntity>>(entityIn, this, partialTicks, matrixStackIn, bufferIn, packedLightIn))) return;
	      matrixStackIn.push();
	      this.entityModel.swingProgress = this.getSwingProgress(entityIn, partialTicks);

	      boolean shouldSit = entityIn.isPassenger() && (entityIn.getRidingEntity() != null && entityIn.getRidingEntity().shouldRiderSit());
	      this.entityModel.isSitting = shouldSit;
	      this.entityModel.isChild = entityIn.isChild();
	      float f = MathHelper.interpolateAngle(partialTicks, entityIn.prevRenderYawOffset, entityIn.renderYawOffset);
	      float f1 = MathHelper.interpolateAngle(partialTicks, entityIn.prevRotationYawHead, entityIn.rotationYawHead);
	      float f2 = f1 - f;
	      if (shouldSit && entityIn.getRidingEntity() instanceof LivingEntity) {
	         LivingEntity livingentity = (LivingEntity)entityIn.getRidingEntity();
	         f = MathHelper.interpolateAngle(partialTicks, livingentity.prevRenderYawOffset, livingentity.renderYawOffset);
	         f2 = f1 - f;
	         float f3 = MathHelper.wrapDegrees(f2);
	         if (f3 < -85.0F) {
	            f3 = -85.0F;
	         }

	         if (f3 >= 85.0F) {
	            f3 = 85.0F;
	         }

	         f = f1 - f3;
	         if (f3 * f3 > 2500.0F) {
	            f += f3 * 0.2F;
	         }

	         f2 = f1 - f;
	      }

	      float f6 = MathHelper.lerp(partialTicks, entityIn.prevRotationPitch, entityIn.rotationPitch);
	      if (entityIn.getPose() == Pose.SLEEPING) {
	         Direction direction = entityIn.getBedDirection();
	         if (direction != null) {
	            float f4 = entityIn.getEyeHeight(Pose.STANDING) - 0.1F;
	            matrixStackIn.translate((double)((float)(-direction.getXOffset()) * f4), 0.0D, (double)((float)(-direction.getZOffset()) * f4));
	         }
	      }

	      float f7 = this.handleRotationFloat(entityIn, partialTicks);
	      this.applyRotations(entityIn, matrixStackIn, f7, f, partialTicks);
	      matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
	      this.preRenderCallback(entityIn, matrixStackIn, partialTicks);
	      matrixStackIn.translate(0.0D, (double)-1.501F, 0.0D);
	      float f8 = 0.0F;
	      float f5 = 0.0F;
	      if (!shouldSit && entityIn.isAlive()) {
	         f8 = MathHelper.lerp(partialTicks, entityIn.prevLimbSwingAmount, entityIn.limbSwingAmount);
	         f5 = entityIn.limbSwing - entityIn.limbSwingAmount * (1.0F - partialTicks);
	         if (entityIn.isChild()) {
	            f5 *= 3.0F;
	         }

	         if (f8 > 1.0F) {
	            f8 = 1.0F;
	         }
	      }

	      this.entityModel.setLivingAnimations(entityIn, f5, f8, partialTicks);
	      this.entityModel.setRotationAngles(entityIn, f5, f8, f7, f2, f6);
	      boolean flag = this.isVisible(entityIn);
	      boolean flag1 = !flag && !entityIn.isInvisibleToPlayer(Minecraft.getInstance().player);
	      RenderType rendertype = this.func_230042_a_(entityIn, flag, flag1);
	      if (rendertype != null) {
	         IVertexBuilder ivertexbuilder = bufferIn.getBuffer(rendertype);
	         int i = getPackedOverlay(entityIn, this.getOverlayProgress(entityIn, partialTicks));
	         this.entityModel.render(matrixStackIn, ivertexbuilder, packedLightIn, i, r, g, b, flag1 ? 0.15F*a : a);
	      }

	      if (!entityIn.isSpectator()) {
	         for(LayerRenderer<ShadowEntity, EntityModel<ShadowEntity>> layerrenderer : this.layerRenderers) {
	            layerrenderer.render(matrixStackIn, bufferIn, packedLightIn, entityIn, f5, f8, partialTicks, f7, f2, f6);
	         }
	      }

	      matrixStackIn.pop();
	      net.minecraftforge.client.event.RenderNameplateEvent renderNameplateEvent = new net.minecraftforge.client.event.RenderNameplateEvent(entityIn, entityIn.getDisplayName(), this, matrixStackIn, bufferIn, packedLightIn, partialTicks);
	      net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(renderNameplateEvent);
	      if (renderNameplateEvent.getResult() != net.minecraftforge.eventbus.api.Event.Result.DENY && (renderNameplateEvent.getResult() == net.minecraftforge.eventbus.api.Event.Result.ALLOW || this.canRenderName(entityIn))) {
	         this.renderName(entityIn, renderNameplateEvent.getContent(), matrixStackIn, bufferIn, packedLightIn);
	      }
	      net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Post<ShadowEntity, EntityModel<ShadowEntity>>(entityIn, this, partialTicks, matrixStackIn, bufferIn, packedLightIn));
	}
	
	// Enable transparency
	   @Nullable
	   protected RenderType func_230042_a_(ShadowEntity p_230042_1_, boolean p_230042_2_, boolean p_230042_3_) {
	      ResourceLocation resourcelocation = this.getEntityTexture(p_230042_1_);
	      if (p_230042_3_) {
	         return RenderType.getEntityTranslucent(resourcelocation);
	      } else if (p_230042_2_) {
	         return RenderType.getEntityTranslucent(resourcelocation);
	      } else {
	         return p_230042_1_.isGlowing() ? RenderType.getOutline(resourcelocation) : null;
	      }
	   }

	@Override
	public ResourceLocation getEntityTexture(ShadowEntity entity)
	{
		switch(entity.getShadowType())
		{
		case CREEPER:
			return CREEPER_TEXTURES;
		case PIGMEN:
			return ZOMBIE_PIGMAN_TEXTURES;
		case ZOMBIE:
			return ZOMBIE_TEXTURES;
			default:
				return SKELETON_TEXTURES;
		}
	}

}

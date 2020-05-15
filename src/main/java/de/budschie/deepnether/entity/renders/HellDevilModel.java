package de.budschie.deepnether.entity.renders;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.CreeperModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.model.ModelRenderer.ModelBox;
import net.minecraft.entity.Entity;

public class HellDevilModel<T extends Entity> extends EntityModel<T>
{
	private final ModelRenderer all;
	private final ModelRenderer bone2;
	private final ModelRenderer bone;
	private final ModelRenderer head;
	private final ModelRenderer bone3;

	public HellDevilModel() {
		textureWidth = 256;
		textureHeight = 256;

		all = new ModelRenderer(this);
		all.setRotationPoint(0.0F, 24.0F, 0.0F);
		setRotationAngle(all, 0.0F, -1.5708F, 0.0F);

		bone2 = new ModelRenderer(this);
		bone2.setRotationPoint(0.0F, 0.0F, 0.0F);
		all.addChild(bone2);
		bone2.addBox("bone2ey", -1.8822F, -30.6019F, -7.0F, 7, 13, 14, 0.0F, 149, 189);
		bone2.addBox("bone2eyy", 1.0F, -11.0F, -2.0F, 4, 10, 6, 0.0F, 0, 0);

		bone = new ModelRenderer(this);
		bone.setRotationPoint(0.0F, 0.0F, 0.0F);
		setRotationAngle(bone, 0.0F, 0.0F, -0.0873F);
		all.addChild(bone);
		bone.addBox("boneey", -0.0038F, -17.9128F, -7.0F, 6, 11, 14, 0.0F, 62, 164);

		head = new ModelRenderer(this);
		head.setRotationPoint(0.3165F, -24.7253F, -0.125F);
		all.addChild(head);
		head.addBox("heade", -1.3165F, -18.2747F, -1.875F, 3, 3, 3, 0.0F, 136, 58);
		head.addBox("headee", -1.3165F, -20.2747F, -0.875F, 1, 2, 2, 0.0F, 134, 55);
		head.addBox("headeee", -1.3165F, -22.2747F, 0.125F, 1, 2, 1, 0.0F, 145, 58);
		head.addBox("headeeee", -3.0505F, -15.1758F, -5.375F, 9, 9, 10, 0.0F, 138, 120);

		bone3 = new ModelRenderer(this);
		bone3.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.addChild(bone3);
	}

	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha)
	{
		matrixStackIn.push();
		matrixStackIn.translate(0, floating, 0);
		all.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
		matrixStackIn.pop();
	}
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
	
	private float floating = 0.0f;
	
	@Override
	public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick)
	{
		floating = (float) (Math.sin((entityIn.ticksExisted + partialTick)/16.0)-1.85/2.0);
	}

	@Override
	public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch)
	{
	     this.head.rotateAngleY = netHeadYaw * ((float)Math.PI / -180F);
	     //this.head.rotateAngleX = headPitch * ((float)Math.PI / -180F);
	}
}

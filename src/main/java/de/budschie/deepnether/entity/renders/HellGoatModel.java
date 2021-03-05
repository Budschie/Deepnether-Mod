package de.budschie.deepnether.entity.renders;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import de.budschie.deepnether.entity.HellGoatEntity;
import de.budschie.deepnether.entity.HellGoatEntity.HellGoatMovement;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

// Made with Blockbench 3.5.4
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


public class HellGoatModel extends EntityModel<HellGoatEntity> {
	private ModelRenderer Base;
	private ModelRenderer Leg1;
	private ModelRenderer Leg2;
	private ModelRenderer Leg3;
	private ModelRenderer Leg4;
	private ModelRenderer Neck;
	private ModelRenderer Head;
	private ModelRenderer bone2;
	private ModelRenderer bone;
	private ModelRenderer Wing2;
	private ModelRenderer bone4;
	private ModelRenderer W5;
	private ModelRenderer W6;
	private ModelRenderer W7;
	private ModelRenderer W8;
	private ModelRenderer Wing1;
	private ModelRenderer bone3;
	private ModelRenderer W1;
	private ModelRenderer W2;
	private ModelRenderer W3;
	private ModelRenderer W4;

	public HellGoatModel() 
	{
		init();
	}
	
	public void init()
	{
		textureWidth = 48;
		textureHeight = 48;

		Base = new ModelRenderer(this);
		Base.setRotationPoint(0.0F, 24.0F, 0.0F);
		setRotationAngle(Base, 0.0F, 3.1416F, 0.0F);
		Base.setTextureOffset(0, 0).addBox(-3.0F, -10.0F, -7.0F, 5.0F, 4.0F, 12.0F, 0.0F, false);

		Leg1 = new ModelRenderer(this);
		Leg1.setRotationPoint(-2.25F, -6.0F, -7.25F);
		Base.addChild(Leg1);
		Leg1.setTextureOffset(0, 40).addBox(-0.5F, 0.0F, 0.5F, 1.0F, 6.0F, 1.0F, 0.0F, false);

		Leg2 = new ModelRenderer(this);
		Leg2.setRotationPoint(1.25F, -6.0F, -7.25F);
		Base.addChild(Leg2);
		Leg2.setTextureOffset(16, 33).addBox(-0.5F, 0.0F, 0.5F, 1.0F, 6.0F, 1.0F, 0.0F, false);

		Leg3 = new ModelRenderer(this);
		Leg3.setRotationPoint(1.25F, -6.0F, 3.75F);
		Base.addChild(Leg3);
		Leg3.setTextureOffset(12, 33).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 6.0F, 1.0F, 0.0F, false);

		Leg4 = new ModelRenderer(this);
		Leg4.setRotationPoint(-2.25F, -6.0F, 3.75F);
		Base.addChild(Leg4);
		Leg4.setTextureOffset(8, 33).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 6.0F, 1.0F, 0.0F, false);

		Neck = new ModelRenderer(this);
		Neck.setRotationPoint(-0.25F, -8.0647F, 4.6165F);
		Base.addChild(Neck);
		setRotationAngle(Neck, -0.4363F, 0.0F, 0.0F);
		Neck.setTextureOffset(0, 23).addBox(-1.75F, -3.2106F, -2.3236F, 3.0F, 3.0F, 2.0F, 0.0F, false);
		Neck.setTextureOffset(0, 16).addBox(-1.75F, -5.9189F, -2.9434F, 3.0F, 3.0F, 4.0F, 0.0F, false);

		Head = new ModelRenderer(this);
		Head.setRotationPoint(-0.25F, -2.4012F, -1.8753F);
		Neck.addChild(Head);
		setRotationAngle(Head, 0.2618F, 0.0F, 0.0F);
		

		bone2 = new ModelRenderer(this);
		bone2.setRotationPoint(0.0F, 0.0F, 0.0F);
		Head.addChild(bone2);
		setRotationAngle(bone2, 0.0873F, 0.0F, 0.0F);
		bone2.setTextureOffset(10, 44).addBox(-1.75F, -5.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		bone2.setTextureOffset(16, 40).addBox(0.75F, -5.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);

		bone = new ModelRenderer(this);
		bone.setRotationPoint(-0.25F, -1.4353F, -1.6165F);
		Neck.addChild(bone);
		bone.setTextureOffset(26, 23).addBox(-1.0F, -3.2421F, 1.7377F, 2.0F, 2.0F, 2.0F, 0.0F, false);
		bone.setTextureOffset(0, 33).addBox(-0.95F, -2.5009F, 2.7037F, 2.0F, 1.0F, 2.0F, 0.0F, false);

		Wing2 = new ModelRenderer(this);
		Wing2.setRotationPoint(2.0F, -7.0F, 0.0F);
		Base.addChild(Wing2);
		setRotationAngle(Wing2, 0.0F, -0.1745F, -0.0873F);
		Wing2.setTextureOffset(12, 28).addBox(0.0571F, -3.1827F, -0.8778F, 1.0F, 3.0F, 2.0F, 0.0F, false);

		bone4 = new ModelRenderer(this);
		bone4.setRotationPoint(1.75F, 0.25F, 0.75F);
		Wing2.addChild(bone4);
		

		W5 = new ModelRenderer(this);
		W5.setRotationPoint(-1.0F, 0.0F, -1.0F);
		bone4.addChild(W5);
		W5.setTextureOffset(22, 16).addBox(-0.25F, -4.0F, -2.0F, 1.0F, 4.0F, 3.0F, 0.0F, false);

		W6 = new ModelRenderer(this);
		W6.setRotationPoint(0.4726F, -1.1245F, -2.3655F);
		W5.addChild(W6);
		W6.setTextureOffset(6, 28).addBox(-0.8962F, -1.8755F, -1.6193F, 1.0F, 3.0F, 2.0F, 0.0F, false);

		W7 = new ModelRenderer(this);
		W7.setRotationPoint(-0.0107F, -0.3755F, -2.0019F);
		W6.addChild(W7);
		W7.setTextureOffset(10, 40).addBox(-0.8618F, -0.7926F, -1.5271F, 1.0F, 2.0F, 2.0F, 0.0F, false);

		W8 = new ModelRenderer(this);
		W8.setRotationPoint(0.0047F, 0.5019F, -1.6098F);
		W7.addChild(W8);
		W8.setTextureOffset(18, 23).addBox(-0.8666F, -0.5F, -3.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);

		Wing1 = new ModelRenderer(this);
		Wing1.setRotationPoint(-2.75F, -7.25F, 0.0F);
		Base.addChild(Wing1);
		setRotationAngle(Wing1, 0.0F, 0.1745F, 0.0873F);
		Wing1.setTextureOffset(18, 28).addBox(-1.2715F, -2.999F, -1.0038F, 1.0F, 3.0F, 2.0F, 0.0F, false);

		bone3 = new ModelRenderer(this);
		bone3.setRotationPoint(-0.25F, 0.25F, 0.75F);
		Wing1.addChild(bone3);
		

		W1 = new ModelRenderer(this);
		W1.setRotationPoint(-1.0F, 0.0F, -1.0F);
		bone3.addChild(W1);
		W1.setTextureOffset(14, 16).addBox(-0.25F, -4.0F, -2.0F, 1.0F, 4.0F, 3.0F, 0.0F, false);

		W2 = new ModelRenderer(this);
		W2.setRotationPoint(0.4726F, -1.1245F, -2.3655F);
		W1.addChild(W2);
		W2.setTextureOffset(0, 28).addBox(-0.5489F, -1.8755F, -1.6193F, 1.0F, 3.0F, 2.0F, 0.0F, false);

		W3 = new ModelRenderer(this);
		W3.setRotationPoint(-0.0107F, -0.3755F, -2.0019F);
		W2.addChild(W3);
		W3.setTextureOffset(4, 40).addBox(-0.5167F, -0.749F, -1.6136F, 1.0F, 2.0F, 2.0F, 0.0F, false);

		W4 = new ModelRenderer(this);
		W4.setRotationPoint(0.0047F, 0.5019F, -1.6098F);
		W3.addChild(W4);
		W4.setTextureOffset(10, 23).addBox(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
	}
	
	@Override
	public void setRotationAngles(HellGoatEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		init();
		
		boolean isFlying = entity.getMovementPhase() == HellGoatMovement.FLYING && !entity.getDataManager().get(HellGoatEntity.STARTED_FLYING);
		
		if(isFlying)
		{
			float currentRotationAngle = (float) (Math.sin(ageInTicks/10f)+.25);
			setRotationAngle(Wing1, -1.4835F, 0.1745F, -1.6581F);
			
			W1.rotateAngleY = currentRotationAngle / 3;
			W2.rotateAngleY = currentRotationAngle / 6;
			W3.rotateAngleY = currentRotationAngle / 9;
			
			setRotationAngle(Wing2, -1.4835F, 0.1745F, 1.6581F);
			
			W5.rotateAngleY = currentRotationAngle / -3;
			W6.rotateAngleY = currentRotationAngle / -6;
			W7.rotateAngleY = currentRotationAngle / -9;
		}
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		matrixStack.scale(2f, 2f, 2f);
		matrixStack.translate(0f, -.75f, 0f);
		Base.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
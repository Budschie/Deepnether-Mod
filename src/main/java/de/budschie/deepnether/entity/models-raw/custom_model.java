// Made with Blockbench 3.5.4
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

/*
public class custom_model extends EntityModel<Entity> {
	private final ModelRenderer Base;
	private final ModelRenderer Leg1;
	private final ModelRenderer Leg2;
	private final ModelRenderer Leg3;
	private final ModelRenderer Leg4;
	private final ModelRenderer Neck;
	private final ModelRenderer Head;
	private final ModelRenderer bone2;
	private final ModelRenderer bone;
	private final ModelRenderer Wing2;
	private final ModelRenderer bone4;
	private final ModelRenderer Wing1;
	private final ModelRenderer bone3;

	public custom_model() {
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
		Wing2.setRotationPoint(4.0F, -7.25F, 0.25F);
		Base.addChild(Wing2);
		setRotationAngle(Wing2, 0.0F, -0.1745F, -0.0873F);
		Wing2.setTextureOffset(6, 28).addBox(-1.7405F, -2.7936F, -0.9135F, 1.0F, 3.0F, 2.0F, 0.0F, false);

		bone4 = new ModelRenderer(this);
		bone4.setRotationPoint(-0.25F, 0.25F, 0.75F);
		Wing2.addChild(bone4);
		bone4.setTextureOffset(14, 16).addBox(-1.25F, -4.0F, -3.0F, 1.0F, 4.0F, 3.0F, 0.0F, false);
		bone4.setTextureOffset(0, 28).addBox(-1.4236F, -3.0F, -4.9848F, 1.0F, 3.0F, 2.0F, 0.0F, false);
		bone4.setTextureOffset(4, 40).addBox(-1.4022F, -2.249F, -6.981F, 1.0F, 2.0F, 2.0F, 0.0F, false);
		bone4.setTextureOffset(10, 23).addBox(-1.3807F, -1.4981F, -9.9772F, 1.0F, 1.0F, 3.0F, 0.0F, false);

		Wing1 = new ModelRenderer(this);
		Wing1.setRotationPoint(-2.75F, -7.25F, 0.0F);
		Base.addChild(Wing1);
		setRotationAngle(Wing1, 0.0F, 0.1745F, -0.0873F);
		Wing1.setTextureOffset(18, 28).addBox(-1.25F, -2.75F, -1.0F, 1.0F, 3.0F, 2.0F, 0.0F, false);

		bone3 = new ModelRenderer(this);
		bone3.setRotationPoint(-0.25F, 0.25F, 0.75F);
		Wing1.addChild(bone3);
		bone3.setTextureOffset(22, 16).addBox(-1.25F, -4.0F, -3.0F, 1.0F, 4.0F, 3.0F, 0.0F, false);
		bone3.setTextureOffset(12, 28).addBox(-1.0764F, -3.0F, -4.9848F, 1.0F, 3.0F, 2.0F, 0.0F, false);
		bone3.setTextureOffset(10, 40).addBox(-1.0549F, -2.249F, -6.981F, 1.0F, 2.0F, 2.0F, 0.0F, false);
		bone3.setTextureOffset(18, 23).addBox(-1.0334F, -1.4981F, -9.9772F, 1.0F, 1.0F, 3.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		Base.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
*/
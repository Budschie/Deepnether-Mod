package de.budschie.deepnether.entity.renders;

import de.budschie.deepnether.main.References;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class HellCreeperRenderer extends CreeperRenderer
{
	public static final ResourceLocation HELL_CREEPER = new ResourceLocation(References.MODID, "textures/entity/hell_creeper/hell_creeper.png");
	
	public HellCreeperRenderer(EntityRendererManager renderManagerIn)
	{
		super(renderManagerIn);
	}
	
	@Override
	public ResourceLocation getEntityTexture(CreeperEntity entity)
	{
		return HELL_CREEPER;
	}
}

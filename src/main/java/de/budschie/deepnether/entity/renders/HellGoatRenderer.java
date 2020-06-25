package de.budschie.deepnether.entity.renders;

import de.budschie.deepnether.entity.HellGoatEntity;
import de.budschie.deepnether.main.References;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;

public class HellGoatRenderer extends LivingRenderer<HellGoatEntity, HellGoatModel>
{
	public HellGoatRenderer(EntityRendererManager rendererManager)
	{
		super(rendererManager, new HellGoatModel(), .5f);
	}

	@Override
	public ResourceLocation getEntityTexture(HellGoatEntity entity)
	{
		return new ResourceLocation(References.MODID, "textures/entity/hell_goat/hell_goat.png");
	}
}

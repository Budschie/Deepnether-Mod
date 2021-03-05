package de.budschie.deepnether.entity.renders;

import de.budschie.deepnether.entity.EntityInit;
import de.budschie.deepnether.entity.HellCreeperEntity;
import de.budschie.deepnether.entity.HellDevilEntity;
import de.budschie.deepnether.entity.ShadowEntity;
import de.budschie.deepnether.entity.ShadowTrapEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class EntityRenderInit
{
	public static void registerEntityRenders()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityInit.SHADOW_TRAP, new IRenderFactory<ShadowTrapEntity>()
		{
			@Override
			public EntityRenderer<? super ShadowTrapEntity> createRenderFor(EntityRendererManager manager)
			{
				return new EmptyRenderer(manager);
			}
		});
		
		RenderingRegistry.registerEntityRenderingHandler(EntityInit.HELL_CREEPER, new IRenderFactory<HellCreeperEntity>()
		{
			@Override
			public EntityRenderer<? super HellCreeperEntity> createRenderFor(EntityRendererManager manager)
			{
				return new HellCreeperRenderer(manager);
			}
		});
		
		RenderingRegistry.registerEntityRenderingHandler(EntityInit.HELL_DEVIL, new IRenderFactory<HellDevilEntity>()
		{
			@Override
			public EntityRenderer<? super HellDevilEntity> createRenderFor(EntityRendererManager manager)
			{
				return new HellDevilRenderer(manager);
			}
		});
		
		RenderingRegistry.registerEntityRenderingHandler(EntityInit.SHADOW, new IRenderFactory<ShadowEntity>()
		{
			@Override
			public EntityRenderer<? super ShadowEntity> createRenderFor(EntityRendererManager manager)
			{
				return new ShadowRenderer(manager);
			}
		});
		
		RenderingRegistry.registerEntityRenderingHandler(EntityInit.HELL_GOAT, (manager) -> new HellGoatRenderer(manager));
	}
}

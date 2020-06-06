package de.budschie.deepnether.gui.budschiegui;

import java.awt.Point;
import java.util.function.Supplier;

import com.mojang.blaze3d.systems.RenderSystem;

import de.budschie.deepnether.util.Util;
import de.budschie.deepnether.util.Util.RGBA;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.potion.HealthBoostEffect;
import net.minecraft.util.ResourceLocation;

public class ImageAnimatedStep extends BaseStoryBordedStep
{
	Supplier<Point> locFrom;
	Supplier<Point> scaleFrom;
	
	Supplier<Point> locTo;
	Supplier<Point> scaleTo;
	
	Point imageSize;
	
	RGBA color = null;
	RGBA color2 = null;
	
	float multiplierX = 1.0f;
	float multiplierY = 1.0f;
	
	ResourceLocation rs;
	
	@Override
	public void draw(GuiWithSteps gui, float time)
	{
		if(rs == null)
			return;
		else
		{
			int scaleX = (int) Util.lerp(scaleFrom.get().x, scaleTo.get().x, time);
			int scaleY = (int) Util.lerp(scaleFrom.get().y, scaleTo.get().y, time);
			
			int posX = (int) Util.lerp(locFrom.get().x, locTo.get().x, time);
			int posY = (int) Util.lerp(locFrom.get().y, locTo.get().y, time);
			
			Minecraft.getInstance().getTextureManager().bindTexture(rs);
			
			RenderSystem.enableAlphaTest();
			RenderSystem.enableBlend();
			RenderSystem.defaultBlendFunc();
			RenderSystem.defaultAlphaFunc();
			
			if(color != null)
			{
				if(color2 == null)
					RenderSystem.color4f(color.getRed()/255f, color.getGreen()/255f, color.getBlue()/255f, color.getAlpha()/255f);
				else
					RenderSystem.color4f(Util.lerp(color.getRed(), color2.getRed(), time)/255f, Util.lerp(color.getGreen(), color2.getGreen(), time)/255f, Util.lerp(color.getBlue(), color2.getBlue(), time)/255f, Util.lerp(color.getAlpha(), color2.getAlpha(), time)/255f);
			}
			
			AbstractGui.blit(posX, posY, scaleX, scaleY, multiplierX, multiplierY, scaleX, scaleY, scaleX, scaleY);
			
			RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		}
	}
	
	public ImageAnimatedStep setMultiplier(float multiplierX, float multiplierY)
	{
		this.multiplierX = multiplierX;
		this.multiplierY = multiplierY;
		
		return this;
	}
	
	public ImageAnimatedStep setLocation(Supplier<Point> locFrom, Supplier<Point> locTo)
	{
		this.locFrom = locFrom;
		this.locTo = locTo;
		return this;
	}
	
	public ImageAnimatedStep setScale(Supplier<Point> scaleFrom, Supplier<Point> scaleTo)
	{
		this.scaleFrom = scaleFrom;
		this.scaleTo = scaleTo;
		return this;
	}
	
	public ImageAnimatedStep setImage(final ResourceLocation resourceLocation)
	{
		rs = resourceLocation;
		return this;
	}
	
	public ImageAnimatedStep setImageSize(Point size)
	{
		imageSize = size;
		return this;
	}
	
	public ImageAnimatedStep setColor(RGBA rgba)
	{
		this.color = rgba;
		this.color2 = null;
		return this;
	}
	
	public ImageAnimatedStep setAnimatedColor(RGBA rgbaFrom, RGBA rgbaTo)
	{
		this.color = rgbaFrom;
		this.color2 = rgbaTo;
		return this;
	}
}

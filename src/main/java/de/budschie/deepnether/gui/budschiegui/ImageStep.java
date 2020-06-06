package de.budschie.deepnether.gui.budschiegui;

import java.awt.Point;
import java.util.function.Supplier;

import com.mojang.blaze3d.systems.RenderSystem;

import de.budschie.deepnether.util.Util;
import de.budschie.deepnether.util.Util.RGBA;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;

public class ImageStep extends BaseStoryBordedStep
{
	Point imageSize = new Point();
	Point loc = new Point();
	Supplier<Point> scale;
	
	float multiplierX = 1.0f;
	float multiplierY = 1.0f;
	
	RGBA color = null;
	RGBA color2 = null;
	
	ResourceLocation rs;
	
	@Override
	public void draw(GuiWithSteps gui, float time)
	{
		if(rs == null)
			return;
		else
		{
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
			
			AbstractGui.blit(loc.x, loc.y, scale.get().x, scale.get().y, multiplierX, multiplierY, scale.get().x, scale.get().y, scale.get().x, scale.get().y);
			
			RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		}
	}
	
	public ImageStep setMultiplier(float multiplierX, float multiplierY)
	{
		this.multiplierX = multiplierX;
		this.multiplierY = multiplierY;
		
		return this;
	}
	
	public ImageStep setLocation(Point loc)
	{
		this.loc = loc;
		return this;
	}
	
	public ImageStep setScale(Supplier<Point> scale)
	{
		this.scale = scale;
		return this;
	}
	
	public ImageStep setColor(RGBA rgba)
	{
		this.color = rgba;
		this.color2 = null;
		return this;
	}
	
	public ImageStep setAnimatedColor(RGBA rgbaFrom, RGBA rgbaTo)
	{
		this.color = rgbaFrom;
		this.color2 = rgbaTo;
		return this;
	}
	
	public ImageStep setImageSize(Point point)
	{
		this.imageSize = point;
		return this;
	}
	
	public ImageStep setImage(final ResourceLocation resourceLocation)
	{
		rs = resourceLocation;
		return this;
	}
}

package de.budschie.deepnether.gui.budschiegui;

import com.mojang.blaze3d.systems.RenderSystem;

import de.budschie.deepnether.util.Util;
import de.budschie.deepnether.util.Util.RGBA;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;

public class BlendStep extends BaseStoryBordedStep
{
	RGBA rgbaFrom = new RGBA(0, 0, 0, 0);
	RGBA rgbaTo = new RGBA(0, 0, 0, 255);
	
	public BlendStep setAnimatedColor(RGBA rgbaFrom, RGBA rgbaTo)
	{
		this.rgbaFrom = rgbaFrom;
		this.rgbaTo = rgbaTo;
		return this;
	}
	
	public BlendStep setRGBTo(RGBA rgba)
	{
		this.rgbaTo = rgba;
		return this;
	}
	
	@Override
	public void draw(GuiWithSteps gui, float time)
	{
		RenderSystem.enableAlphaTest();
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.defaultAlphaFunc();
		
		AbstractGui.fill(0, 0, Minecraft.getInstance().getMainWindow().getScaledWidth(), Minecraft.getInstance().getMainWindow().getScaledHeight(), Util.getMCColorFromRGBA((int)Util.lerp(rgbaFrom.getRed(), rgbaTo.getRed(), time), (int)Util.lerp(rgbaFrom.getGreen(), rgbaTo.getGreen(), time), (int)Util.lerp(rgbaFrom.getBlue(), rgbaTo.getBlue(), time), (int)Util.lerp(rgbaFrom.getAlpha(), rgbaTo.getAlpha(), time)));
	}
}

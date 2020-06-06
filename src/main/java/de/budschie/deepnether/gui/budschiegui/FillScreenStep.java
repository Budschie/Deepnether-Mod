package de.budschie.deepnether.gui.budschiegui;

import de.budschie.deepnether.util.Util;
import de.budschie.deepnether.util.Util.RGBA;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;

public class FillScreenStep extends BaseStoryBordedStep
{
	RGBA rgba = new RGBA(0, 0, 0, 255);
	
	@Override
	public void draw(GuiWithSteps gui, float time)
	{
		AbstractGui.fill(0, 0, Minecraft.getInstance().getMainWindow().getScaledWidth(), Minecraft.getInstance().getMainWindow().getScaledHeight(), Util.getMCColorFromRGBA(rgba));
	}
	
	public FillScreenStep setRGB(RGBA rgba)
	{
		this.rgba = rgba;
		return this;
	}
}

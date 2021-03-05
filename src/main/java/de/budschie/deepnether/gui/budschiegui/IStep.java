package de.budschie.deepnether.gui.budschiegui;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.util.math.vector.Matrix4f;

public interface IStep
{
	int getFrameDuration();
	void onStart(GuiWithSteps gui);
	void draw(MatrixStack matrix, GuiWithSteps gui, float time);
	
	void onEnd(GuiWithSteps gui);
}

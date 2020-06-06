package de.budschie.deepnether.gui.budschiegui;

public interface IStep
{
	int getFrameDuration();
	void onStart(GuiWithSteps gui);
	void draw(GuiWithSteps gui, float time);
	
	void onEnd(GuiWithSteps gui);
}

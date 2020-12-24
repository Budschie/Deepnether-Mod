package de.budschie.deepnether.gui.budschiegui;

import java.util.ArrayList;

import de.budschie.deepnether.util.Util;
import de.budschie.deepnether.util.Util.RGBA;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;

public abstract class BaseStoryBordedStep implements IStep, IStoryBord, IOwnStackAdder
{	
	boolean clearScene = false;
	
	IStep attachedStep;
	
	int duration;
	
	boolean shouldAddToStack = true;
	
	@Override
	public <A extends IStep> A attach(A step)
	{
		this.attachedStep = step;
		return step;
	}

	@Override
	public int getFrameDuration()
	{
		return duration;
	}

	@Override
	public void onStart(GuiWithSteps gui)
	{
		if(clearScene)
			gui.newScene();
	}

	@Override
	public BaseStoryBordedStep setFrameDuration(int duration)
	{
		this.duration = duration;
		return this;
	}

	@Override
	public void onEnd(GuiWithSteps gui)
	{
		if(attachedStep != null)
			gui.playStep(attachedStep);
	}
	
	@Override
	public BaseStoryBordedStep clearScene()
	{
		clearScene = true;
		return this;
	}
	
	@Override
	public BaseStoryBordedStep disableStackAdding()
	{
		shouldAddToStack = false;
		return this;
	}
	
	@Override
	public void addToStack(ArrayList<IStep> stack)
	{
		if(shouldAddToStack)
			stack.add(this);
	}
}

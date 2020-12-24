package de.budschie.deepnether.gui.budschiegui;

import java.util.ArrayList;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.AbstractGui;
import net.minecraftforge.common.MinecraftForge;

/** Unideal **/
public class GuiWithSteps extends AbstractGui
{
	private ArrayList<IStep> currentScene = new ArrayList<IStep>();
	private IStep currentStep;
	
	private int currentTime = 0;
	
	public void newScene()
	{
		currentScene.clear();
	}
	
	public void playStep(IStep step)
	{
		currentTime = 0;
		
		final IStep currentStepImmutable = currentStep;
		
		if(currentStep != null)
		{
			if(currentStep instanceof IOwnStackAdder)
				((IOwnStackAdder)currentStep).addToStack(currentScene);
			else
				currentScene.add(currentStepImmutable);
		}
		
		step.onStart(this);
		currentStep = step;
	}
	
	public void drawWithUpdate(MatrixStack matrix)
	{
		
		if(currentStep != null)
		{
			float time = (float)currentTime / (float)currentStep.getFrameDuration();
			
			for(int i = 0; i < currentScene.size(); i++)
			{
				currentScene.get(i).draw(matrix, this, 1f);
			}
			
			currentStep.draw(matrix, this, time);
			
			currentTime++;
			
			IStep stepBefore = currentStep;
			
			if(hasEnded())
			{
				currentStep.onEnd(this);
			
				if(currentStep == stepBefore)
				{
					GuiEndedEvent event = new GuiEndedEvent();
					
					event.gui = this;
					
					MinecraftForge.EVENT_BUS.post(event);
				}
			}
		}
	}
	
	public boolean hasEnded()
	{
		return currentTime > currentStep.getFrameDuration();
	}
}
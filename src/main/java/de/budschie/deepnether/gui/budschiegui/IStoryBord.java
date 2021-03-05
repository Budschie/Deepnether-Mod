package de.budschie.deepnether.gui.budschiegui;

public interface IStoryBord
{
	/** Should return the attached step **/
	<A extends IStep> A attach(A step);
	
	IStep setFrameDuration(int duration);
	
	IStep clearScene();
	
	IStep disableStackAdding();
}

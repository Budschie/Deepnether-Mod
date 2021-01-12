package de.budschie.deepnether.util;

public class MathUtil
{
	/** This method implements a linear interpolation algorithm. **/
	public static float linearInterpolation(float from, float to, float at)
	{
		return (1-at)*from + to * at;
	}
	
	/** This method implements a bilinear interpolation algorithm, and it uses {@link MathUtil#linearInterpolation(float, float, float)}**/
	public static float bilinearInterpolation(float upperLeft, float upperRight, float bottomLeft, float bottomRight, float x, float y)
	{
		float upperX = linearInterpolation(upperLeft, upperRight, x);
		float bottomX = linearInterpolation(bottomLeft, bottomRight, x);
		
		return linearInterpolation(bottomX, upperX, y);
	}
	
	/** This method implements a linear interpolation algorithm. **/
	public static double linearInterpolation(double from, double to, double at)
	{
		return (1-at)*from + to * at;
	}
	
	/** This method implements a bilinear interpolation algorithm, and it uses {@link MathUtil#linearInterpolation(float, float, float)}**/
	public static double bilinearInterpolation(double upperLeft, double upperRight, double bottomLeft, double bottomRight, double x, double y)
	{
		double upperX = linearInterpolation(upperLeft, upperRight, x);
		double bottomX = linearInterpolation(bottomLeft, bottomRight, x);
		
		return linearInterpolation(bottomX, upperX, y);
	}
}

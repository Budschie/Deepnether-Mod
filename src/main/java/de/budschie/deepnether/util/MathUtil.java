package de.budschie.deepnether.util;

public class MathUtil
{
	/** When progress is one, output is b. **/
	public static double lerp(double a, double b, double progress)
	{
		return b * progress + (1 - progress) * a;
	}
}

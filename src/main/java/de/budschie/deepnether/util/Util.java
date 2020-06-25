package de.budschie.deepnether.util;

public class Util
{
	public static class RGBA
	{
		private int r;
		private int g;
		private int b;
		private int a;
		
		public int getAlpha()
		{
			return a;
		}
		
		public int getRed()
		{
			return r;
		}
		
		public int getBlue()
		{
			return b;
		}
		
		public int getGreen()
		{
			return g;
		}
		
		public RGBA(int red, int green, int blue, int alpha)
		{
			r = red;
			g = green;
			b = blue;
			a = alpha;
		}
	}
	
	public static RGBA getRGBAFromMCColor(int color)
	{
	    int red = (color >> 24) & 0xff;
	    int green = (color >> 16) & 0xff;
	    int blue = (color >> 8) & 0xff;
	    int alpha = (color) & 0xff;
	    
		return new RGBA(red, green, blue, alpha);
	}
	
	public static int getMCColorFromRGBA(RGBA rgba)
	{
		int mcColor = rgba.a;
		mcColor = (mcColor << 8) + rgba.b; 
		mcColor = (mcColor << 8) + rgba.g;
		mcColor = (mcColor << 8) + rgba.r;
		
		return mcColor;
	}
	
	public static int getMCColorFromRGBA(int r, int g, int b, int a)
	{
		int mcColor = a;
		mcColor = (mcColor << 8) + b; 
		mcColor = (mcColor << 8) + g;
		mcColor = (mcColor << 8) + r;
		
		return mcColor;
	}
	
	public static float lerp(float val1, float val2, float toLerp)
	{
		return val1 * (1-toLerp) + val2 * toLerp;
	}
}

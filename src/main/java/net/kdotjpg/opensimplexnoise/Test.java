package net.kdotjpg.opensimplexnoise;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import de.budschie.deepnether.noise.VoronoiNoise;
import de.budschie.deepnether.util.MathUtil;

public class Test
{
	public static void main(String... args)
	{
		// System.out.println(MathUtil.linearInterpolation());
		
		/*
		 * 
		 */
		
		/*
		File imageFile = new File("C:\\Users\\Budschie\\AppData\\img.png");
		BufferedImage bImg = new BufferedImage(512, 512, BufferedImage.TYPE_INT_ARGB);
		
		VoronoiNoise noise = new VoronoiNoise(0);
		
		int spacing = 0;
		double[][] expandedBuffer = new double[512 + spacing * 2][512 + spacing * 2];
		
		for(int x = -spacing; x < 512 + spacing; x++)
		{
			for(int z = -spacing; z < 512 + spacing; z++)
			{
				expandedBuffer[x + spacing][z + spacing] = getGroundHeight(5, x, z);
			}
		}
		
		for(int x = 0; x < 512; x++)
		{
			for(int y = 0; y < 512; y++)
			{
				//int value = (int) (noise.voronoiNoise(x, y, .0125, false) * 255);
				int value = (int) (MathUtil.bilinearInterpolation(expandedBuffer[x][y + spacing*2], expandedBuffer[x + spacing*2][y + spacing*2], expandedBuffer[x][y], expandedBuffer[x + spacing*2][y], .5f, .5f) * 256);
				int a = (int)(255); //alpha
				int r = (int)(value); //red
				int g = (int)(value); //green
				int b = (int)(value); //blue
				int p = (a<<24) | (r<<16) | (g<<8) | b;
				bImg.setRGB(x, y, p);
			}
		}
		
		try
		{
			ImageIO.write(bImg, "png", imageFile);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		// .0125
	}
	
	
	public static double getGroundHeight(long seed, int x, int z)
	{
		OpenSimplexNoise osn = new OpenSimplexNoise(seed);
		
		double currentSize = .07;
		double transparency = 1;
		
		double currentValue = 0;
		
		for(int octaves = 0; octaves < 1; octaves++)
		{
			currentValue += osn.eval(x * currentSize, z * currentSize) * transparency;
			
			transparency *= 0.5;
			currentSize *= 2;
		}
		
		if(currentValue < -1)
			currentValue = -1;
		else if(currentValue > 1)
			currentValue = 1;
		
		return (currentValue + 1) * 0.5;
	}
	
	
	/*
	public static double getGroundHeight(long seed, int x, int z, int spacing)
	{
		return MathUtil.bilinearInterpolation(getRandomValue(seed, x + spacing, z + spacing), getRandomValue(seed, x, z + spacing), getRandomValue(seed, x + spacing, z), getRandomValue(seed, x, z), .5, .5);
	}
	*/
	
	public static double getRandomValue(long seed, int x, int z)
	{
		return new Random(x).nextDouble();
	}
	
	/** 
	 * 		OpenSimplexNoise osn = new OpenSimplexNoise(seed);
		
		double currentSize = size;
		double transparency = 1;
		
		double currentValue = 0;
		
		for(int octaves = 0; octaves < 8; octaves++)
		{
			currentValue += osn.eval(x * currentSize, z * currentSize) * transparency;
			
			transparency *= 0.5;
			currentSize *= 2;
		}
		
		if(currentValue < -1)
			currentValue = -1;
		else if(currentValue > 1)
			currentValue = 1;
		
		return currentValue;
	 */
}

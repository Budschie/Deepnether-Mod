package de.budschie.deepnether.noise;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class VoronoiNoise
{
	private static class VoronoiPair
	{
		double nextPointX, nextPointY, value;
	}
	
	long seed;

	public VoronoiNoise(long seed)
	{
		this.seed = seed;
	}

	public long getSeed()
	{
		return seed;
	}

	public void setSeed(long seed)
	{
		this.seed = seed;
	}

	public double voronoiNoise(double xCoord, double yCoord, double scale)
	{
		// First, get grid you are in and calc grid id
		double output = 10000;

		Point grid = new Point((int) Math.floor(xCoord), (int) Math.floor(yCoord));

		ArrayList<Double> list = new ArrayList<Double>();

		double nextPointX;
		double nextPointY;

		for (int x = grid.x - 1; x <= grid.x + 1; x++)
		{
			for (int y = grid.y - 1; y <= grid.y + 1; y++)
			{
				// System.out.println("X: " + x + " Y: " + y);
				double randXPoint = (new Random(x * x + y + seed).nextInt(1000) / 1000d) + (double) x;
				double randYPoint = (new Random(y * x + x + seed).nextInt(1000) / 1000d) + (double) y;

				// System.out.println("RandXPoint: " + randXPoint + " randYPoint " +
				// randYPoint);

				double dist = Math.max(0, Math.min(1,
						Math.abs(Math.sqrt(Math.pow((randXPoint - xCoord), 2) + Math.pow((randYPoint - yCoord), 2)))));

				if (dist > 1 || dist < 0)
					return 0;

				list.add(dist);
			}
		}

		list.sort(Double::compare);

		return list.get(0);
	}

	public double voronoiNoise(double xCoord, double yCoord, double scale, int number)
	{
		// First, get grid you are in and calc grid id
		double output = 10000;

		xCoord *= scale;
		yCoord *= scale;
		
		Point grid = new Point((int) Math.floor(xCoord), (int) Math.floor(yCoord));

		ArrayList<Double> list = new ArrayList<Double>();

		double nextPointX;
		double nextPointY;

		for (int x = grid.x - 1; x <= grid.x + 1; x++)
		{
			for (int y = grid.y - 1; y <= grid.y + 1; y++)
			{
				// System.out.println("X: " + x + " Y: " + y);
				double randXPoint = (new Random(x * x + y + seed).nextInt(1000) / 1000d) + (double) x;
				double randYPoint = (new Random(y * x + x + seed).nextInt(1000) / 1000d) + (double) y;

				// System.out.println("RandXPoint: " + randXPoint + " randYPoint " +
				// randYPoint);

				double dist = Math.max(0, Math.min(1,
						Math.abs(Math.sqrt(Math.pow((randXPoint - xCoord), 2) + Math.pow((randYPoint - yCoord), 2)))));

				if (dist > 1 || dist < 0)
					return 0;

				list.add(dist);
			}
		}

		list.sort(Double::compare);

		return list.get(number);
	}
	
	public double voronoiNoise(double xCoord, double yCoord, double scale, int number, boolean useID)
	{
		// First, get grid you are in and calc grid id
		double output = 10000;

		xCoord *= scale;
		yCoord *= scale;
		
		Point grid = new Point((int) Math.floor(xCoord), (int) Math.floor(yCoord));

		ArrayList<VoronoiPair> list = new ArrayList<VoronoiPair>();

		double nextPointX;
		double nextPointY;

		for (int x = grid.x - 1; x <= grid.x + 1; x++)
		{
			for (int y = grid.y - 1; y <= grid.y + 1; y++)
			{
				// System.out.println("X: " + x + " Y: " + y);
				double randXPoint = (new Random(x * x + y + seed).nextInt(1000) / 1000d) + (double) x;
				double randYPoint = (new Random(y * x + x + seed).nextInt(1000) / 1000d) + (double) y;

				// System.out.println("RandXPoint: " + randXPoint + " randYPoint " +
				// randYPoint);

				double dist = Math.max(0, Math.min(1,
						Math.abs(Math.sqrt(Math.pow((randXPoint - xCoord), 2) + Math.pow((randYPoint - yCoord), 2)))));

				if (dist > 1 || dist < 0)
					return 0;

				VoronoiPair voronoiPair = new VoronoiPair();
				voronoiPair.nextPointX = randXPoint * 100;
				voronoiPair.nextPointY = randYPoint * 100;
				voronoiPair.value = dist;
				list.add(voronoiPair);
			}
		}
		list.sort((element, otherElement) -> Double.compare(element.value, otherElement.value));

		return useID ? new Random((long) (list.get(number).nextPointX + list.get(number).nextPointY) + seed).nextInt(5000) / 5000d : list.get(number).value;
	}
	
	/** This method can use an ID to create flat no-smooth noise if {@code useID} is set to {@code true}.**/
	public double voronoiNoise(double xCoord, double yCoord, double scale, boolean useID)
	{
		// First, get grid you are in and calc grid id
		double output = 10000;
		
		xCoord *= scale;
		yCoord *= scale;

		Point grid = new Point((int) Math.floor(xCoord), (int) Math.floor(yCoord));

		double nextPointX = 0;
		double nextPointY = 0;

		for (int x = grid.x - 1; x <= grid.x + 1; x++)
		{
			for (int y = grid.y - 1; y <= grid.y + 1; y++)
			{
				// System.out.println("X: " + x + " Y: " + y);
				double randXPoint = (new Random(x * x + y + seed).nextInt(1000) / 1000d) + (double) x;
				double randYPoint = (new Random(y * x + x + seed).nextInt(1000) / 1000d) + (double) y;

				// System.out.println("RandXPoint: " + randXPoint + " randYPoint " +
				// randYPoint);

				double dist = Math.max(0, Math.min(1,
						Math.abs(Math.sqrt(Math.pow((randXPoint - xCoord), 2) + Math.pow((randYPoint - yCoord), 2)))));

				if (dist > 1 || dist < 0)
					return 0;

				if (dist < output)
				{
					output = dist;
					nextPointX = randXPoint * 100;
					nextPointY = randYPoint * 100;
				}
			}
		}

		return useID ? new Random((long) (nextPointX + nextPointY) + seed).nextInt(5000) / 5000d : output;
	}
}

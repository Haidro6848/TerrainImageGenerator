package com.ngexdesign.terrainimagegenerator;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ElevationImageGenerator
{

	public static BufferedImage generate(ArrayList<ElevationPoint> inputData)
	{
		if (inputData == null && inputData.isEmpty())
			return null;
		// Get the boundary points
		double[] boundaries = getBoundaryPoints(inputData);

		// Determine width and height of image
		double ratio = (boundaries[1] - boundaries[0])
		        / (boundaries[3] - boundaries[2]);

		int width = 470;
		int height = (int) (width / ratio);

		BufferedImage image = new BufferedImage(width, height,
		        BufferedImage.TYPE_INT_RGB);

		// map latitude/longitude to x y in image
		for (ElevationPoint e : inputData)
		{
			int[] xy = mapElevationPointToPixel(boundaries, e);

			// map z to RGB color in image
		}
		return null;
	}

	private static int[] mapElevationPointToPixel(double[] boundaries,
	        ElevationPoint e)
	{
		// TODO Auto-generated method stub
		return null;
	}

	private static double[] getBoundaryPoints(
	        ArrayList<ElevationPoint> inputData)
	{
		double xMin = inputData.get(0).latitude;
		double xMax = inputData.get(0).latitude;
		double yMin = inputData.get(0).longitude;
		double yMax = inputData.get(0).longitude;
		double zMin = inputData.get(0).height;
		double zMax = inputData.get(0).height;

		for (ElevationPoint e : inputData)
		{
			xMin = Math.min(xMin, e.latitude);
			xMax = Math.max(xMax, e.latitude);
			yMin = Math.min(yMin, e.latitude);
			yMax = Math.max(yMax, e.latitude);
			zMin = Math.min(zMin, e.latitude);
			zMax = Math.max(zMax, e.latitude);
		}
		double[] results = new double[]
		{ xMin, xMax, yMin, yMax, zMin, zMax };
		return results;
	}
}

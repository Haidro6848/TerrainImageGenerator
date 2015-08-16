package com.ngexdesign.terrainimagegenerator;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ElevationImageGenerator {

	public static BufferedImage generate(ArrayList<ElevationPoint> inputData) {
		if (inputData == null || inputData.isEmpty())
			return null;
		// Get the boundary points
		double[] boundaries = getBoundaryPoints(inputData);

		// get the image width and height by elevation resolution
		int dimension[] = getImageDimension(inputData, boundaries);

		BufferedImage image = new BufferedImage(dimension[0], dimension[1],
				BufferedImage.TYPE_INT_RGB);

		// map latitude/longitude to x y in image
		for (ElevationPoint e : inputData) {
			// map elevation point to a pixel location in image
			int[] xy = mapElevationPointToPixelLocation(boundaries[0],boundaries[1],boundaries[2],boundaries[3],e,dimension[0],dimension[1]);
			// map z to RGB color in image
			Color color = mapElevationHeightToColor(boundaries[4], boundaries[5], e.height);
			image.setRGB(xy[0],xy[1],color.getRGB()); 
		}
		return image;
	}

	// Determine width and height of image
	private static int[] getImageDimension(ArrayList<ElevationPoint> inputData,
			double[] boundaries) {
		double ratio = (boundaries[1] - boundaries[0])
				/ (boundaries[3] - boundaries[2]);
		double resolution = (boundaries[1] - boundaries[0])*(boundaries[3] - boundaries[2])/inputData.size();
		resolution = Math.sqrt(resolution);
		
		int width = (int) ((boundaries[1] - boundaries[0])/resolution);
		
		width = (int) (width * 0.95);	//compensate rounding errors
		
		int height = (int) (width / ratio);
		
		int[] dimension = new int[] {width, height};
		return dimension;
	}

	private static int[] mapElevationPointToPixelLocation(double minX, double maxX, double minY, double maxY,
			ElevationPoint e, int imageWidth, int imageHeight) {
		double x = (e.latitude-minX)/(maxX-minX);
		double y = (e.longitude-minY)/(maxY-minY);
		
		int[] location = new int[] {(int)(x*(imageWidth)), (int)(y*(imageHeight))};
		if(location[0] == imageWidth) location[0] = imageWidth-1;
		if(location[1] == imageHeight) location[1] = imageHeight-1;
		
		return location;
	}

	private static Color mapElevationHeightToColor(double minHeight,
			double maxHeight, double height) {
		final int MAX = 255;

		height = (height - minHeight) / (maxHeight - minHeight);
		height = height * MAX;
		
		double green, red, blue;
		green = red = blue = 0.0;
		if (height >= 0 && height <= 128) {
			// interpolate between (1.0f, 0.0f, 0.0f) and (0.0f, 1.0f, 0.0f)
			green = height / 128.0f;
			red = 1.0f - green;
			blue = 0.0f;

		} else if (height > 128 && height <= 255) {
			// interpolate between (0.0f, 1.0f, 0.0f) and (0.0f, 0.0f, 1.0f)
			red = 0.0f;
			blue = (height - 127) / 128.0f;
			green = 1.0f - blue;
		}
		Color color = new Color((int) (red*MAX), (int) (green*MAX), (int) (blue*MAX));
		return color; 
	}

	private static double[] getBoundaryPoints(
			ArrayList<ElevationPoint> inputData) {
		double xMin = inputData.get(0).latitude;
		double xMax = inputData.get(0).latitude;
		double yMin = inputData.get(0).longitude;
		double yMax = inputData.get(0).longitude;
		double zMin = inputData.get(0).height;
		double zMax = inputData.get(0).height;

		for (ElevationPoint e : inputData) {
			xMin = Math.min(xMin, e.latitude);
			xMax = Math.max(xMax, e.latitude);
			yMin = Math.min(yMin, e.longitude);
			yMax = Math.max(yMax, e.longitude);
			zMin = Math.min(zMin, e.height);
			zMax = Math.max(zMax, e.height);
		}
		double[] results = new double[] { xMin, xMax, yMin, yMax, zMin, zMax };
		return results;
	}
}

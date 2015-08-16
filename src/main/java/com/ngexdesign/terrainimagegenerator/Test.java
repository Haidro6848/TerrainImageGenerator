package com.ngexdesign.terrainimagegenerator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Test
{

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		ArrayList<ElevationPoint> data = CSVReader.load("LOWGRID44.csv");

		BufferedImage image = ElevationImageGenerator.generate(data);
		
		File file = new File("LOWGRID44.jpg");
        try {
			ImageIO.write(image, "jpg", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

package com.ngexdesign.terrainimagegenerator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVReader
{
	public static ArrayList<ElevationPoint> load(String csvFilePath)
	{
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try
		{
			br = new BufferedReader(new FileReader(csvFilePath));
			ArrayList<ElevationPoint> elevationData = new ArrayList<ElevationPoint>();
			while ((line = br.readLine()) != null)
			{
				// use comma as separator
				String[] row = line.split(cvsSplitBy);
				ElevationPoint point = convertToElevation(row);
				if (point != null)
				{
					elevationData.add(point);
				}
			}
			return elevationData;

		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (br != null)
			{
				try
				{
					br.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	private static ElevationPoint convertToElevation(String[] row)
	{
		try
		{
			ElevationPoint point = new ElevationPoint(
			        Double.parseDouble(row[0]), Double.parseDouble(row[1]),
			        Double.parseDouble(row[2]));
			return point;
		}
		catch (NumberFormatException ne)
		{
			return null;
		}
	}
}

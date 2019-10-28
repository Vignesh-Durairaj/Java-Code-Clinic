package com.clinic.challenges.statistics;

import static java.nio.file.Paths.get;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BPTrendEnhanced {

	private static final DateFormat format = new SimpleDateFormat("yyyy_MM_dd HH:mm:ss");
	
	private List<WeatherData> weatherData = new ArrayList<>();
	
	public void readData(String fileName) throws URISyntaxException {
		URI filePath = getClass().getClassLoader().getResource(fileName).toURI();
        
        try (BufferedReader reader = Files.newBufferedReader(get(filePath))) {
        	String currentLine = null;
        	boolean isFirstLine = true;
        	int index = 0;
        	while((currentLine = reader.readLine()) != null) {
        		
        		if (isFirstLine) {
        			isFirstLine = false;
        			continue;
        		}
        		
        		weatherData.add(getDataFromArray(currentLine.split("\t"), index));
        		index ++;
        	}
        } catch	(IOException e) {
        	e.printStackTrace();
        }
        
	}
	
	public String doCalc(String from, String to){
		float slope = 0f;
		
		try {
			Date fromDate = getDateFromString(from);
			Date toDate = getDateFromString(to);
			
			WeatherData fromData = weatherData
					.stream()
					.filter(data -> data.getDate().compareTo(fromDate) >= 0)
					.findFirst()
					.get();
			
			WeatherData toData = weatherData
					.stream()
					.filter(data -> data.getDate().compareTo(toDate) >= 0)
					.findFirst()
					.get();
			
			if (fromData != null && toData != null) {
				slope = (toData.getPressure() - fromData.getPressure()) / (toData.getIndex() - fromData.getIndex());
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		String result = (slope > 0 ? "Positive" : (slope == 0 ? "Equal" : "Negative"));
		return String.format("From %s to %s.\nThe barometric pressure slope is %f\nThe forcast is : %s\n", 
				from, to, slope, result);
	}
	
	public static void main(String[] args) throws URISyntaxException {
		BPTrendEnhanced bpTrend = new BPTrendEnhanced();
		System.out.println("Reading data...");
		bpTrend.readData("weather-statistics/Environmental_Data_Deep_Moor_2012.txt");
		bpTrend.readData("weather-statistics/Environmental_Data_Deep_Moor_2013.txt");
		bpTrend.readData("weather-statistics/Environmental_Data_Deep_Moor_2014.txt");
		bpTrend.readData("weather-statistics/Environmental_Data_Deep_Moor_2015.txt");
        System.out.println("Done!");
        System.out.println("Total number of weather data entries : " + bpTrend.weatherData.size());
        
        String from = "";
        String to   = "";
        
        System.out.println("Test Case #1:");

        from = "2012_01_01 00:30:00";
        to = "2012_01_01 02:30:00";

        System.out.println(bpTrend.doCalc(from, to) );

        System.out.println("Test Case #2:");

        from = "2013_03_15 10:30:00";
        to = "2013_03_17 02:30:00";

        System.out.println(bpTrend.doCalc(from, to) );

        System.out.println("Test Case #3:");

        from = "2014_06_21 10:00:00";
        to = "2014_06_25 23:59:59";

        System.out.println(bpTrend.doCalc(from, to) );
	}
	
	private WeatherData getDataFromArray(String[] inputArr, int index) {
		WeatherData weatherData = null;
		
		try {
			weatherData = new WeatherData(getDateFromString(inputArr[0]), Float.valueOf(inputArr[2]), Float.valueOf(inputArr[4]));
			weatherData.setIndex(index);
		} catch(ParseException | NumberFormatException e) {
			e.printStackTrace();
		}
		
		return weatherData;
	}
	
	private Date getDateFromString(final String dateString) throws ParseException {
		return format.parse(dateString);
	}
}

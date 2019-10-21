package com.clinic.challenge.statistics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BPTrend{

    ArrayList<WeatherEntry> collectedData = new ArrayList<>();
            
    public void readData(String fileName) {
    	
    	String filePath = getClass().getClassLoader().getResource(fileName).getFile();
        DateFormat format = new SimpleDateFormat("yyyy_MM_dd HH:mm:ss");
        
        try (BufferedReader buf = new BufferedReader(new FileReader(filePath))) {
            String line = null;
            String[] wordsArray = null;
            boolean skipFirstLine = true;

            while(true) {
                line = buf.readLine();
                if (skipFirstLine) { // skip data header
                    skipFirstLine = false; continue;
                }
                
                if(line == null) {  
                    break; 
                } else {
                    wordsArray = line.split("\t");
                    
                    WeatherEntry weatherEntry = new WeatherEntry();
                    weatherEntry.when = format.parse(wordsArray[0]);
                    weatherEntry.pressure = Float.valueOf(wordsArray[2]);
                    weatherEntry.humidity = Float.valueOf(wordsArray[4]);
                    collectedData.add(weatherEntry);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public String doCalc( String from, String to){
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date d1 =null;
        Date d2=null;
        try{
            d1 = format.parse(from);
            d2 = format.parse(to);
        } catch (Exception e){
            System.out.println(e);
        }
        
        String result = String.format("From %s to %s\n", format.format(d1), format.format(d2));
        
        WeatherEntry y1 = null;
        WeatherEntry y2 = null;
        int idx = 0;
        int x1 = 0;
        int x2 = 0;

        for (WeatherEntry x: collectedData){
            if ((y1==null) && x.when.compareTo(d1) >= 0) {
                y1 = x;
                x1 = idx;
            }
            if (x.when.compareTo(d2) >= 0) {
                x2 = idx;
                y2 = x; 
                break;
            }
            idx++;
        }
        
        //formula , slope variable
        float slope = (y2.pressure - y1.pressure) / (x2 - x1);
        
        result = result + " The barometric pressure slope is " 
        +String.format("%.6f",slope) + " \nthe forecast is: " + (slope > 0 ? "Positive" : (slope == 0 ? "Equal" : "Negative"));

        //
        //Trend analysis
        //
        return result;
    }

    public static void main(String[] args) {
        BPTrend calcTrend = new BPTrend();
        System.out.println("Reading data...");
        calcTrend.readData("weather-statistics/Environmental_Data_Deep_Moor_2012.txt");
        calcTrend.readData("weather-statistics/Environmental_Data_Deep_Moor_2013.txt");
        calcTrend.readData("weather-statistics/Environmental_Data_Deep_Moor_2014.txt");
        calcTrend.readData("weather-statistics/Environmental_Data_Deep_Moor_2015.txt");
        System.out.println("Done!");
        System.out.println("Total number of weather data entries: "
        + calcTrend.collectedData.size());
        
        String from = "";
        String to   = "";
        
        System.out.println("Test Case #1:");

        from = "2012/01/01 00:30:00";
        to = "2012/01/01 02:30:00";

        System.out.println( calcTrend.doCalc( from, to ) );

        System.out.println("Test Case #2:");

        from = "2013/03/15 10:30:00";
        to = "2013/03/17 02:30:00";

        System.out.println( calcTrend.doCalc( from, to ) );

        System.out.println("Test Case #3:");

        from = "2014/06/21 10:00:00";
        to = "2014/06/25 23:59:59";

        System.out.println( calcTrend.doCalc( from, to ) );
    }
}
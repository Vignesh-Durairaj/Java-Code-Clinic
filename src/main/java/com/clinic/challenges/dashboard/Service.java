package com.clinic.challenges.dashboard;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class Service {
    static ArrayList<Integer> pulsometer = new ArrayList();
    static ArrayList<Integer> r = new ArrayList();
    static ArrayList<Integer> g = new ArrayList();
    static ArrayList<Integer> b = new ArrayList();
    static ArrayList<Float> EngineEff = new ArrayList();


    String pattern = "###.##";
    DecimalFormat decimalFormat = new DecimalFormat(pattern);

    @CrossOrigin
    @RequestMapping("/data")
    public Map<String, Object> home(Service s) {
        // REST API Service Method
    	ZonedDateTime now = ZonedDateTime.now();
    	ZonedDateTime midNight = now.truncatedTo(ChronoUnit.DAYS);
    	
    	Duration duration = Duration.between(midNight, now);
    	int secondsPassed = (int) duration.getSeconds();
    	
    	Map<String, Object> resultMap = new HashMap<>();
    	resultMap.put("pulsometer", pulsometer.get(secondsPassed));
    	resultMap.put("r", r.get(secondsPassed));
    	resultMap.put("g", g.get(secondsPassed));
    	resultMap.put("b", b.get(secondsPassed));
    	resultMap.put("EngineEff", decimalFormat.format(EngineEff.get(secondsPassed)));
    	
    	return resultMap;
    }

    public static void loadData(){
        String filename = "./dashBoardData.csv";
        File file = new File(filename);
        String[] values = null;
        boolean skipHeader = true;

        try{
            Scanner inputStream = new Scanner(file);
            while(inputStream.hasNext()){
                String data = inputStream.next();
                if ( skipHeader ) { skipHeader = false; continue;}                
                values = data.split(",");
                pulsometer.add(Integer.parseInt(values[1]));
                EngineEff.add(Float.parseFloat(values[2]));
                r.add(Integer.parseInt(values[3]));
                g.add(Integer.parseInt(values[4]));
                b.add(Integer.parseInt(values[5]));
            }
            inputStream.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        loadData();
        SpringApplication.run(Service.class, args);
    }

}
package com.clinic.challenge.geolocation;

import org.json.*;

import static java.lang.String.format;

import java.io.*;
import java.net.*;


public class App {

    public static void main(String[] args) throws UnknownHostException {
        JSONObject locationObject = null;

        String ipAddress = getIpAddress();
        System.out.println("The IP Address is : " + ipAddress);

        locationObject = getLocationData(ipAddress);
        if (locationObject.has("bogon") && locationObject.getBoolean("bogon")) {
        	System.out.println("Your network provider is private and hides the location. Searching the details of service provider");
        	ipAddress = "";
        }

        locationObject = getLocationData(ipAddress);
    	System.out.println(format("You are in or near the city of %s, %s, %s", 
    			locationObject.getString("city"), locationObject.getString("region"), locationObject.getString("country")));
    	System.out.println(format("Your approximate location on the map : \nhttps://www.google.com/maps/?q=%s", locationObject.getString("loc"))); 
        
    }

    protected static String getData(String ip) {
        URL url;
        String response = "";
        if (!ip.equals("")) ip = "/" + ip ;
        try {

            String a="https://ipinfo.io"+ip+"/json";
            url = new URL(a);
            URLConnection conn = url.openConnection();

            // open the stream and put it into BufferedReader
            BufferedReader br = new BufferedReader(
                               new InputStreamReader(conn.getInputStream()));

            String inputLine; 
            while ((inputLine = br.readLine()) != null) {
                    response = response + inputLine;
            }
            br.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return response;
    }
    
    protected static String getIpAddress() {
    	String ipAddress = "";
    	try {
			ipAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
    	
    	return ipAddress;
    }
    
    protected static JSONObject getLocationData (final String ipAddress) {
    	return new JSONObject(getData(ipAddress));
    }
}
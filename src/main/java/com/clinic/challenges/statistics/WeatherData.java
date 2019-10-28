package com.clinic.challenges.statistics;

import java.util.Date;

public class WeatherData {

	private Date date;
	private float pressure;
	private float humidity;
	private int index;
	
	public WeatherData(Date date, float pressure, float humidity) {
		super();
		this.date = date;
		this.pressure = pressure;
		this.humidity = humidity;
	}

	public Date getDate() {
		return date;
	}

	public float getPressure() {
		return pressure;
	}

	public float getHumidity() {
		return humidity;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
}

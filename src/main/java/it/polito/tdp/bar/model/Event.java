package it.polito.tdp.bar.model;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Event implements Comparable<Event>{
	
	public enum EventType{
		ARRIVO_GRUPPO, SERVIZIO_TERMINATO
	}
	
	private LocalTime time;
	private EventType type;
	private int numPersone;
	private Duration durata;
	private double tolleranza;
	private int idTavolo;
	
	public Event(LocalTime time, EventType type) {
		super();
		this.time = time;
		this.type = type;
		this.numPersone = (int) (Math.random()*10.0);
		int x = (int) ((1.0+Math.random())*60);
		this.durata = Duration.of(x, ChronoUnit.MINUTES);
		this.tolleranza = Math.random();
	}

	public LocalTime getTime() {
		return time;
	}

	public int getNumPersone() {
		return numPersone;
	}

	public Duration getDurata() {
		return durata;
	}

	public double getTolleranza() {
		return tolleranza;
	}

	public int compareTo(Event other) {
		return this.time.compareTo(other.getTime());
	}


	public EventType getType() {
		
		return this.type;
	}
	
	public int getIdTavolo() {
		return idTavolo;
	}

	public void setIdTavolo(int idTavolo) {
		this.idTavolo = idTavolo;
	}

	@Override
	public String toString() {
		return "Event [time=" + time + ", type=" + type + ", numPersone=" + numPersone + ", durata=" + durata
				+ ", tolleranza=" + tolleranza + ", idTavolo=" + idTavolo + "]";
	}

	
}

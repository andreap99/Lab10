package it.polito.tdp.bar.model;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

import it.polito.tdp.bar.model.Event.EventType;

public class Simulator {
	
	private int ID = 1;
	
	//CODA DEGLI EVENTI
	private PriorityQueue<Event> coda = new PriorityQueue<>();
	
	//PARAMETRI
	private final LocalTime apertura = LocalTime.of(6, 00);
	private final LocalTime chiusura = LocalTime.of(22, 00);
	private int clienti;
	private int soddisfatti;
	private int insoddisfatti;
	private Map<Integer, Tavolo> tavoli = new TreeMap<>();
	
	//MODELLO DEL MONDO
	
	//SETTARE I PARAMETRI
	public void setSimulator() {
		for(int i=0; i<5; i++) {
			Tavolo t = new Tavolo(ID, 4);
			tavoli.put(ID++, t); //aggiungo tavoli di 4 posti
		}
		for(int i=0; i<4; i++) {
			Tavolo t = new Tavolo(ID, 6);
			tavoli.put(ID++, t); //aggiungo tavoli di 6 posti
		}
		for(int i=0; i<4; i++) {
			Tavolo t = new Tavolo(ID, 8);
			tavoli.put(ID++, t); //aggiungo tavoli di 8 posti
		}
		for(int i=0; i<2; i++) {
			Tavolo t = new Tavolo(ID, 10);
			tavoli.put(ID++, t); //aggiungo tavoli di 10 posti
			//System.err.println(t);
		}
		
	}
	
	public Duration getFrequenza() {
		int minuti = (int) (Math.random()*10.0);
		return Duration.of(minuti, ChronoUnit.MINUTES);
	}

	public int getClienti() {
		return clienti;
	}

	public int getSoddisfatti() {
		return soddisfatti;
	}

	public int getInsoddisfatti() {
		return insoddisfatti;
	}
	
	public void run() {
		this.clienti = 0;
		this.soddisfatti = 0;
		this.insoddisfatti = 0;
		
		coda.clear();
		LocalTime oraArrivo = this.apertura;
		do{
			Event z = new Event(oraArrivo, EventType.ARRIVO_GRUPPO);
			this.coda.add(z);
			Duration durata = getFrequenza();
			oraArrivo = oraArrivo.plus(durata);
		}while(oraArrivo.isBefore(chiusura));
		
		while(!this.coda.isEmpty()) {
			Event e = this.coda.poll();
			
			processEvent(e);
			
		}
		
	}

	private void processEvent(Event e) {
		System.out.println(e);
		
		switch(e.getType()) {
		case ARRIVO_GRUPPO:
			
			this.clienti += e.getNumPersone();
			
			int id = getTavoloAdeguato(e);
			//System.err.println(id);
			if(id>=1){
				e.setIdTavolo(id);
				//System.out.println("Al tavolo");
				tavoli.get(id).setOccupato(true);
				this.soddisfatti += e.getNumPersone();
				
				Event nuovo = new Event(e.getTime().plus(e.getDurata()), EventType.SERVIZIO_TERMINATO);
				nuovo.setIdTavolo(id);
				this.coda.add(nuovo);
			}else if(id==0) {
				this.insoddisfatti += e.getNumPersone();
				//System.out.println("Clienti insoddisfatti");
			}else if(id==-1) {
				//System.out.println("Clienti al bancone");
				this.soddisfatti += e.getNumPersone();
			}
			
			break;
		
		case SERVIZIO_TERMINATO:
			
			Tavolo y = this.tavoli.get(e.getIdTavolo());
			y.setOccupato(false);
			//System.err.println("Servizio terminato");
			break;
		}
	}

	private int getTavoloAdeguato(Event e) {
		for(Tavolo t : this.tavoli.values()) {
			if(!t.isOccupato() && t.getPosti()>e.getNumPersone() && t.getPosti()<=(e.getNumPersone()*2)) {
				return t.getId();
			}
		}
		if(e.getTolleranza()>0.9)
			return -1;
		return 0;
	}
	
}

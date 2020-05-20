package it.polito.tdp.bar.model;

import java.util.List;

public class Model {
	
	private Simulator sim;
	private List<Event> result;
	
	public Model() {
		this.sim = new Simulator();
		
	}
	
	public List<Event> getSimulazione(){
		this.sim.setSimulator();
		return sim.run();
	}
	
	public int getSodd() {
		return sim.getSoddisfatti();
	}
	
	public int getIns() {
		return sim.getInsoddisfatti();
	}
	
	public int getClienti() {
		return sim.getClienti();
	}

}

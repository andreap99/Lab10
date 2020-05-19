package it.polito.tdp.bar.model;

public class TestSimulator {

	public static void main(String[] args) {
		
		Simulator sim = new Simulator();
		sim.setSimulator();
		//System.out.println(sim.getora());
		sim.run();
		
		System.out.format("%d clienti\t%d soddisfatti e %d insoddisfatti", sim.getClienti(), sim.getSoddisfatti(), sim.getInsoddisfatti());
	}

}

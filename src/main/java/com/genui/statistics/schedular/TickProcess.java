package com.genui.statistics.schedular;


import com.genui.statistics.dto.Instrument;

public class TickProcess implements Runnable {
	
	private Instrument instrument;
	
	public TickProcess(Instrument instrument) {
		
		this.instrument = instrument;
	}
	
	@Override
	public void run() {
		
		instrument.clean();
	}
}
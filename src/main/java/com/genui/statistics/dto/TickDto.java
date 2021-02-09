package com.genui.statistics.dto;

public class TickDto {

	private String instrument;
	private Double price;
	private Long timestamp;

	public String getInstrument() {
		return instrument;
	}

	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timeStamp) {
		this.timestamp = timeStamp;
	}
	

	public TickDto(String instrument, Double price, Long timeStamp) {
		this.instrument = instrument;
		this.price = price;
		this.timestamp = timeStamp;
	}
	
	public TickDto() {}
}
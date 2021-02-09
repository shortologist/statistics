package com.genui.statistics.dto;

public class QueuedTick {
	
	private Double price;
	private Long timeStamp;
	
	
	public QueuedTick(Double price, Long timeStamp) {
		this.price = price;
		this.timeStamp = timeStamp;
	}


	public Double getPrice() {
		return price;
	}


	public void setPrice(Double price) {
		this.price = price;
	}


	public Long getTimeStamp() {
		return timeStamp;
	}


	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}
}
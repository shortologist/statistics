package com.genui.statistics.dto;

public class Statistics{
	
private Double avg = 0.0;
private Double max = 0.0;
private Double min = 5555555555.0;
private Integer count = 0;

public Double getAvg() {
	return avg;
}

public void setAvg(Double avg) {
	this.avg = avg;
}

public Double getMax() {
	return max;
}

public void setMax(Double max) {
	this.max = max;
}

public Double getMin() {
	return min;
}

public void setMin(Double min) {
	this.min = min;
}

public Integer getCount() {
	return count;
}

public synchronized void setCount(Integer count) {
	this.count = count;
}

public synchronized void incCount() {
	this.count++;
}

public synchronized void decCount() {
	this.count--;
}
}
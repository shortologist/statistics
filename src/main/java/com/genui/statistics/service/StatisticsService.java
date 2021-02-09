package com.genui.statistics.service;

import com.genui.statistics.dto.Statistics;
import com.genui.statistics.dto.TickDto;

public interface StatisticsService {
	
	public boolean addTick(TickDto tick);
	public Statistics getStatisticsForAll();
	public Statistics getStatisticsForInstrument(String identifier);
}
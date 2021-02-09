package com.genui.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.genui.statistics.dto.Statistics;
import com.genui.statistics.dto.TickDto;
import com.genui.statistics.service.StatisticsService;

@RestController
public class TickController {
	
	@Autowired
	private StatisticsService statisticsService;
	
	@PostMapping("/ticks")
	public ResponseEntity<?> addTick(@RequestBody TickDto newTic){
		
		System.out.println(newTic.getPrice() + "--" + newTic.getTimestamp());
		System.out.println("current :" + System.currentTimeMillis());
		if(statisticsService.addTick(newTic))
			return new ResponseEntity<>(HttpStatus.CREATED);
		else
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/ticks/{instrument_identifier}")
	public Statistics getInstrumentStatistics(@PathVariable("instrument_identifier") String identifier) {
		
		Statistics statistics = statisticsService.getStatisticsForInstrument(identifier);
		
		return statistics;
	}
}
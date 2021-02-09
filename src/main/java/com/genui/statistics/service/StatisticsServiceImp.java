package com.genui.statistics.service;



import java.util.Date;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import com.genui.statistics.dao.StatisticsDaoImp;
import com.genui.statistics.dto.Instrument;
import com.genui.statistics.dto.QueuedTick;
import com.genui.statistics.dto.Statistics;
import com.genui.statistics.dto.TickDto;
import com.genui.statistics.schedular.TickProcess;


@Service
public class StatisticsServiceImp implements StatisticsService{

	
	@Autowired
	private ThreadPoolTaskScheduler scheduler;
	
	private Map<String, Instrument> map = StatisticsDaoImp.map;
	
	@Override
	public boolean addTick(TickDto tick) {
		
		tick.setTimestamp(tick.getTimestamp() + 60000);
		
		if(System.currentTimeMillis() >= tick.getTimestamp())
			return false;
		
		Instrument instrument = map.get(tick.getInstrument());
		
		if(instrument==null) {
			instrument = 
					new Instrument(new PriorityQueue<QueuedTick>((a, b)->
					{
						Long result = a.getTimeStamp()-b.getTimeStamp();
						if(result>0)
							return 1;
						else if (result<0)
							return -1;
						return 0;
						}), new CopyOnWriteArrayList<QueuedTick>(), new Statistics(), tick.getInstrument());
			
			map.put(tick.getInstrument(), instrument);
			System.out.println(tick.getTimestamp() + "-" + System.currentTimeMillis());
		}
		
		instrument.addNewTick(tick);
		scheduler.schedule(new TickProcess(instrument), new Date(tick.getTimestamp()));
		return true;
	}
	
	@Override
	public Statistics getStatisticsForAll() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Statistics getStatisticsForInstrument(String identifier) {
		
		Instrument instrument = map.get(identifier);
		
		Statistics statistics;
		
		if(instrument==null) {
			statistics = new Statistics();
			statistics.setMin(0.0);
		}
		else
			statistics = instrument.getStatistics();
		
		return statistics;
	}
	
}
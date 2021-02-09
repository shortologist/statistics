package com.genui.statistics.schedular;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import com.genui.statistics.dao.StatisticsDaoImp;
import com.genui.statistics.dto.Instrument;
import com.genui.statistics.dto.QueuedTick;
import com.genui.statistics.dto.Statistics;

public class TickProcess implements Runnable {
	

	private String identifier;
	private Queue<QueuedTick> timeQueue;
	private List<QueuedTick> priceList;
	private Statistics statistics;
	SimpleDateFormat formatter = new SimpleDateFormat("mm:ss"); 
	
	public TickProcess(String identifier, Instrument instrument) {
		
		this.identifier = identifier;
		this.timeQueue = instrument.getTimeQueue();
		this.priceList = instrument.getPriceList();
		this.statistics = instrument.getStatistics();
	}
	
	@Override
	public void run() {
		
		QueuedTick expiredTick = timeQueue.poll(); 
		if(statistics.getCount() == 1)
			StatisticsDaoImp.map.remove(identifier);
		else {
			if(expiredTick.getPrice() == statistics.getMax())
				setNewMax();
			else if (expiredTick.getPrice() == statistics.getMin())
				setnewMin();		
			statistics.setAvg(getNewAvg(expiredTick));
			statistics.decCount();
		}

		System.out.println("Time: " + formatter.format(new Date(expiredTick.getTimeStamp())) + " Price:" + expiredTick.getPrice());
	}

	private double getNewAvg(QueuedTick expiredTick) {
		return (statistics.getAvg()*statistics.getCount()-expiredTick.getPrice())/(statistics.getCount()-1);
	}

	private void setnewMin() {
		QueuedTick minTick;
		do {
			if(priceList.size()==0)
				return;
			 minTick = priceList.remove(0);
			 System.out.println("Min Time: " + formatter.format(new Date(minTick.getTimeStamp())) + " Price:" + minTick.getPrice() + " Current time: " + formatter.format(new Date(System.currentTimeMillis())));
		} while (minTick.getTimeStamp() <= System.currentTimeMillis());
		statistics.setMin(minTick.getPrice());
	}

	private void setNewMax() {
		QueuedTick maxTick;
		do {
			if(priceList.size()==0)
				return;
			 maxTick = priceList.remove(priceList.size()-1);
			 System.out.println("Max Time: " + formatter.format(new Date(maxTick.getTimeStamp())) + " Price:" + maxTick.getPrice() + " Current time: " + formatter.format(new Date(System.currentTimeMillis())));
		} while(maxTick.getTimeStamp() <= System.currentTimeMillis());
		statistics.setMax(maxTick.getPrice());
	}
}
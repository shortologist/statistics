package com.genui.statistics.dto;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;

import com.genui.statistics.dao.StatisticsDaoImp;

public class Instrument {
	
	private PriorityQueue<QueuedTick> timeQueue;
	private List<QueuedTick> priceList;
	private Statistics statistics;
	private String identifier;
	SimpleDateFormat formatter = new SimpleDateFormat("mm:ss"); 
	
	public Instrument(PriorityQueue<QueuedTick> timeQueue, List<QueuedTick> priceList, Statistics statistics, String identifier) {
		this.timeQueue = timeQueue;
		this.priceList = priceList;
		this.statistics = statistics;
		this.identifier = identifier;
	}


	public PriorityQueue<QueuedTick> getTimeQueue() {
		return timeQueue;
	}


	public void setTimeQueue(PriorityQueue<QueuedTick> timeQueue) {
		this.timeQueue = timeQueue;
	}


	public List<QueuedTick> getPriceList() {
		return priceList;
	}


	public void setPriceList(List<QueuedTick> priceList) {
		this.priceList = priceList;
	}


	public Statistics getStatistics() {
		return statistics;
	}

	public void setStatistics(Statistics statistics) {
		this.statistics = statistics;
	}
	
	public synchronized void clean() {
		
		QueuedTick expiredTick = timeQueue.poll(); 
		if(statistics.getCount() == 1)
			StatisticsDaoImp.map.remove(identifier);
		else {
			if(expiredTick.getPrice() == statistics.getMax())
				setNewMax();
			else if (expiredTick.getPrice() == statistics.getMin())
				setnewMin();		
			statistics.setAvg(cleanAvg(expiredTick));
			statistics.decCount();
		}

		System.out.println("Time: " + formatter.format(new Date(expiredTick.getTimeStamp())) + " Price:" + expiredTick.getPrice());
	}
	
	public synchronized void addNewTick(TickDto newTick) {
		
		QueuedTick queuedTick = new QueuedTick(newTick.getPrice(), newTick.getTimestamp());
		timeQueue.add(queuedTick);
		priceList.add(queuedTick);
		priceList.sort((a, b)->{
						double result = a.getPrice()-b.getPrice();
						if(result>0)
							return 1;
						else if (result<0)
							return -1;
						return 0;
						});
			statistics.setAvg(newAvg(newTick));
			statistics.incCount();
			if(statistics.getMax()<newTick.getPrice())
				statistics.setMax(newTick.getPrice());
			if(statistics.getMin()>newTick.getPrice())
				statistics.setMin(newTick.getPrice());
	}


	private double newAvg(TickDto newTick) {
		return (statistics.getAvg()*statistics.getCount()+newTick.getPrice())/(statistics.getCount()+1);
	}
	
	private double cleanAvg(QueuedTick expiredTick) {
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
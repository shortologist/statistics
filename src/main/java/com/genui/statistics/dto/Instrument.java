package com.genui.statistics.dto;



import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class Instrument {
	
	private PriorityQueue<QueuedTick> timeQueue;
	private CopyOnWriteArrayList<QueuedTick> priceList;
	private Statistics statistics;
	
	
	public Instrument(PriorityQueue<QueuedTick> timeQueue, CopyOnWriteArrayList<QueuedTick> priceList, Statistics statistics) {
		this.timeQueue = timeQueue;
		this.priceList = priceList;
		this.statistics = statistics;
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


	public synchronized void setPriceList(CopyOnWriteArrayList<QueuedTick> priceList) {
		this.priceList = priceList;
	}


	public Statistics getStatistics() {
		return statistics;
	}

	public synchronized void setStatistics(Statistics statistics) {
		this.statistics = statistics;
	}
	
	public synchronized void addNewTick(TickDto newTick) {
		
		QueuedTick queuedTick = new QueuedTick(newTick.getPrice(), newTick.getTimestamp());
		synchronized (queuedTick) {
			timeQueue.add(queuedTick);
		}
		priceList.add(queuedTick);
		priceList.sort((a, b)->{
						double result = a.getPrice()-b.getPrice();
						if(result>0)
							return 1;
						else if (result<0)
							return -1;
						return 0;
						});
			statistics.setAvg((statistics.getAvg()*statistics.getCount()+newTick.getPrice())/(statistics.getCount()+1));
			statistics.incCount();
			if(statistics.getMax()<newTick.getPrice())
				statistics.setMax(newTick.getPrice());
			if(statistics.getMin()>newTick.getPrice())
				statistics.setMin(newTick.getPrice());
	}
}
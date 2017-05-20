package com.usermanagement.requestmonitor;

import java.util.HashMap;
import java.util.Map;

public class RequestMonitor extends Thread {
	
	private static RequestMonitor requestMonitor = null;
	private int maxRequestCount;
	private int maxGlobalRequestCount;
	private int currentRequestCount;
	
	private int resetPeriod;
	private boolean signalStop;
	private boolean stopped;
	
	private  Map<String,Integer> requestMap;
	
	public int getMaxGlobalRequestCount() {
		return maxGlobalRequestCount;
	}

	public void setMaxGlobalRequestCount(int maxGlobalRequestCount) {
		this.maxGlobalRequestCount = maxGlobalRequestCount;
	}


	public void stopMonitor() throws Exception {
		if (!isStopped()){
			signalStop();
			
			while (!isStopped()) {
				System.out.println("STOP PENDING");
			}		
			this.join();
			System.out.println("JOIN SUCCESS");		
			
			requestMonitor = null;
		}
	}
	
	
	private RequestMonitor() { 
		signalStop = false;
		stopped = true;
		currentRequestCount = 0;
		requestMap = new HashMap<String, Integer>();
	 }
	
	public boolean isStopped() {
		return stopped;
	}

	public boolean isSignalStop() {
		return signalStop;
	}

	 @Override
	public void start() {
		 signalStop = false;
		 stopped = false;
		 reset();
		 super.start();
	}
	 
	public void signalStop() {
		this.signalStop = true;
	}
	
	 
	 public int getResetPeriod() {
		return resetPeriod;
	}

	public void setResetPeriod(int resetPeriod) {
		this.resetPeriod = resetPeriod;
	}

	
	 
	 public void reset() {
		 requestMap = new HashMap<String, Integer>();
		 currentRequestCount = 0;
	 }
	 
	 public static RequestMonitor getRequestMonitorInstance() {
		 if(requestMonitor == null){
			 requestMonitor = new RequestMonitor();
			 return requestMonitor;
		 }
		 return requestMonitor;
	 }
	 
	 
	public void setMaxRequestCount(Integer value){
		 this.maxRequestCount = value;
	 }
	 public int getMaxRequestCount(){
		 return this.maxRequestCount;
	 }
	
	 synchronized public boolean allowRequest(String address) {
		 
		 if (currentRequestCount >= maxGlobalRequestCount) {
			 return false;
		 }

		 currentRequestCount++;
		 
		 if (currentRequestCount % 100 == 0) {
			 System.out.println("currentRequestCount = " + currentRequestCount);
		 }
		 
		if(!requestMap.containsKey(address))
			requestMap.put(address, 1);

		int frequence = requestMap.get(address);
		if(frequence > maxRequestCount){
			return false;
		}
		requestMap.replace(address, frequence+1);
		return true;
	}
	@Override
	 public void run() {
		while(!signalStop) { 
		try{
			Thread.sleep(resetPeriod);
		}
		catch (Exception e){
			stopped = true;
			requestMonitor = null;
			return;
		}					
			requestMap = new HashMap<String, Integer>();
			currentRequestCount = 0;
		}
		stopped = true;
		System.out.println("THREAD STOPPED");
	}
}

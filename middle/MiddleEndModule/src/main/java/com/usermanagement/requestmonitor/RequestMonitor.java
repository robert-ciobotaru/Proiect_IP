package com.usermanagement.requestmonitor;

import java.util.HashMap;
import java.util.Map;

public class RequestMonitor extends Thread {
	
	 private static RequestMonitor requestMonitor = null;
	 private  int maxRequestCount;
	 private  Map<String,Integer> requestMap = new HashMap<String, Integer>();
	 
	 private RequestMonitor() { 
		 maxRequestCount = 100;
	 }
	 
	 public void reset() {
		 requestMap = new HashMap<String, Integer>();
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
		while(true){ 
			try {
				Thread.sleep(1000*60);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			requestMap = new HashMap<String, Integer>();
			
			
		}
	}
	@SuppressWarnings("deprecation")
	@Override
	public void finalize(){
	   this.stop();
	}
	

}

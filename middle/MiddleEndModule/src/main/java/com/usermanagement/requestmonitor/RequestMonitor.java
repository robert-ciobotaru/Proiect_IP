package com.usermanagement.requestmonitor;

import java.util.HashMap;
import java.util.Map;

public class RequestMonitor extends Thread {
	 private RequestMonitor(){
		 
	 }
	 private static RequestMonitor requestMonitor=null;
	 
	 public static RequestMonitor getRequestMonitorInstance(int value){
		 if(requestMonitor==null){
			 requestMonitor = new RequestMonitor(value);
			 return requestMonitor;
		 }
		 return requestMonitor;
	 }
	 
	 private  Integer maxRequestCount; 
	 
	 private RequestMonitor(int value){
		this.maxRequestCount = value;	
		this.start();
			
     }
	 
	public void setMaxRequestCount(Integer value){
		 this.maxRequestCount = value;
	 }
	 public Integer getMaxRequestCount(){
		 return this.maxRequestCount;
	 }
	 

	
	private  Map<String,Integer> requestMap = new HashMap<String, Integer>(); 
	
	public  boolean allowRequest(String address){
		if(!requestMap.containsKey(address))
			requestMap.put(address, 1);
		else{
			int frequence = requestMap.get(address);
			if(frequence >=maxRequestCount){
				return false;
			}
			else
			{
				requestMap.replace(address, frequence+1);
			}
		}
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

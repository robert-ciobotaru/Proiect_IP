package com.usermanagement.requestmonitor;

import java.util.HashMap;
import java.util.Map;

public class RequestMonitor extends Thread {
	 
	 private  Integer maxRequestCount; 
	
	 public RequestMonitor(int value){
		this.maxRequestCount = value;	
		this.start();
			
     }
	 
	public void setMaxRequestCount(Integer value){
		 this.maxRequestCount = value;
	 }
	 public Integer getMaxRequestCount(){
		 return this.maxRequestCount;
	 }
	 

	
	private  Map<String,Integer> requestMap = new HashMap(); 
	
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
			requestMap = new HashMap();
			
			
		}
		
		
		
	}
	@Override
	public void finalize(){
	   this.stop();
	}
	

}

package com.usermangement.tests;

import static org.junit.Assert.*;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.usermanagement.requestmonitor.RequestMonitor;

public class TestRequestMonitor {
	static RequestMonitor requestMonitor  = RequestMonitor.getRequestMonitorInstance();
	static int initialResetPeriod;
	Lock sequential = new ReentrantLock();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		initialResetPeriod = requestMonitor.getResetPeriod();
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("setUp() start");
		requestMonitor = RequestMonitor.getRequestMonitorInstance();
		requestMonitor.setResetPeriod(1000);
		requestMonitor.setMaxRequestCount(100);
		requestMonitor.setMaxGlobalRequestCount(1000);
		requestMonitor.reset();
		requestMonitor.start();
		System.out.println("setUp() end");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("tearDown() start (wait for " + requestMonitor.getResetPeriod());
		Thread.sleep(2500);
		requestMonitor.stopMonitor();
		System.out.println("tearDown() end");
	}

//	@Test
//	public void testStart() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testRun() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testIsStopped() {
//		fail("Not yet implemented");
//	}
//
	@Test
	public void testIsSignalStop() {
		assertFalse(requestMonitor.isSignalStop());
	}
//
//	@Test
//	public void testSetSignalStop() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetResetPeriod() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetResetPeriod() {
//		fail("Not yet implemented");
//	}
//
	@Test
	public void testReset() {
		String adress = "123"; 
		for (int i = 0; i < requestMonitor.getMaxRequestCount(); i++){
			assertTrue(true == requestMonitor.allowRequest(adress));
		}
		requestMonitor.reset();
		assertTrue(requestMonitor.allowRequest(adress));
	}

	@Test
	public void testGetRequestMonitorInstance() {
		RequestMonitor monitor = RequestMonitor.getRequestMonitorInstance();
		assertNotNull(monitor);
	}

	@Test
	public void testSetMaxRequestCount() {
		requestMonitor.setMaxRequestCount(20);
		assertEquals(20, requestMonitor.getMaxRequestCount());
	}

	@Test
	public void testGetMaxRequestCount() {
		requestMonitor.setMaxRequestCount(20);
		assertEquals(20, requestMonitor.getMaxRequestCount());
	}
	
	@Test
	public void testGetMaxGlobalRequestCount() {
		requestMonitor.setMaxGlobalRequestCount(20);
		assertEquals(20, requestMonitor.getMaxGlobalRequestCount());
	}

	@Test
	public void testAllowRequest() {
		String address = "123"; 
		for (int i = 0; i < requestMonitor.getMaxRequestCount(); i++){
			assertTrue(true == requestMonitor.allowRequest(address));
		}
		assertFalse(requestMonitor.allowRequest(address));
		
	}
	
	@Test
	public void testAllowRequestGlobalScenario() {
		String address1 = "123"; 
		String address2 = "124";
		String address3 = "125"; 
		
		requestMonitor.setMaxGlobalRequestCount(2 * requestMonitor.getMaxRequestCount());
		
		for (int i = 0; i < requestMonitor.getMaxRequestCount(); i++){
			assertTrue(true == requestMonitor.allowRequest(address1));
		}
		
		for (int i = 0; i < requestMonitor.getMaxRequestCount(); i++){
			assertTrue(true == requestMonitor.allowRequest(address2));
		}
		assertFalse(requestMonitor.allowRequest(address3));	
	}
	
	@Test
	public void testInterruptedException() throws InterruptedException {
	
		requestMonitor.interrupt();
		while (!requestMonitor.isStopped()) {
			
		}
		Thread.sleep(1000);
		assertFalse(requestMonitor.equals(RequestMonitor.getRequestMonitorInstance()));
	}

}

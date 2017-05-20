package com.usermangement.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.usermanagement.UsersManagementApplication;

public class TestUsersManagementApplication extends UsersManagementApplication{

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMain() {
		
		String[] params = new String[3];
		params[0] = "1";
		params[1] = "2";
		params[2] = "3";
		UsersManagementApplication.main(params);
		assertTrue(true);
	}

}

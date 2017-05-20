package com.usermangement.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.usermanagement.Sanitizer;

public class TestSanitizer extends Sanitizer{

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
	public void testEscapeSql() {
		String  escaped = Sanitizer.escapeSql("ceva'");
		assertTrue("ceva''".equals(escaped));
	}
	
	@Test
	public void noEscapeTest() {
		String  escaped = Sanitizer.escapeSql("ceva");
		assertTrue("ceva".equals(escaped));
	}
	
	@Test
	public void nullStringTest() {
		String  escaped = TestSanitizer.escapeSql(null);
		assertNull(escaped);
	}
	

}

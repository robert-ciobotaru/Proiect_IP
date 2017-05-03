package com.example;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.usermanagement.DTO.NotificationRequestDto;
import com.usermanagement.controllers.UsersRestController;
@RunWith(SpringJUnit4ClassRunner.class)

public class GetNotificationsSpecificatedTest {

	private MockMvc mockMvc;
    private UsersRestController controllers;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		this.controllers=new UsersRestController();
		this.mockMvc = MockMvcBuilders.standaloneSetup(controllers).build();
	}

	@After
	public void tearDown() throws Exception {
		this.controllers = null;
	}

	@Test
	public void test1() throws Exception {
		NotificationRequestDto response = null;
	this.mockMvc.perform(get("/v1/users/2/notifications/23").contentType(MediaType.APPLICATION_JSON_UTF8).content("{"
				+ "\"method\" : \"getNotification\","
				+ "\"userId\" : 2,"
				+ "\"notificationId\" : 23"
				+ "}"))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.id", is(23)))
	            .andExpect(jsonPath("$.text", is("Trezirea de dimineata")))
	            .andExpect(jsonPath("$.period", is("none")))
	            .andExpect(jsonPath("$.day_timestamp", is(2315)))
	            ;
	
		
	
		
	}
}

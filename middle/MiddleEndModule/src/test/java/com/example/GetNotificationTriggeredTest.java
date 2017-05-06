package com.example;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.usermanagement.DTO.NotificationRequestDto;
import com.usermanagement.controllers.UsersRestController;

public class GetNotificationTriggeredTest {

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
	this.mockMvc.perform(get("/v1/users/2/notifications/triggered-notification").contentType(MediaType.APPLICATION_JSON_UTF8).content("{"
				+ "\"userId\" : 2,"
				+ "\"method\" : \"getExpiredNotifications\","
				+ "}"))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.triggered-notification.type", is("User_Notification")))
	            .andExpect(jsonPath("$.triggered-notification.data.id", is(23)))
	            .andExpect(jsonPath("$.triggered-notification.data.text", is("Wake me up")))
	            .andExpect(jsonPath("$.triggered-notification.data.time", is(1231245)))
	            .andExpect(jsonPath("$.triggered-notification.data.repeatable", is("true")))
	            .andExpect(jsonPath("$.triggered-notification.data.interval", is(300)))
	            ;
	}
}

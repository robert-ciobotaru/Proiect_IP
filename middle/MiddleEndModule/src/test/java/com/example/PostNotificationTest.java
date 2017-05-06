package com.example;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.usermanagement.DTO.NotificationDto;
import com.usermanagement.controllers.UsersRestController;

public class PostNotificationTest {

	 private MockMvc mockMvc;
     private UsersRestController controllers= new UsersRestController();
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	this.mockMvc = MockMvcBuilders.standaloneSetup(controllers).build();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test1() throws Exception {
		NotificationDto response = null;
		try {
			this.mockMvc.perform(post("v1/users/2/notifications").contentType(MediaType.APPLICATION_JSON_UTF8).content("{"
						+ "\"text\":\"Wake me up\","
						+ "\"time\":\"1231245\","
						+ "\"repeatable\":\"true\","
						+ "\"interval\":\"300\","
						+ "}"))
			            .andExpect(status().isOk())
			            .andExpect(jsonPath("$.notifications.id", is(23)))
			            .andExpect(jsonPath("$.notifications.text", is("Wake me up")))
			            .andExpect(jsonPath("$.notifications.time", is(1231245)))
			            .andExpect(jsonPath("$.notifications.repetable", is(true)))
			            .andExpect(jsonPath("$.notifications.interval", is(300)))
			            ;
			        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}

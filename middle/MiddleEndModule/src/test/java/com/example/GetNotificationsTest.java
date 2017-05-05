package com.example;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.springframework.test.web.ModelAndViewAssert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.usermanagement.DTO.GetNotificationsResultDto;
import com.usermanagement.DTO.GetNotificationsResultFromBackEnd;
import com.usermanagement.DTO.NotificationDto;
import com.usermanagement.controllers.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({/* include live config here
    e.g. "file:web/WEB-INF/application-context.xml",
    "file:web/WEB-INF/dispatcher-servlet.xml" */})
public class GetNotificationsTest {
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
		GetNotificationsResultFromBackEnd response = null;
	this.mockMvc.perform(get("/v1/users/2/notifications").contentType(MediaType.APPLICATION_JSON_UTF8).content("{"
				+ "\"method\":\"getNotifications\","
				+ "\"userId\":2"
				+ "}"))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.notifications[0].id", is(23)))
	            .andExpect(jsonPath("$.notifications[0].text", is("Wake me up")))
	            .andExpect(jsonPath("$.notifications[0].time", is(1231245)))
	            .andExpect(jsonPath("$.notifications[0].repeatable", is(true)))
	            .andExpect(jsonPath("$.notifications[0].interval", is(300)))
	            .andExpect(jsonPath("$.notifications[1].id", is(24)))
	            .andExpect(jsonPath("$.notifications[1].text", is("Get the kid")))
	            .andExpect(jsonPath("$.notifications[1].time", is(123245)))
	            .andExpect(jsonPath("$.notifications[1].repeatable", is(false)))
	            .andExpect(jsonPath("$.notifications[1].interval", is(400)));
	
	}
	@Test
	public void test2() throws Exception {
		GetNotificationsResultFromBackEnd response = null;
	this.mockMvc.perform(get("/v1/users/1/notifications").contentType(MediaType.APPLICATION_JSON_UTF8).content("{"
				+ "\"method\":\"getNotifications\","
				+ "\"userId\":2"
				+ "}"))
	            .andExpect(status().isUnprocessableEntity())
				.andExpect(jsonPath("$.error",is(not(""))));
	
	
	            
	
	}
}

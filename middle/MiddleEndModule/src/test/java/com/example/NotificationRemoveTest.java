package com.example;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.usermanagement.DTO.NotificationDto;
import com.usermanagement.controllers.UsersRestController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({/* include live config here
    e.g. "file:web/WEB-INF/application-context.xml",
    "file:web/WEB-INF/dispatcher-servlet.xml" */})
public class NotificationRemoveTest {
	static MockMvc mockMvc;
	static  UsersRestController controllers;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {

	     controllers= new UsersRestController();
	     mockMvc = MockMvcBuilders.standaloneSetup(controllers).build();
	}

	@After
	public void tearDown() throws Exception {
	mockMvc = null;
	controllers= null;
	}

	@Test
	public void test1() throws Exception {
		mockMvc.perform(delete("/v1/users/1/notifications/2"))
			            .andExpect(status().isUnprocessableEntity())
			            .andExpect(jsonPath("$.error", is("EroorHappens")))
			           ;
	
		
			
	}
	@Test
	public void test2() throws Exception {
		mockMvc.perform(delete("/v1/users/3/notifications/3"))
			            .andExpect(status().isOk())
			            .andExpect(jsonPath("$.id", is(23)))
			           ;
	
		
			
	}

}

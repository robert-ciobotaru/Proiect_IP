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

public class CreateUserTest {
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
		controllers= new UsersRestController();
		this.mockMvc = MockMvcBuilders.standaloneSetup(controllers).build();
	}

	@After
	public void tearDown() throws Exception {
		mockMvc = null;
		controllers = null;
	}

	@Test
	public void test1() {
		try {
			this.mockMvc.perform(post("/v1/users").contentType(MediaType.APPLICATION_JSON_UTF8).content("{"
						+ "\"country\":\"Romania\","
						+ "\"city\":\"Iasi\","
						+ "\"newsCrawler\":\"false\","
						+ "\"hazzardCrawler\":\"false\","
						+ "\"weatherCrawler\":\"true\","
						+ "\"email\":\"valentin.damoc@gmail.com\""
						+ "}"))
			            .andExpect(status().isCreated())
			            .andExpect(jsonPath("$.user.id", is(23)))
			            .andExpect(jsonPath("$.user.country", is("Romania")))
			            .andExpect(jsonPath("$.user.city", is("Iasi")))
			            .andExpect(jsonPath("$.user.hazzardCrawler", is(false)))
			            .andExpect(jsonPath("$.user.weatherCrawler", is(true)))
			            .andExpect(jsonPath("$.user.email", is("valentin.damoc@gmail.com")))
			            .andExpect(jsonPath("$.user.newsCrawler", is(false)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void test2() {
		try {
			this.mockMvc.perform(post("/v1/users").contentType(MediaType.APPLICATION_JSON_UTF8).content("{"
						+ "\"country\":\"Romania\","
						+ "\"city\":\"Iasi\","
						+ "\"newsCrawler\":\"false\","
						+ "\"hazzardCrawler\":\"false\","
						+ "\"weatherCrawler\":\"true\","
						+ "\"email\":\"manole.catalin@gmail.com\""
						+ "}"))
			            .andExpect(status().isBadRequest());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

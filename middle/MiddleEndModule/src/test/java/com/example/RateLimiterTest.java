package com.example;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.net.ssl.SSLEngineResult.Status;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.usermanagement.DTO.NotificationDto;
import com.usermanagement.controllers.UsersRestController;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;


public class RateLimiterTest {
	@Rule
	public WireMockRule wireMockRule = new WireMockRule(9001); 
	
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
	public void createdTest() {
		
        wireMockRule.stubFor(any(urlPathEqualTo("/"))
                .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
				.withBody(
					 "{"
						   + "\"userId\" : 24,"
						   + "\"error\" : \"\""
					+ "}")
				
				));
		
		try {
			controllers.getRequestMonitor().setMaxRequestCount(100);
			for(int i =1; i<=controllers.getRequestMonitor().getMaxRequestCount();i++)
			this.mockMvc.perform(post("/v1/users").contentType(MediaType.APPLICATION_JSON_UTF8).content("{"
						+ "\"country\":\"Romania\","
						+ "\"city\":\"Iasi\","
						+ "\"newsCrawler\":\"false\","
						+ "\"hazzardCrawler\":\"false\","
						+ "\"weatherCrawler\":\"true\","
						+ "\"email\":\"valentin.damoc@gmail.com\""
						+ "}"))
			           .andExpect(status().isCreated());
			this.mockMvc.perform(post("/v1/users").contentType(MediaType.APPLICATION_JSON_UTF8).content("{"
					+ "\"country\":\"Romania\","
					+ "\"city\":\"Iasi\","
					+ "\"newsCrawler\":\"false\","
					+ "\"hazzardCrawler\":\"false\","
					+ "\"weatherCrawler\":\"true\","
					+ "\"email\":\"valentin.damoc@gmail.com\""
					+ "}"))
		           .andExpect(status().isTooManyRequests());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}

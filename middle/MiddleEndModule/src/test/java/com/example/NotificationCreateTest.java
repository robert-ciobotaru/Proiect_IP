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


public class NotificationCreateTest
{
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
						   + "\"notificationId\" : 23,"
						   + "\"error\" : \"\""
					+ "}")
				
				));
		
		try {
			this.mockMvc.perform(post("/v1/users/2/reminders").contentType(MediaType.APPLICATION_JSON_UTF8).content("{"
						+ "\"text\":\"Wake me up\","
						+ "\"time\":1231245,"
						+ "\"repeatable\":\"true\","
						+ "\"interval\":300"
						+ "}"))
			           .andExpect(status().isCreated())
			            ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void errorSet() {
		wireMockRule.stubFor(any(urlPathEqualTo("/"))
                .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
				.withBody(
					 "{"
						   + "\"notificationId\": 23,"
						   + "\"error\" : \"Eroare a avut loc Romania\""
					
					+ "}")
				));
		
		try {
			this.mockMvc.perform(post("/v1/users/2/reminders").contentType(MediaType.APPLICATION_JSON_UTF8).content("{"
					+ "\"text\":\"Wake me up\","
					+ "\"time\": 123456,"
					+ "\"repeatable\":\"true\","
					+ "\"interval\":300"
					+ "}"))
			        .andExpect(status().isUnprocessableEntity())
			            ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void missingField() {
		wireMockRule.stubFor(any(urlPathEqualTo("/"))
                .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
				.withBody(
					 "{"
						   + "\"notificationId\" :2,"
						   + "\"error\" : \"Eroare\""
					
					+ "}")
				));
		
		try {
			this.mockMvc.perform(post("/v1/users/2/reminders").contentType(MediaType.APPLICATION_JSON_UTF8).content("{"
					+ "\"text\":\"Wake me up\","
					+ "\"repeatable\":\"true\","
					+ "\"interval\":300"
					+ "}"))
			            .andExpect(status().isBadRequest())
			            
			            ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void internalServerError() {
		wireMockRule.stubFor(any(urlPathEqualTo("/"))
                .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(
      					 "{"
      							 + "\"idf\" :12,"
     						   + "\"errobr\" : \"Eroare\""
      							
      					+ "}")
				));
		
		try {
			this.mockMvc.perform(post("/v1/users/2/reminders").contentType(MediaType.APPLICATION_JSON_UTF8).content("{"
					+ "\"text\":\"Wake me up\","
					+ "\"time\":1231245,"
					+ "\"repeatable\":\"true\","
					+ "\"interval\":300"
					+ "}"))
			            .andExpect(status().isInternalServerError())
			            
			            ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void serviceUnavaible() {
	
		
		try {
			this.mockMvc.perform(post("/v1/users/2/reminders").contentType(MediaType.APPLICATION_JSON_UTF8).content("{"
					+ "\"text\":\"Wake me up\","
					+ "\"time\":1231245,"
					+ "\"repeatable\":\"true\","
					+ "\"interval\":300"
					+ "}"))
			            .andExpect(status().isServiceUnavailable())
			            
			            ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}

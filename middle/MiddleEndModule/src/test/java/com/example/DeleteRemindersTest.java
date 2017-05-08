package com.example;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.any;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
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
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.usermanagement.DTO.NotificationDto;
import com.usermanagement.controllers.UsersRestController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({/* include live config here
    e.g. "file:web/WEB-INF/application-context.xml",
    "file:web/WEB-INF/dispatcher-servlet.xml" */})
public class DeleteRemindersTest {
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
	public void serviceUnavailable() {
		
		try {
			this.mockMvc.perform(delete("/v1/users/2/reminders/23"))
			            .andExpect(status().isServiceUnavailable());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void statusOk() {
		
		wireMockRule.stubFor(any(urlPathEqualTo("/"))
				.willReturn(aResponse()
				.withHeader("Content-Type", "application/json")
				.withBody("{"
						+ "\"error\" : \"\""
						+ "}"
						)
				));
		try{
			this.mockMvc.perform(delete("/v1/users/2/reminders/23"))
			            .andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void unprocesableEntity() {
		
		wireMockRule.stubFor(any(urlPathEqualTo("/"))
				.willReturn(aResponse()
				.withHeader("Content-Type", "application/json")
				.withBody("{"
						+ "\"error\" : \"ceva in error\""
						+ "}"
						)
				));
		try{
			this.mockMvc.perform(delete("/v1/users/2/reminders/23"))
			            .andExpect(status().isUnprocessableEntity());
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
				.withBody("{"
						+ "\"eror\" : \"\""
						+ "}"
						)
				));
		try{
			this.mockMvc.perform(delete("/v1/users/2/reminders/23"))
			            .andExpect(status().isInternalServerError())
			            
			            ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
	}
}

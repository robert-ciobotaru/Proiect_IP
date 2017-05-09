package com.example;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import javax.net.ssl.SSLEngineResult.Status;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.usermanagement.DTO.NotificationDto;
import com.usermanagement.controllers.ReminderController;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;


public class GetReminderTest {
	@Rule
	public WireMockRule wireMockRule = new WireMockRule(9001); 
	
    private MockMvc mockMvc;
    private ReminderController controllers;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		controllers= new ReminderController();
		this.mockMvc = MockMvcBuilders.standaloneSetup(controllers).build();
	}

	@After
	public void tearDown() throws Exception {
		mockMvc = null;
		controllers = null;
	}

	@Test
	public void testStatusOk() {
		
        wireMockRule.stubFor(any(urlPathEqualTo("/"))
                .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
				.withBody(
						"{"
							      + "\"notificationsList\": [{"
							        + "\"id\": 23,"
							        + "\"text\": \"Get the kid\","
							        + "\"time\": 213141,"
							        + "\"repeatable\": true,"
							        + "\"interval\": 234"
							       + "},"
							       + "{"
							        + "\"id\": 24,"
							        + "\"text\": \"Burn the house\","
							        + "\"time\": 54234,"
							        + "\"repeatable\": false,"
							        + "\"interval\": 234"
							       + "}"
							         + "],"
							       +"\"error\" : \"\""
					+ "}")
				//.withStatus(201)
				));
		
		try {
			this.mockMvc.perform(get("/v1/users/20/reminders"))
			            .andExpect(status().is(200))
			            .andExpect(jsonPath("$.remindersList[0].id", is(23)))
			            .andExpect(jsonPath("$.remindersList[0].text", is("Get the kid")))
			            .andExpect(jsonPath("$.remindersList[0].time", is(213141)))
			            .andExpect(jsonPath("$.remindersList[0].repeatable", is(true)))
			            .andExpect(jsonPath("$.remindersList[0].interval", is(234)))
			            .andExpect(jsonPath("$.remindersList[1].id", is(24)))
			            .andExpect(jsonPath("$.remindersList[1].text", is("Burn the house")))
			            .andExpect(jsonPath("$.remindersList[1]time", is(54234)))
			            .andExpect(jsonPath("$.remindersList[1].repeatable", is(false)))
			            .andExpect(jsonPath("$.remindersList[1].interval", is(234)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testUnprocessableEntity() {
		
        wireMockRule.stubFor(any(urlPathEqualTo("/"))
                .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
				.withBody(
						"{"
							      + "\"notificationsList\": [{"
							        + "\"id\": 23,"
							        + "\"text\": \"Get the kid\","
							        + "\"time\": 213141,"
							        + "\"repeatable\": true,"
							        + "\"interval\": 234"
							       + "},"
							       + "{"
							        + "\"id\": 24,"
							        + "\"text\": \"Burn the house\","
							        + "\"time\": 54234,"
							        + "\"repeatable\": false,"
							        + "\"interval\": 234"
							       + "}"
							         + "],"
							       +"\"error\" : \"Whatever error string\""
					+ "}")
				//.withStatus(201)
				));
		
		try {
			this.mockMvc.perform(get("/v1/users/20/reminders"))
			            .andExpect(status().is(422));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testInternalServerError() {
		
        wireMockRule.stubFor(any(urlPathEqualTo("/"))
                .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
				.withBody(
						"{"
							      + "\"notificationsList\": [{"
							        + "\"id\": 23,"
							        + "\"ttext\": \"Get the kid\","
							        + "\"time\": 213141,"
							        + "\"repeatable\": true,"
							        + "\"interval\": 234"
							       + "},"
							       + "{"
							        + "\"id\": 24,"
							        + "\"text\": \"Burn the house\","
							        + "\"time\": 54234,"
							        + "\"repeatable\": false,"
							        + "\"interval\": 234"
							       + "}"
							         + "],"
							       +"\"error\" : \"\""
					+ "}")
				//.withStatus(201)
				));
		
		try {
			this.mockMvc.perform(get("/v1/users/20/reminders"))
			            .andExpect(status().is(500));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testServiceUnavailable() {
		
		try {
			this.mockMvc.perform(get("/v1/users/20/reminders"))
			            .andExpect(status().is(503));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}

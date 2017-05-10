package com.example;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

import static com.github.tomakehurst.wiremock.client.WireMock.any;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.usermanagement.controllers.ReminderController;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestReminderController {
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
	public void zrate_limiter_test() {
		
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
			controllers.getRequestMonitor().setMaxRequestCount(100);;
			for(int i =1;i<=controllers.getRequestMonitor().getMaxRequestCount()-14;i++)
			this.mockMvc.perform(post("/v1/users/2/reminders").contentType(MediaType.APPLICATION_JSON_UTF8).content("{"
						+ "\"text\":\"Wake me up\","
						+ "\"time\":1231245,"
						+ "\"repeatable\":\"true\","
						+ "\"interval\":300"
						+ "}"))
			           .andExpect(status().isCreated())
			            ;
			this.mockMvc.perform(post("/v1/users/2/reminders").contentType(MediaType.APPLICATION_JSON_UTF8).content("{"
					+ "\"text\":\"Wake me up\","
					+ "\"time\":1231245,"
					+ "\"repeatable\":\"true\","
					+ "\"interval\":300"
					+ "}"))
		           .andExpect(status().isTooManyRequests())
		            ;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void zz_rate_limiter_remove_reminderid_test(){
	
			
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
				            .andExpect(status().isTooManyRequests());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	@Test
	public void zzz_rate_limiter_get_reminders_test(){
	

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
			controllers.setBackEndUrlPath("test");
			this.mockMvc.perform(get("/v1/users/20/reminders"))
			            .andExpect(status().isTooManyRequests())
			          ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_created_successful_reminder_post() {
		
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
	public void test_injection_prevention_reminder_post() {
		
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
						+ "\"text\":\"Wake me up'\","
						+ "\"time\":1231245,"
						+ "\"repeatable\":\"true\","
						+ "\"interval\":300"
						+ "}"))
			           .andExpect(status().isCreated())
			           .andExpect(jsonPath("$.text", is("Wake me up''")))
			            ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Test
	public void test_error_set_reminder_post() {
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
	public void test_missing_field_reminder_post() {
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
	public void test_internal_server_error_reminder_post() {
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
	public void test_service_unavaible_reminder_post() {
	 
		
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
	@Test
	public void test_service_unavailable_delete() {
		
		try {
			this.mockMvc.perform(delete("/v1/users/2/reminders/23"))
			            .andExpect(status().isServiceUnavailable());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void test_delete_reminder_successful() {
		
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
	public void test_unprocessable_entity_delete() {
		
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
	public void test_internal_server_error_delete() {
		
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
	@Test
	public void test_successful_get() {
		
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
	public void test_unprocessable_entity_get() {
		
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
	public void test_internal_server_error_get() {
		
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
	public void test_service_unavailable_get() {
		
		try {
			this.mockMvc.perform(get("/v1/users/20/reminders"))
			            .andExpect(status().is(503));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	
}

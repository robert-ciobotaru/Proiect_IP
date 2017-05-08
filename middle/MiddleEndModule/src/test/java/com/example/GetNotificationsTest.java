package com.example;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

public class GetNotificationsTest {

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
	public void testShouldBeOK() throws Exception {
		wireMockRule.stubFor(any(urlPathEqualTo("/"))
                .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
				.withBody("{"
						+ "\"userNotifications\": [{"
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
					    + "\"weatherNotificationsList\": [{"
					    		+ "\"location\": {"
					    			+ "\"city\": \"Iasi\","
					    			+ "\"country\": \"Romania\""
					    		+ "},"
					    		+ "\"text\": \"The weather is bad\""
					    	+ "},"
					    	+ "{"
					    		+ "\"location\": {"
					    			+ "\"city\": \"Iasi\","
					    			+ "\"country\": \"Romania\""
					    		+ "},"
					    		+ "\"text\": \"The weather is bad\""
					    	+ "}"
					    + "],"
					    + "\"earthquakesList\": [{"
					    	+ "\"magnitude\": 2,"
					    	+ "\"location\": {"
					    		+ "\"city\": \"Iasi\","
					    		+ "\"country\": \"Romania\""
					    	+ "},"
					    	+ "\"time\": \"2017-05-06T00:21:40\","
					    	+ "\"url\": \"http://link.ro\","
					    	+ "\"title\": \"The amazing earthquake\""
					    + "}],"
					    + "\"floodsList\": [{"
					    	+ "\"alertLevel\": \"BIG\","
					    	+ "\"location\": {"
					    		+ "\"city\": \"Iasi\","
					    		+ "\"country\": \"Romania\""
					    	+ "},"
					    	+ "\"time\": \"2017-05-06T00:21:40\","
					    	+ "\"url\": \"http://link.ro\","
					    	+ "\"title\": \"The amazing earthquake\","
					    	+ "\"description\": \"Description for the flood\""
					    + "}],"
					    + "\"cyclonesList\": [{"
					    	+ "\"alertLevel\": \"BIG\","
					    	+ "\"location\": {"
					    		+ "\"city\": \"Iasi\","
					    		+ "\"country\": \"Romania\""
					    	+ "},"
					    	+ "\"time\": \"2017-05-06T00:21:40\","
					    	+ "\"url\": \"http://link.ro\","
					    	+ "\"title\": \"The amazing cyclone\","
					    	+ "\"description\": \"Description for the cyclone\""
					    + "}],"
					    + "\"newsNotificationsList\": [{"
					    	+ "\"author\": \"Some guy\","
					    	+ "\"title\": \"The weather is bad\","
					    	+ "\"description\": \"The weaher is really bad\","
					    	+ "\"url\": \"http://link.ro\","
					    	+ "\"urlToImage\": \"http://...\","
					    	+ "\"publishedAt\": \"some location\""
					    	+ "},"
					    	+ "{"
					    	+ "\"author\": \"Some guy\","
					    	+ "\"title\": \"The weather is bad\","
					    	+ "\"description\": \"The weaher is really bad\","
					    	+ "\"url\": \"http://link.ro\","
					    	+ "\"urlToImage\": \"http://...\","
					    	+ "\"publishedAt\": \"some location\""
					    	+ "}"
					    + "],"
					+ "\"error\": \"\""
					+ "}")
				//.withStatus(201)
				));
		
		try {
			this.mockMvc.perform(get("/v1/users/23/notifications"))
			            .andExpect(status().is(200))
			            .andExpect(jsonPath("$.userNotificationsList[0].id", is(23)))
			            .andExpect(jsonPath("$.userNotificationsList[0].text", is("Wake me up")))
			            .andExpect(jsonPath("$.userNotificationsList[0].time", is(1231245)))
			            .andExpect(jsonPath("$.userNotificationsList[0].repeatable", is(true)))
			            .andExpect(jsonPath("$.userNotificationsList[0].interval", is(300)))
			            .andExpect(jsonPath("$.userNotificationsList[1].id", is(24)))
			            .andExpect(jsonPath("$.userNotificationsList[1].text", is("Get the kid")))
			            .andExpect(jsonPath("$.userNotificationsList[1].time", is(123245)))
			            .andExpect(jsonPath("$.userNotificationsList[1].repeatable", is(false)))
			            .andExpect(jsonPath("$.userNotificationsList[1].interval", is(400)))
			            .andExpect(jsonPath("$.weatherNotificationsList[0].location.city", is("Iasi")))
			            .andExpect(jsonPath("$.weatherNotificationsList[0].location.country", is("Romania")))
			            .andExpect(jsonPath("$.weatherNotificationsList[0].text", is("The weather is bad")))
			            .andExpect(jsonPath("$.weatherNotificationsList[1].location.city", is("Iasi")))
			            .andExpect(jsonPath("$.weatherNotificationsList[1].location.country", is("Romania")))
			            .andExpect(jsonPath("$.weatherNotificationsList[1].text", is("The weather is bad")))
			            .andExpect(jsonPath("$.hazzardNotifications.earthquakesList[0].magnitude", is(2)))
			            .andExpect(jsonPath("$.hazzardNotifications.earthquakesList[0].location.city", is("Iasi")))
			            .andExpect(jsonPath("$.hazzardNotifications.earthquakesList[0].location.country", is("Romania")))
			            .andExpect(jsonPath("$.hazzardNotifications.earthquakesList[0].time", is("2017-05-06T00:21:40")))
			            .andExpect(jsonPath("$.hazzardNotifications.earthquakesList[0].url", is("http://link.ro")))
			            .andExpect(jsonPath("$.hazzardNotifications.earthquakesList[0].title", is("The amazing earthquake")))
			            .andExpect(jsonPath("$.hazzardNotifications.hazzard.floodsList[0].alertLevel", is("BIG")))
			            .andExpect(jsonPath("$.hazzardNotifications.hazzard.floodsList[0].location.country", is("Romania")))
			            .andExpect(jsonPath("$.hazzardNotifications.hazzard.floodsList[0].time", is("2017-05-06T00:21:40")))
			            .andExpect(jsonPath("$.hazzardNotifications.hazzard.floodsList[0].url", is("http://link.ro")))
			            .andExpect(jsonPath("$.hazzardNotifications.hazzard.floodsList[0].title", is("The amazing earthquake")))
			            .andExpect(jsonPath("$.hazzardNotifications.hazzard.floodsList[0].description", is("Description for the flood")))
			            .andExpect(jsonPath("$.hazzardNotifications.hazzard.cyclonesList[0].alertLevel", is("BIG")))
			            .andExpect(jsonPath("$.hazzardNotifications.hazzard.cyclonesList[0].location.country", is("Romania")))
			            .andExpect(jsonPath("$.hazzardNotifications.hazzard.cyclonesList[0].time", is("2017-05-06T00:21:40")))
			            .andExpect(jsonPath("$.hazzardNotifications.hazzard.cyclonesList[0].url", is("http://link.ro")))
			            .andExpect(jsonPath("$.hazzardNotifications.hazzard.cyclonesList[0].title", is("The amazing earthquake")))
			            .andExpect(jsonPath("$.hazzardNotifications.hazzard.cyclonesList[0].description", is("Description for the cyclone")))
			            .andExpect(jsonPath("$.newsNotificationsList[0].author", is("Some guy")))
			            .andExpect(jsonPath("$.newsNotificationsList[0].title", is("The weather is bad")))
			            .andExpect(jsonPath("$.newsNotificationsList[0].description", is("The weaher is really bad")))
			            .andExpect(jsonPath("$.newsNotificationsList[0].url", is("http://link.ro")))
			            .andExpect(jsonPath("$.newsNotificationsList[0].urlToImage", is("http://...")))
			            .andExpect(jsonPath("$.newsNotificationsList[0].publishedAt", is("some location")))
			            .andExpect(jsonPath("$.newsNotificationsList[1].author", is("Some guy")))
			            .andExpect(jsonPath("$.newsNotificationsList[1].title", is("The weather is bad")))
			            .andExpect(jsonPath("$.newsNotificationsList[1].description", is("The weaher is really bad")))
			            .andExpect(jsonPath("$.newsNotificationsList[1].url", is("http://link.ro")))
			            .andExpect(jsonPath("$.newsNotificationsList[1].urlToImage", is("http://...")))
			            .andExpect(jsonPath("$.newsNotificationsList[1].publishedAt", is("some location")));
			            
			            
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}
package com.usermangement.tests;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.any;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.usermanagement.controllers.NotificationController;
import com.usermanagement.requestmonitor.RequestMonitor;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestNotificationController {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(9001);

    private MockMvc mockMvc;
    private NotificationController controllers;
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {}

    @AfterClass
    public static void tearDownAfterClass() throws Exception {}

    @Before
    public void setUp() throws Exception {
        controllers = new NotificationController();
        controllers.setBackEndUrlPath("http://localhost:9001");
        this.mockMvc = MockMvcBuilders.standaloneSetup(controllers).build();
        RequestMonitor.getRequestMonitorInstance().reset();
        RequestMonitor.getRequestMonitorInstance().setMaxRequestCount(100);
        RequestMonitor.getRequestMonitorInstance().setMaxGlobalRequestCount(1000);
    }

    @After
    public void tearDown() throws Exception {
        mockMvc = null;
        controllers = null;
    }

    @Test
    public void z_test_rate_limiter() throws Exception {
        int initVal = RequestMonitor.getRequestMonitorInstance().getMaxRequestCount();
        System.out.println("first initVal: " + initVal);

        RequestMonitor.getRequestMonitorInstance().setMaxRequestCount(0);

        this.mockMvc.perform(get("/v1/users/23/notifications"))
            .andExpect(status().isTooManyRequests());



        RequestMonitor.getRequestMonitorInstance().setMaxRequestCount(initVal);
        initVal = RequestMonitor.getRequestMonitorInstance().getMaxRequestCount();
        System.out.println("second initVal: " + initVal);
    }

    @Test
    public void test_successful_get() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("{" +
                    "\"userNotifications\": [{" +
                    "\"id\": 23," +
                    "\"text\": \"Get the kid\"," +
                    "\"time\": 213141," +
                    "\"repeatable\": true," +
                    "\"interval\": 234" +
                    "}," +
                    "{" +
                    "\"id\": 24," +
                    "\"text\": \"Burn the house\"," +
                    "\"time\": 54234," +
                    "\"repeatable\": false," +
                    "\"interval\": 234" +
                    "}" +
                    "]," +
                    "\"weatherNotificationsList\": [{" +
                    "\"location\": {" +
                    "\"city\": \"Iasi\"," +
                    "\"country\": \"Romania\"" +
                    "}," +
                    "\"text\": \"The weather is bad\"" +
                    "}," +
                    "{" +
                    "\"location\": {" +
                    "\"city\": \"Iasi\"," +
                    "\"country\": \"Romania\"" +
                    "}," +
                    "\"text\": \"The weather is bad\"" +
                    "}" +
                    "]," +
                    "\"earthquakesList\": [{" +
                    "\"magnitude\": 2," +
                    "\"place\": \"Iasi, Romania\"," +
                    "\"time\": \"2017-05-06T00:21:40\"," +
                    "\"url\": \"http://link.ro\"," +
                    "\"title\": \"The amazing earthquake\"" +
                    "}]," +
                    "\"floodsList\": [{" +
                    "\"alertLevel\": \"BIG\"," +
                    "\"country\": \"Romania\"," +
                    "\"time\": \"2017-05-06T00:21:40\"," +
                    "\"url\": \"http://link.ro\"," +
                    "\"title\": \"The amazing earthquake\"," +
                    "\"description\": \"Description for the flood\"" +
                    "}]," +
                    "\"cyclonesList\": [{" +
                    "\"alertLevel\": \"BIG\"," +
                    "\"country\": \"Romania\"," +
                    "\"time\": \"2017-05-06T00:21:40\"," +
                    "\"url\": \"http://link.ro\"," +
                    "\"title\": \"The amazing cyclone\"," +
                    "\"description\": \"Description for the cyclone\"" +
                    "}]," +
                    "\"newsNotificationsList\": [{" +
                    "\"author\": \"Some guy\"," +
                    "\"title\": \"The weather is bad\"," +
                    "\"description\": \"The weaher is really bad\"," +
                    "\"url\": \"http://link.ro\"," +
                    "\"urlToImage\": \"http://...\"," +
                    "\"publishedAt\": \"some location\"" +
                    "}," +
                    "{" +
                    "\"author\": \"Some guy\"," +
                    "\"title\": \"The weather is bad\"," +
                    "\"description\": \"The weaher is really bad\"," +
                    "\"url\": \"http://link.ro\"," +
                    "\"urlToImage\": \"http://...\"," +
                    "\"publishedAt\": \"some location\"" +
                    "}" +
                    "]," +
                    "\"error\": \"\"" +
                    "}")
                //.withStatus(201)
            ));


        this.mockMvc.perform(get("/v1/users/23/notifications"))
            .andExpect(status().is(200))
            .andExpect(jsonPath("$.userNotificationsList[0].id", is(23)))
            .andExpect(jsonPath("$.userNotificationsList[0].text", is("Get the kid")))
            .andExpect(jsonPath("$.userNotificationsList[0].time", is(213141)))
            .andExpect(jsonPath("$.userNotificationsList[0].repeatable", is(true)))
            .andExpect(jsonPath("$.userNotificationsList[0].interval", is(234)))
            .andExpect(jsonPath("$.userNotificationsList[1].id", is(24)))
            .andExpect(jsonPath("$.userNotificationsList[1].text", is("Burn the house")))
            .andExpect(jsonPath("$.userNotificationsList[1].time", is(54234)))
            .andExpect(jsonPath("$.userNotificationsList[1].repeatable", is(false)))
            .andExpect(jsonPath("$.userNotificationsList[1].interval", is(234)))
            .andExpect(jsonPath("$.weatherNotificationsList[0].location.city", is("Iasi")))
            .andExpect(jsonPath("$.weatherNotificationsList[0].location.country", is("Romania")))
            .andExpect(jsonPath("$.weatherNotificationsList[0].text", is("The weather is bad")))
            .andExpect(jsonPath("$.weatherNotificationsList[1].location.city", is("Iasi")))
            .andExpect(jsonPath("$.weatherNotificationsList[1].location.country", is("Romania")))
            .andExpect(jsonPath("$.weatherNotificationsList[1].text", is("The weather is bad")))
            .andExpect(jsonPath("$.hazzardNotifications.earthquakesList[0].magnitude", is(2)))
            .andExpect(jsonPath("$.hazzardNotifications.earthquakesList[0].place", is("Iasi, Romania")))
            .andExpect(jsonPath("$.hazzardNotifications.earthquakesList[0].time", is("2017-05-06T00:21:40")))
            .andExpect(jsonPath("$.hazzardNotifications.earthquakesList[0].url", is("http://link.ro")))
            .andExpect(jsonPath("$.hazzardNotifications.earthquakesList[0].title", is("The amazing earthquake")))
            .andExpect(jsonPath("$.hazzardNotifications.hazzard.floodsList[0].alertLevel", is("BIG")))
            .andExpect(jsonPath("$.hazzardNotifications.hazzard.floodsList[0].country", is("Romania")))
            .andExpect(jsonPath("$.hazzardNotifications.hazzard.floodsList[0].time", is("2017-05-06T00:21:40")))
            .andExpect(jsonPath("$.hazzardNotifications.hazzard.floodsList[0].url", is("http://link.ro")))
            .andExpect(jsonPath("$.hazzardNotifications.hazzard.floodsList[0].title", is("The amazing earthquake")))
            .andExpect(jsonPath("$.hazzardNotifications.hazzard.floodsList[0].description", is("Description for the flood")))
            .andExpect(jsonPath("$.hazzardNotifications.hazzard.cyclonesList[0].alertLevel", is("BIG")))
            .andExpect(jsonPath("$.hazzardNotifications.hazzard.cyclonesList[0].country", is("Romania")))
            .andExpect(jsonPath("$.hazzardNotifications.hazzard.cyclonesList[0].time", is("2017-05-06T00:21:40")))
            .andExpect(jsonPath("$.hazzardNotifications.hazzard.cyclonesList[0].url", is("http://link.ro")))
            .andExpect(jsonPath("$.hazzardNotifications.hazzard.cyclonesList[0].title", is("The amazing cyclone")))
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




    }

    @Test
    public void test_service_unavailable_reminder_get() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/")));



        this.mockMvc.perform(get("/v1/users/23/notifications"))
            .andExpect(status().is(503));


    }

    @Test
    public void test_internal_sever_error_notification_get() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("{" +
                    "\"userNotifications\": [{" +
                    "\"id\": 23," +
                    "\"text\": \"Get the kid\"," +
                    "\"time\": 213141," +
                    "\"repeatable\": true," +
                    "\"interval\": 234" +
                    "}," +
                    "{" +
                    "\"id\": 24," +
                    "\"text\": \"Burn the house\"," +
                    "\"time\": 54234," +
                    "\"repeatable\": false," +
                    "\"interval\": 234" +
                    "}" +
                    "]," +
                    "\"weatherNotificationsList\": [{" +
                    "\"location\": {" +
                    "\"city\": \"Iasi\"," +
                    "\"country\": \"Romania\"" +
                    "}," +
                    "\"text\": \"The weather is bad\"" +
                    "}," +
                    "{" +
                    "\"location\": {" +
                    "\"city\": \"Iasi\"," +
                    "\"country\": \"Romania\"" +
                    "}," +
                    "\"text\": \"The weather is bad\"" +
                    "}" +
                    "]," +
                    "\"earthquakesList\": [{" +
                    "\"magnitude\": 2," +
                    "\"place\": \"Iasi, Romania\"," +
                    "\"time\": \"2017-05-06T00:21:40\"," +
                    "\"url\": \"http://link.ro\"," +
                    "\"title\": \"The amazing earthquake\"" +
                    "}]," +
                    "\"floodsList\": [{" +
                    "\"alertLevel\": \"BIG\"," +
                    "\"country\": \"Romania\"," +
                    "\"time\": \"2017-05-06T00:21:40\"," +
                    "\"url\": \"http://link.ro\"," +
                    "\"title\": \"The amazing earthquake\"," +
                    "\"description\": \"Description for the flood\"" +
                    "}]," +
                    "\"cyclonesList\": [{" +
                    "\"alertLevel\": \"BIG\"," +
                    "\"country\": \"Romania\"," +
                    "\"time\": \"2017-05-06T00:21:40\"," +
                    "\"url\": \"http://link.ro\"," +
                    "\"title\": \"The amazing cyclone\"," +
                    "\"description\": \"Description for the cyclone\"" +
                    "}]," +
                    "\"newsNotificationsList\": [{" +
                    "\"author\": \"Some guy\"," +
                    "\"title\": \"The weather is bad\"," +
                    "\"description\": \"The weaher is really bad\"," +
                    "\"url\": \"http://link.ro\"," +
                    "\"urlToImage\": \"http://...\"," +
                    "\"publishedAt\": \"some location\"" +
                    "}," +
                    "{" +
                    "\"author\": \"Some guy\"," +
                    "\"title\": \"The weather is bad\"," +
                    "\"url\": \"http://link.ro\"," +
                    "\"urlToImage\": \"http://...\"," +
                    "\"publishedAt\": \"some location\"" +
                    "}" +
                    "]," +
                    "\"error\": \"\"" +
                    "}")
                //.withStatus(201)
            ));


        this.mockMvc.perform(get("/v1/users/23/notifications"))
            .andExpect(status().is(500));




    }
    @Test
    public void test_internal_sever_error_notification_get_errornul() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("{"

                    +
                    "}")
                //.withStatus(201)
            ));


        this.mockMvc.perform(get("/v1/users/23/notifications"))
            .andExpect(status().is(500));




    }

    @Test
    public void test_unprocessable_entity_notification_test() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("{" +
                    "\"userNotifications\": [{" +
                    "\"id\": 23," +
                    "\"text\": \"Get the kid\"," +
                    "\"time\": 213141," +
                    "\"repeatable\": true," +
                    "\"interval\": 234" +
                    "}," +
                    "{" +
                    "\"id\": 24," +
                    "\"text\": \"Burn the house\"," +
                    "\"time\": 54234," +
                    "\"repeatable\": false," +
                    "\"interval\": 234" +
                    "}" +
                    "]," +
                    "\"weatherNotificationsList\": [{" +
                    "\"location\": {" +
                    "\"city\": \"Iasi\"," +
                    "\"country\": \"Romania\"" +
                    "}," +
                    "\"text\": \"The weather is bad\"" +
                    "}," +
                    "{" +
                    "\"location\": {" +
                    "\"city\": \"Iasi\"," +
                    "\"country\": \"Romania\"" +
                    "}," +
                    "\"text\": \"The weather is bad\"" +
                    "}" +
                    "]," +
                    "\"earthquakesList\": [{" +
                    "\"magnitude\": 2," +
                    "\"place\": \"Iasi, Romania\"," +
                    "\"time\": \"2017-05-06T00:21:40\"," +
                    "\"url\": \"http://link.ro\"," +
                    "\"title\": \"The amazing earthquake\"" +
                    "}]," +
                    "\"floodsList\": [{" +
                    "\"alertLevel\": \"BIG\"," +
                    "\"country\": \"Romania\"," +
                    "\"time\": \"2017-05-06T00:21:40\"," +
                    "\"url\": \"http://link.ro\"," +
                    "\"title\": \"The amazing earthquake\"," +
                    "\"description\": \"Description for the flood\"" +
                    "}]," +
                    "\"cyclonesList\": [{" +
                    "\"alertLevel\": \"BIG\"," +
                    "\"country\": \"Romania\"," +
                    "\"time\": \"2017-05-06T00:21:40\"," +
                    "\"url\": \"http://link.ro\"," +
                    "\"title\": \"The amazing cyclone\"," +
                    "\"description\": \"Description for the cyclone\"" +
                    "}]," +
                    "\"newsNotificationsList\": [{" +
                    "\"author\": \"Some guy\"," +
                    "\"title\": \"The weather is bad\"," +
                    "\"description\": \"The weaher is really bad\"," +
                    "\"url\": \"http://link.ro\"," +
                    "\"urlToImage\": \"http://...\"," +
                    "\"publishedAt\": \"some location\"" +
                    "}," +
                    "{" +
                    "\"author\": \"Some guy\"," +
                    "\"title\": \"The weather is bad\"," +
                    "\"description\": \"The weaher is really bad\"," +
                    "\"url\": \"http://link.ro\"," +
                    "\"urlToImage\": \"http://...\"," +
                    "\"publishedAt\": \"some location\"" +
                    "}" +
                    "]," +
                    "\"error\": \"Error message\"" +
                    "}")
                //.withStatus(201)
            ));


        this.mockMvc.perform(get("/v1/users/23/notifications"))
            .andExpect(status().is(422));




    }

    @Test
    public void test_unknown_server_error() throws Exception {

        this.controllers.setBackEndUrlPath("asfsagrarrae");


        this.mockMvc.perform(get("/v1/users/23/notifications"))
            .andExpect(status().is(500));

    }
    @Test
    public void test_rest_client_exception() throws Exception {

        this.controllers.setBackEndUrlPath("http://www.thislink.com");


        this.mockMvc.perform(get("/v1/users/23/notifications"))
            .andExpect(status().is(503));

    }
    
    @Test
    public void test_internal_server_error_notification_get_null_field_weatherNotficationsList() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("{" +
                    "\"userNotifications\": [{" +
                    "\"id\": 23," +
                    "\"text\": \"Get the kid\"," +
                    "\"time\": 213141," +
                    "\"repeatable\": true," +
                    "\"interval\": 234" +
                    "}," +
                    "{" +
                    "\"id\": 24," +
                    "\"text\": \"Burn the house\"," +
                    "\"time\": 54234," +
                    "\"repeatable\": false," +
                    "\"interval\": 234" +
                    "}" +
                    "]," +
                    "\"weatherNotificationsList\": [{" +
                    "\"location\": {" +
                    "\"city\": null," +
                    "\"country\": \"Romania\"" +
                    "}," +
                    "\"text\": \"The weather is bad\"" +
                    "}," +
                    "{" +
                    "\"location\": {" +
                    "\"city\": \"Iasi\"," +
                    "\"country\": \"Romania\"" +
                    "}," +
                    "\"text\": \"The weather is bad\"" +
                    "}" +
                    "]," +
                    "\"earthquakesList\": [{" +
                    "\"magnitude\": 2," +
                    "\"place\": \"Iasi, Romania\"," +
                    "\"time\": \"2017-05-06T00:21:40\"," +
                    "\"url\": \"http://link.ro\"," +
                    "\"title\": \"The amazing earthquake\"" +
                    "}]," +
                    "\"floodsList\": [{" +
                    "\"alertLevel\": \"BIG\"," +
                    "\"country\": \"Romania\"," +
                    "\"time\": \"2017-05-06T00:21:40\"," +
                    "\"url\": \"http://link.ro\"," +
                    "\"title\": \"The amazing earthquake\"," +
                    "\"description\": \"Description for the flood\"" +
                    "}]," +
                    "\"cyclonesList\": [{" +
                    "\"alertLevel\": \"BIG\"," +
                    "\"country\": \"Romania\"," +
                    "\"time\": \"2017-05-06T00:21:40\"," +
                    "\"url\": \"http://link.ro\"," +
                    "\"title\": \"The amazing cyclone\"," +
                    "\"description\": \"Description for the cyclone\"" +
                    "}]," +
                    "\"newsNotificationsList\": [{" +
                    "\"author\": \"Some guy\"," +
                    "\"title\": \"The weather is bad\"," +
                    "\"description\": \"The weaher is really bad\"," +
                    "\"url\": \"http://link.ro\"," +
                    "\"urlToImage\": \"http://...\"," +
                    "\"publishedAt\": \"some location\"" +
                    "}," +
                    "{" +
                    "\"author\": \"Some guy\"," +
                    "\"title\": \"The weather is bad\"," +
                    "\"description\": \"The weaher is really bad\"," +
                    "\"url\": \"http://link.ro\"," +
                    "\"urlToImage\": \"http://...\"," +
                    "\"publishedAt\": \"some location\"" +
                    "}" +
                    "]," +
                    "\"error\": \"\"" +
                    "}")
                //.withStatus(201)
            ));


        this.mockMvc.perform(get("/v1/users/23/notifications"))
            .andExpect(status().is(500));
        

    }
    
    @Test
    public void test_internal_server_error_notification_get_null_field_userNotficationsList() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("{" +
                    "\"userNotifications\": [{" +
                    "\"id\": null," +
                    "\"text\": \"Get the kid\"," +
                    "\"time\": 213141," +
                    "\"repeatable\": true," +
                    "\"interval\": 234" +
                    "}," +
                    "{" +
                    "\"id\": 24," +
                    "\"text\": \"Burn the house\"," +
                    "\"time\": 54234," +
                    "\"repeatable\": false," +
                    "\"interval\": 234" +
                    "}" +
                    "]," +
                    "\"weatherNotificationsList\": [{" +
                    "\"location\": {" +
                    "\"city\": \"Iasi\"," +
                    "\"country\": \"Romania\"" +
                    "}," +
                    "\"text\": \"The weather is bad\"" +
                    "}," +
                    "{" +
                    "\"location\": {" +
                    "\"city\": \"Iasi\"," +
                    "\"country\": \"Romania\"" +
                    "}," +
                    "\"text\": \"The weather is bad\"" +
                    "}" +
                    "]," +
                    "\"earthquakesList\": [{" +
                    "\"magnitude\": 2," +
                    "\"place\": \"Iasi, Romania\"," +
                    "\"time\": \"2017-05-06T00:21:40\"," +
                    "\"url\": \"http://link.ro\"," +
                    "\"title\": \"The amazing earthquake\"" +
                    "}]," +
                    "\"floodsList\": [{" +
                    "\"alertLevel\": \"BIG\"," +
                    "\"country\": \"Romania\"," +
                    "\"time\": \"2017-05-06T00:21:40\"," +
                    "\"url\": \"http://link.ro\"," +
                    "\"title\": \"The amazing earthquake\"," +
                    "\"description\": \"Description for the flood\"" +
                    "}]," +
                    "\"cyclonesList\": [{" +
                    "\"alertLevel\": \"BIG\"," +
                    "\"country\": \"Romania\"," +
                    "\"time\": \"2017-05-06T00:21:40\"," +
                    "\"url\": \"http://link.ro\"," +
                    "\"title\": \"The amazing cyclone\"," +
                    "\"description\": \"Description for the cyclone\"" +
                    "}]," +
                    "\"newsNotificationsList\": [{" +
                    "\"author\": \"Some guy\"," +
                    "\"title\": \"The weather is bad\"," +
                    "\"description\": \"The weaher is really bad\"," +
                    "\"url\": \"http://link.ro\"," +
                    "\"urlToImage\": \"http://...\"," +
                    "\"publishedAt\": \"some location\"" +
                    "}," +
                    "{" +
                    "\"author\": \"Some guy\"," +
                    "\"title\": \"The weather is bad\"," +
                    "\"description\": \"The weaher is really bad\"," +
                    "\"url\": \"http://link.ro\"," +
                    "\"urlToImage\": \"http://...\"," +
                    "\"publishedAt\": \"some location\"" +
                    "}" +
                    "]," +
                    "\"error\": \"\"" +
                    "}")
                //.withStatus(201)
            ));


        this.mockMvc.perform(get("/v1/users/23/notifications"))
            .andExpect(status().is(500));
    
    }
    
    @Test
    public void test_internal_server_error_notification_get_null_field_earthquakesNotificationsList() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("{" +
                    "\"userNotifications\": [{" +
                    "\"id\": 23," +
                    "\"text\": \"Get the kid\"," +
                    "\"time\": 213141," +
                    "\"repeatable\": true," +
                    "\"interval\": 234" +
                    "}," +
                    "{" +
                    "\"id\": 24," +
                    "\"text\": \"Burn the house\"," +
                    "\"time\": 54234," +
                    "\"repeatable\": false," +
                    "\"interval\": 234" +
                    "}" +
                    "]," +
                    "\"weatherNotificationsList\": [{" +
                    "\"location\": {" +
                    "\"city\": \"Iasi\"," +
                    "\"country\": \"Romania\"" +
                    "}," +
                    "\"text\": \"The weather is bad\"" +
                    "}," +
                    "{" +
                    "\"location\": {" +
                    "\"city\": \"Iasi\"," +
                    "\"country\": \"Romania\"" +
                    "}," +
                    "\"text\": \"The weather is bad\"" +
                    "}" +
                    "]," +
                    "\"earthquakesList\": [{" +
                    "\"magnitude\": null," +
                    "\"place\": \"Iasi, Romania\"," +
                    "\"time\": \"2017-05-06T00:21:40\"," +
                    "\"url\": \"http://link.ro\"," +
                    "\"title\": \"The amazing earthquake\"" +
                    "}]," +
                    "\"floodsList\": [{" +
                    "\"alertLevel\": \"BIG\"," +
                    "\"country\": \"Romania\"," +
                    "\"time\": \"2017-05-06T00:21:40\"," +
                    "\"url\": \"http://link.ro\"," +
                    "\"title\": \"The amazing earthquake\"," +
                    "\"description\": \"Description for the flood\"" +
                    "}]," +
                    "\"cyclonesList\": [{" +
                    "\"alertLevel\": \"BIG\"," +
                    "\"country\": \"Romania\"," +
                    "\"time\": \"2017-05-06T00:21:40\"," +
                    "\"url\": \"http://link.ro\"," +
                    "\"title\": \"The amazing cyclone\"," +
                    "\"description\": \"Description for the cyclone\"" +
                    "}]," +
                    "\"newsNotificationsList\": [{" +
                    "\"author\": \"Some guy\"," +
                    "\"title\": \"The weather is bad\"," +
                    "\"description\": \"The weaher is really bad\"," +
                    "\"url\": \"http://link.ro\"," +
                    "\"urlToImage\": \"http://...\"," +
                    "\"publishedAt\": \"some location\"" +
                    "}," +
                    "{" +
                    "\"author\": \"Some guy\"," +
                    "\"title\": \"The weather is bad\"," +
                    "\"description\": \"The weaher is really bad\"," +
                    "\"url\": \"http://link.ro\"," +
                    "\"urlToImage\": \"http://...\"," +
                    "\"publishedAt\": \"some location\"" +
                    "}" +
                    "]," +
                    "\"error\": \"\"" +
                    "}")
                //.withStatus(201)
            ));


        this.mockMvc.perform(get("/v1/users/23/notifications"))
            .andExpect(status().is(500));
    
    }
    
    @Test
    public void test_internal_server_error_notification_get_null_field_floodsNotificationsList() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("{" +
                    "\"userNotifications\": [{" +
                    "\"id\": 23," +
                    "\"text\": \"Get the kid\"," +
                    "\"time\": 213141," +
                    "\"repeatable\": true," +
                    "\"interval\": 234" +
                    "}," +
                    "{" +
                    "\"id\": 24," +
                    "\"text\": \"Burn the house\"," +
                    "\"time\": 54234," +
                    "\"repeatable\": false," +
                    "\"interval\": 234" +
                    "}" +
                    "]," +
                    "\"weatherNotificationsList\": [{" +
                    "\"location\": {" +
                    "\"city\": \"Iasi\"," +
                    "\"country\": \"Romania\"" +
                    "}," +
                    "\"text\": \"The weather is bad\"" +
                    "}," +
                    "{" +
                    "\"location\": {" +
                    "\"city\": \"Iasi\"," +
                    "\"country\": \"Romania\"" +
                    "}," +
                    "\"text\": \"The weather is bad\"" +
                    "}" +
                    "]," +
                    "\"earthquakesList\": [{" +
                    "\"magnitude\": 2," +
                    "\"place\": \"Iasi, Romania\"," +
                    "\"time\": \"2017-05-06T00:21:40\"," +
                    "\"url\": \"http://link.ro\"," +
                    "\"title\": \"The amazing earthquake\"" +
                    "}]," +
                    "\"floodsList\": [{" +
                    "\"alertLevel\": null," +
                    "\"country\": \"Romania\"," +
                    "\"time\": \"2017-05-06T00:21:40\"," +
                    "\"url\": \"http://link.ro\"," +
                    "\"title\": \"The amazing earthquake\"," +
                    "\"description\": \"Description for the flood\"" +
                    "}]," +
                    "\"cyclonesList\": [{" +
                    "\"alertLevel\": \"BIG\"," +
                    "\"country\": \"Romania\"," +
                    "\"time\": \"2017-05-06T00:21:40\"," +
                    "\"url\": \"http://link.ro\"," +
                    "\"title\": \"The amazing cyclone\"," +
                    "\"description\": \"Description for the cyclone\"" +
                    "}]," +
                    "\"newsNotificationsList\": [{" +
                    "\"author\": \"Some guy\"," +
                    "\"title\": \"The weather is bad\"," +
                    "\"description\": \"The weaher is really bad\"," +
                    "\"url\": \"http://link.ro\"," +
                    "\"urlToImage\": \"http://...\"," +
                    "\"publishedAt\": \"some location\"" +
                    "}," +
                    "{" +
                    "\"author\": \"Some guy\"," +
                    "\"title\": \"The weather is bad\"," +
                    "\"description\": \"The weaher is really bad\"," +
                    "\"url\": \"http://link.ro\"," +
                    "\"urlToImage\": \"http://...\"," +
                    "\"publishedAt\": \"some location\"" +
                    "}" +
                    "]," +
                    "\"error\": \"\"" +
                    "}")
                //.withStatus(201)
            ));


        this.mockMvc.perform(get("/v1/users/23/notifications"))
            .andExpect(status().is(500));
    
    }
    
    @Test
    public void test_internal_server_error_notification_get_null_field_cyclonesNotificationsList() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("{" +
                    "\"userNotifications\": [{" +
                    "\"id\": 23," +
                    "\"text\": \"Get the kid\"," +
                    "\"time\": 213141," +
                    "\"repeatable\": true," +
                    "\"interval\": 234" +
                    "}," +
                    "{" +
                    "\"id\": 24," +
                    "\"text\": \"Burn the house\"," +
                    "\"time\": 54234," +
                    "\"repeatable\": false," +
                    "\"interval\": 234" +
                    "}" +
                    "]," +
                    "\"weatherNotificationsList\": [{" +
                    "\"location\": {" +
                    "\"city\": \"Iasi\"," +
                    "\"country\": \"Romania\"" +
                    "}," +
                    "\"text\": \"The weather is bad\"" +
                    "}," +
                    "{" +
                    "\"location\": {" +
                    "\"city\": \"Iasi\"," +
                    "\"country\": \"Romania\"" +
                    "}," +
                    "\"text\": \"The weather is bad\"" +
                    "}" +
                    "]," +
                    "\"earthquakesList\": [{" +
                    "\"magnitude\": 2," +
                    "\"place\": \"Iasi, Romania\"," +
                    "\"time\": \"2017-05-06T00:21:40\"," +
                    "\"url\": \"http://link.ro\"," +
                    "\"title\": \"The amazing earthquake\"" +
                    "}]," +
                    "\"floodsList\": [{" +
                    "\"alertLevel\": \"BIG\"," +
                    "\"country\": \"Romania\"," +
                    "\"time\": \"2017-05-06T00:21:40\"," +
                    "\"url\": \"http://link.ro\"," +
                    "\"title\": \"The amazing earthquake\"," +
                    "\"description\": \"Description for the flood\"" +
                    "}]," +
                    "\"cyclonesList\": [{" +
                    "\"alertLevel\": null," +
                    "\"country\": \"Romania\"," +
                    "\"time\": \"2017-05-06T00:21:40\"," +
                    "\"url\": \"http://link.ro\"," +
                    "\"title\": \"The amazing cyclone\"," +
                    "\"description\": \"Description for the cyclone\"" +
                    "}]," +
                    "\"newsNotificationsList\": [{" +
                    "\"author\": \"Some guy\"," +
                    "\"title\": \"The weather is bad\"," +
                    "\"description\": \"The weaher is really bad\"," +
                    "\"url\": \"http://link.ro\"," +
                    "\"urlToImage\": \"http://...\"," +
                    "\"publishedAt\": \"some location\"" +
                    "}," +
                    "{" +
                    "\"author\": \"Some guy\"," +
                    "\"title\": \"The weather is bad\"," +
                    "\"description\": \"The weaher is really bad\"," +
                    "\"url\": \"http://link.ro\"," +
                    "\"urlToImage\": \"http://...\"," +
                    "\"publishedAt\": \"some location\"" +
                    "}" +
                    "]," +
                    "\"error\": \"\"" +
                    "}")
                //.withStatus(201)
            ));


        this.mockMvc.perform(get("/v1/users/23/notifications"))
            .andExpect(status().is(500));
    
    }
   
}
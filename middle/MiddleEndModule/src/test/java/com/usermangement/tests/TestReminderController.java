package com.usermangement.tests;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

import static com.github.tomakehurst.wiremock.client.WireMock.any;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.hamcrest.CoreMatchers.is;
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
import com.usermanagement.requestmonitor.RequestMonitor;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestReminderController {
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(9001);

    private MockMvc mockMvc;
    private ReminderController controllers;
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {}

    @AfterClass
    public static void tearDownAfterClass() throws Exception {}

    @Before
    public void setUp() throws Exception {
        controllers = new ReminderController();
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
    public void z_rate_limiter_test() throws Exception {

        int requestCount = RequestMonitor.getRequestMonitorInstance().getMaxRequestCount();
        System.out.println("f requestCount: " + requestCount);
        RequestMonitor.getRequestMonitorInstance().setMaxRequestCount(1);


        wireMockRule.stubFor(any(urlPathEqualTo("/"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(
                    "{" +
                    "\"notificationId\" : 23," +
                    "\"error\" : \"\"" +
                    "}")

            ));


        this.mockMvc.perform(post("/v1/users/2/reminders").contentType(MediaType.APPLICATION_JSON_UTF8).content("{" +
                "\"text\":\"Wake me up\"," +
                "\"time\":1231245," +
                "\"repeatable\":\"true\"," +
                "\"interval\":300" +
                "}"))
            .andExpect(status().isCreated());
        this.mockMvc.perform(post("/v1/users/2/reminders").contentType(MediaType.APPLICATION_JSON_UTF8).content("{" +
                "\"text\":\"Wake me up\"," +
                "\"time\":1231245," +
                "\"repeatable\":\"true\"," +
                "\"interval\":300" +
                "}"))
            .andExpect(status().isTooManyRequests());



        RequestMonitor.getRequestMonitorInstance().setMaxRequestCount(requestCount);
        requestCount = RequestMonitor.getRequestMonitorInstance().getMaxRequestCount();

    }

    @Test
    public void zz_rate_limiter_remove_reminderid_test() throws Exception {

        int requestCount = RequestMonitor.getRequestMonitorInstance().getMaxRequestCount();
        System.out.println("f requestCount: " + requestCount);
        RequestMonitor.getRequestMonitorInstance().setMaxRequestCount(1);

        wireMockRule.stubFor(any(urlPathEqualTo("/"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("{" +
                    "\"error\" : \"\"" +
                    "}"
                )
            ));

        this.mockMvc.perform(delete("/v1/users/2/reminders/23"))
            .andExpect(status().isOk());

        this.mockMvc.perform(delete("/v1/users/2/reminders/23"))
            .andExpect(status().isTooManyRequests());



        RequestMonitor.getRequestMonitorInstance().setMaxRequestCount(requestCount);
        requestCount = RequestMonitor.getRequestMonitorInstance().getMaxRequestCount();

    }



    //	@Test
    //	public void zzz_rate_limiter_get_reminders_test(){
    //		
    //
    //        wireMockRule.stubFor(any(urlPathEqualTo("/"))
    //                .willReturn(aResponse()
    //                .withHeader("Content-Type", "application/json")
    //				.withBody(
    //						"{"
    //							      + "\"notificationsList\": [{"
    //							        + "\"id\": 23,"
    //							        + "\"text\": \"Get the kid\","
    //							        + "\"time\": 213141,"
    //							        + "\"repeatable\": true,"
    //							        + "\"interval\": 234"
    //							       + "},"
    //							       + "{"
    //							        + "\"id\": 24,"
    //							        + "\"text\": \"Burn the house\","
    //							        + "\"time\": 54234,"
    //							        + "\"repeatable\": false,"
    //							        + "\"interval\": 234"
    //							       + "}"
    //							         + "],"
    //							       +"\"error\" : \"\""
    //					+ "}")
    //				//.withStatus(201)
    //				));
    //		
    //		try {
    //			controllers.setBackEndUrlPath("test");
    //			this.mockMvc.perform(get("/v1/users/20/reminders"))
    //			            .andExpect(status().isOk())
    //			          ;
    //		} catch (Exception e) {
    //			// TODO Auto-generated catch block
    //			e.printStackTrace();
    //		}
    //	}

    @Test
    public void zzz_rate_limiter_get_reminders_test() throws Exception {

        int requestCount = RequestMonitor.getRequestMonitorInstance().getMaxRequestCount();
        System.out.println("f requestCount: " + requestCount);
        RequestMonitor.getRequestMonitorInstance().setMaxRequestCount(1);

        wireMockRule.stubFor(any(urlPathEqualTo("/"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(
                    "{" +
                    "\"notificationsList\": [{" +
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
                    "\"error\" : \"\"" +
                    "}")
                //.withStatus(201)
            ));


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

        this.mockMvc.perform(get("/v1/users/20/reminders"))
            .andExpect(status().isTooManyRequests());



        RequestMonitor.getRequestMonitorInstance().setMaxRequestCount(requestCount);
        requestCount = RequestMonitor.getRequestMonitorInstance().getMaxRequestCount();

    }



    @Test
    public void test_created_successful_reminder_post() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(
                    "{" +
                    "\"notificationId\" : 23," +
                    "\"error\" : \"\"" +
                    "}")

            ));


        this.mockMvc.perform(post("/v1/users/2/reminders").contentType(MediaType.APPLICATION_JSON_UTF8).content("{" +
                "\"text\":\"Wake me up\"," +
                "\"time\":1231245," +
                "\"repeatable\":\"true\"," +
                "\"interval\":300" +
                "}"))
            .andExpect(status().isCreated());


    }

    @Test
    public void test_injection_prevention_reminder_post() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(
                    "{" +
                    "\"notificationId\" : 23," +
                    "\"error\" : \"\"" +
                    "}")

            ));


        this.mockMvc.perform(post("/v1/users/2/reminders").contentType(MediaType.APPLICATION_JSON_UTF8).content("{" +
                "\"text\":\"Wake me up'\"," +
                "\"time\":1231245," +
                "\"repeatable\":\"true\"," +
                "\"interval\":300" +
                "}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.text", is("Wake me up''")));


    }
    @Test
    public void test_error_set_reminder_post() throws Exception {


            wireMockRule.stubFor(any(urlPathEqualTo("/"))
                .willReturn(aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        "{" +
                        "\"notificationId\": 23," +
                        "\"error\" : \"Eroare a avut loc Romania\""

                        +
                        "}")
                ));


            this.mockMvc.perform(post("/v1/users/2/reminders").contentType(MediaType.APPLICATION_JSON_UTF8).content("{" +
                    "\"text\":\"Wake me up\"," +
                    "\"time\": 123456," +
                    "\"repeatable\":\"true\"," +
                    "\"interval\":300" +
                    "}"))
                .andExpect(status().isUnprocessableEntity());

        }
        //	@Test
        //	public void test_missing_field_reminder_post() {
        //		
        //		wireMockRule.stubFor(any(urlPathEqualTo("/"))
        //                .willReturn(aResponse()
        //                .withHeader("Content-Type", "application/json")
        //				.withBody(
        //					 "{"
        //						   + "\"notificationId\" :2,"
        //						   + "\"error\" : \"Eroare\""
        //					
        //					+ "}")
        //				));
        //		
        //		try {
        //			this.mockMvc.perform(post("/v1/users/2/reminders").contentType(MediaType.APPLICATION_JSON_UTF8).content("{"
        //					+ "\"text\":\"Wake me up\","
        //					+ "\"repeatable\":\"true\","
        //					+ "\"interval\":300"
        //					+ "}"))
        //			            .andExpect(status().isBadRequest())
        //			            
        //			            ;
        //		} catch (Exception e) {
        //			// TODO Auto-generated catch block
        //			e.printStackTrace();
        //		}
        //	}

    @Test
    public void test_internal_server_error_reminder_post() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(
                    "{" +
                    "\"idf\" :12," +
                    "\"errobr\" : \"Eroare\""

                    +
                    "}")
            ));


        this.mockMvc.perform(post("/v1/users/2/reminders").contentType(MediaType.APPLICATION_JSON_UTF8).content("{" +
                "\"text\":\"Wake me up\"," +
                "\"time\":1231245," +
                "\"repeatable\":\"true\"," +
                "\"interval\":300" +
                "}"))
            .andExpect(status().isInternalServerError())

        ;

    }
    @Test
    public void test_internal_server_error_reminder_post_idmissing() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(
                    "{" +
                    "\"id\" :\"\"," +
                    "\"error\" : \"\""

                    +
                    "}")
            ));


        this.mockMvc.perform(post("/v1/users/2/reminders").contentType(MediaType.APPLICATION_JSON_UTF8).content("{" +
                "\"text\":\"Wake me up\"," +
                "\"time\":1231245," +
                "\"repeatable\":\"true\"," +
                "\"interval\":300" +
                "}"))
            .andExpect(status().isInternalServerError())

        ;

    }
    @Test
    public void test_service_unavaible_reminder_post() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/")));


        this.mockMvc.perform(post("/v1/users/2/reminders").contentType(MediaType.APPLICATION_JSON_UTF8).content("{" +
                "\"text\":\"Wake me up\"," +
                "\"time\":1231245," +
                "\"repeatable\":\"true\"," +
                "\"interval\":300" +
                "}"))
            .andExpect(status().isServiceUnavailable())

        ;

    }
    @Test
    public void test_service_unavailable_delete() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/")));


        this.mockMvc.perform(delete("/v1/users/2/reminders/23"))
            .andExpect(status().isServiceUnavailable());


    }

    @Test
    public void test_delete_reminder_successful() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("{" +
                    "\"error\" : \"\"" +
                    "}"
                )
            ));

        this.mockMvc.perform(delete("/v1/users/2/reminders/23"))
            .andExpect(status().isOk());

    }
    @Test
    public void test_unprocessable_entity_delete() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("{" +
                    "\"error\" : \"ceva in error\"" +
                    "}"
                )
            ));

        this.mockMvc.perform(delete("/v1/users/2/reminders/23"))
            .andExpect(status().isUnprocessableEntity());

    }
    @Test
    public void test_internal_server_error_delete() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("{" +
                    "\"eror\" : \"\"" +
                    "}"
                )
            ));

        this.mockMvc.perform(delete("/v1/users/2/reminders/23"))
            .andExpect(status().isInternalServerError())

        ;

    }
    @Test
    public void test_successful_get() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(
                    "{" +
                    "\"notificationsList\": [{" +
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
                    "\"error\" : \"\"" +
                    "}")
                //.withStatus(201)
            ));


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


    }

    @Test
    public void test_unprocessable_entity_get() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(
                    "{" +
                    "\"notificationsList\": [{" +
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
                    "\"error\" : \"Whatever error string\"" +
                    "}")
                //.withStatus(201)
            ));


        this.mockMvc.perform(get("/v1/users/20/reminders"))
            .andExpect(status().is(422));


    }

    @Test
    public void test_internal_server_error_get() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(
                    "{" +
                    "\"notificationsList\": [{" +
                    "\"id\": 23," +
                    "\"ttext\": \"Get the kid\"," +
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
                    "\"error\" : \"\"" +
                    "}")
                //.withStatus(201)
            ));


        this.mockMvc.perform(get("/v1/users/20/reminders"))
            .andExpect(status().is(500));


    }
    @Test
    public void test_internal_server_error_get_errornull() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(
                    "{"

                    +
                    "}")
                //.withStatus(201)
            ));


        this.mockMvc.perform(get("/v1/users/20/reminders"))
            .andExpect(status().is(500));


    }

    @Test
    public void test_service_unavailable_get() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/")));


        this.mockMvc.perform(get("/v1/users/20/reminders"))
            .andExpect(status().is(503));


    }
    @Test
    public void test_input_from_frontend_too_large() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(
                    "{" +
                    "\"notificationId\" : 23," +
                    "\"error\" : \"\"" +
                    "}")

            ));


        this.mockMvc.perform(post("/v1/users/2/reminders").contentType(MediaType.APPLICATION_JSON_UTF8).content("{" +
                "\"text\":\"WakewakeWakewWakewakeewakeWakewakeWakewakeWakewawakeewakeWakewakeWWakewakeWakewWakewakeewakeWakewakeWakewakeWakewawakeewakeWakewakeWakWakewakeWakewWakewakeewakeWakewakeWakewakeWakewawakeewakeWakewakeWakak" +
                "ewakeWakewawakeewakeWakewakeWakewakeWakewawakeewaWakewawakeewakeWakewakeWakewakeWakewawakeewakeWakewakeWakewakeWakewawakeWakewawakeewakeWakewakeWakewakeWakewawakeewakeWakewakeWakewakeWakewawakeWakewawakeewakeWakewakeWakewakeWakewawakeewakeWakewakeWakewakeWakewawakekeWa" +
                "kewakeWakewakeWakewawakeewakeWakewakeWakewakeWakewawakeewakeWakewakeWakewakeWakewawakeewakeWakewWakewakeWakewWakewakeewakeWakewakeWakewakeWakewawakeewakeWakewakeWakakeWakewakeWakewa" +
                "wakeewakeWakewakeWakewakeWakewawakeewakeWakewakeWakewakeWakewawakeewakeWakewakeWakewakeWakewawakeewakeWakewakeWakewakeWakewawakeewakeWakewakeWakewakeWakewawakeewakeWakewakeWakewakeWakewawakeewakeWakewakeWakewakeWakewawakeewake" +
                "WakewakeWakewakeWakewawakeewakeWakewakeWakewakeWakewawakeewakeWakewakeWakewakeWakewawakeewakeWakewakeWakewakeWakewawakeewakeWakewakeWakewakeWakewawakeewakeWakewakeWakewakeWakewawakeewakeWakewakeWakewakeWakewawakeewakeWakewakeWakewakeWakewawakeewakeWakewakeWakewakeWakewakeWakewake\"," +
                "\"time\":1231245," +
                "\"repeatable\":\"true\"," +
                "\"interval\":300" +
                "}"))
            .andExpect(status().isBadRequest());


    }

    @Test
    public void test_unknown_server_error_post_reminders() throws Exception {

        this.controllers.setBackEndUrlPath("asfsagrarrae");


        this.mockMvc.perform(post("/v1/users/2/reminders").contentType(MediaType.APPLICATION_JSON_UTF8).content("{" +
                "\"text\":\"Wake me up\"," +
                "\"time\":1231245," +
                "\"repeatable\":\"true\"," +
                "\"interval\":300" +
                "}"))
            .andExpect(status().is(500));

    }
    @Test
    public void test_rest_client_exception_post_reminders() throws Exception {

        this.controllers.setBackEndUrlPath("http://www.thislink.com");


        this.mockMvc.perform(post("/v1/users/2/reminders").contentType(MediaType.APPLICATION_JSON_UTF8).content("{" +
                "\"text\":\"Wake me up\"," +
                "\"time\":1231245," +
                "\"repeatable\":\"true\"," +
                "\"interval\":300" +
                "}"))
            .andExpect(status().is(503));

    }

    @Test
    public void test_unknown_server_error_get_reminders() throws Exception {

        this.controllers.setBackEndUrlPath("asfsagrarrae");


        this.mockMvc.perform(get("/v1/users/20/reminders"))
            .andExpect(status().is(500));

    }
    @Test
    public void test_rest_client_exception_get_reminders() throws Exception {

        this.controllers.setBackEndUrlPath("http://www.thislink.com");


        this.mockMvc.perform(get("/v1/users/20/reminders"))
            .andExpect(status().is(503));

    }

    @Test
    public void test_unknown_server_error_delete_reminders() throws Exception {

        this.controllers.setBackEndUrlPath("asfsagrarrae");


        this.mockMvc.perform(delete("/v1/users/2/reminders/23"))
            .andExpect(status().is(500));

    }
    @Test
    public void test_rest_client_exception_delete_reminders() throws Exception {

        this.controllers.setBackEndUrlPath("http://www.thislink.com");


        this.mockMvc.perform(delete("/v1/users/2/reminders/23"))
            .andExpect(status().is(503));

    }

    @Test
    public void test_missing_interval_field_post_reminders() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(
                    "{" +
                    "\"notificationId\" : 23," +
                    "\"error\" : \"\"" +
                    "}")

            ));


        this.mockMvc.perform(post("/v1/users/2/reminders").contentType(MediaType.APPLICATION_JSON_UTF8).content("{" +
                "\"text\":\"Wake me up\"," +
                "\"time\":1231245," +
                "\"repeatable\":\"true\"" +
                "}"))
            .andExpect(status().is(400));

    }

    @Test
    public void test_missing_text_field_post_reminders() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(
                    "{" +
                    "\"notificationId\" : 23," +
                    "\"error\" : \"\"" +
                    "}")

            ));


        this.mockMvc.perform(post("/v1/users/2/reminders").contentType(MediaType.APPLICATION_JSON_UTF8).content("{" +
                "\"time\":1231245," +
                "\"repeatable\":\"true\"," +
                "\"interval\":300" +
                "}"))
            .andExpect(status().is(400));

    }

    @Test
    public void test_missing_time_field_post_reminders() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(
                    "{" +
                    "\"notificationId\" : 23," +
                    "\"error\" : \"\"" +
                    "}")

            ));


        this.mockMvc.perform(post("/v1/users/2/reminders").contentType(MediaType.APPLICATION_JSON_UTF8).content("{" +
                "\"text\":\"Wake me up\"," +
                "\"repeatable\":\"true\"," +
                "\"interval\":300" +
                "}"))
            .andExpect(status().is(400));

    }

    @Test
    public void test_missing_repeatable_field_post_reminders() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(
                    "{" +
                    "\"notificationId\" : 23," +
                    "\"error\" : \"\"" +
                    "}")

            ));


        this.mockMvc.perform(post("/v1/users/2/reminders").contentType(MediaType.APPLICATION_JSON_UTF8).content("{" +
                "\"text\":\"Wake me up\"," +
                "\"time\":1231245," +
                "\"interval\":300" +
                "}"))
            .andExpect(status().is(400));

    }
    
    @Test
    public void test_bad_request_post_reminders() throws Exception {
    	

        this.mockMvc.perform(post("/v1/users/2/reminders").contentType(MediaType.APPLICATION_JSON_UTF8).content("{" +
                "\"texnbgjkgvmt\":\"Wake me up\"," +
                "\"tijgkjkhme\":1vbnmvb231245," +
                "\"repbvnmeatable\":\"trdfghue\"," +
                "\"ibvnmgjknterval\":3gjkh00"+
                "dsfgsdfgsdhbgvnc"+
                "}"))
            .andExpect(status().isBadRequest());

}
    
}
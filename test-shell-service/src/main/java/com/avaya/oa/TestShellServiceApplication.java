package com.avaya.oa;

import java.io.IOException;
import java.util.TimeZone;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages={"com.avaya.oa"})
@SpringBootApplication
public class TestShellServiceApplication {
	
	private static final Logger logger = LoggerFactory.getLogger(TestShellServiceApplication.class);
	
	@Value("${home.directory}")
	static String homeDirectory;
	
	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC")); 
		SpringApplication.run(TestShellServiceApplication.class, args);
		
		logger.info("Step - 0 ");
		
		Process process ;
		try {
			  process = Runtime.getRuntime()
			  .exec(String.format("sh -c ls %s", homeDirectory));
			  
			  logger.info("Step - 1 - {}",String.format("sh -c ls %s", homeDirectory) );
			  
			  StreamGobbler streamGobbler = 
					  new StreamGobbler(process.getInputStream(), System.out::println);
			  
					Executors.newSingleThreadExecutor().submit(streamGobbler);
					
					int exitCode = process.waitFor();
					assert exitCode == 0;
					
					logger.info("Step - 2 - ");
					
		} catch (IOException e) {
			logger.error("Process can not be instantiated IO Exception ", e );
		} catch (InterruptedException e) {
			logger.error("Process executer interrupted Exception ", e );
		}
		
		
				
		
		
	}
	
}

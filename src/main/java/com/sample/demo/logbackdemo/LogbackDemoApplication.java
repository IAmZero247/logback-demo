package com.sample.demo.logbackdemo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import ch.qos.logback.classic.LoggerContext;
import com.sample.demo.logbackdemo.component.TestComponent1;
import com.sample.demo.logbackdemo.component.TestComponent2;
import org.springframework.core.env.Environment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class LogbackDemoApplication {
	private static final Logger log = LoggerFactory.getLogger(LogbackDemoApplication.class);

	@Autowired
	private static Environment environment;

	public static void main(String[] args) throws Exception {
		ApplicationContext context = SpringApplication.run(LogbackDemoApplication.class, args);

		Integer val1 = Integer.valueOf(System.getenv("PARAM1"));
		Integer val2 = Integer.valueOf(System.getenv("PARAM2"));


		if (val1 == null  || val2 == null ){
			throw  new Exception( "Param1 or Param2 is not defined in environment");
		}
		ExecutorService executorService = null;
		TestComponent1 testCom1 = context.getBean(TestComponent1.class);
		TestComponent2 testCom2 = context.getBean(TestComponent2.class);
		//testCom1.processStep(val1);
		//testCom2.processStep(val2);
		Runnable runnable1 = () -> {
			System.err.println("started thread1");
			testCom1.processStep(val1);
			System.err.println("ended thread1");

		};
		Runnable runnable2 = () -> {
			System.err.println("started thread2");
			testCom2.processStep(val2);
			System.err.println("ended thread2");
		};

		try{
			executorService = Executors.newFixedThreadPool(2);
			executorService.submit(runnable1);
			executorService.submit(runnable2);
		}catch (Exception e ){
			log.info(e.getMessage());
		}

		finally{
			executorService.shutdown();
		}

		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
		loggerContext.stop();
	}

}

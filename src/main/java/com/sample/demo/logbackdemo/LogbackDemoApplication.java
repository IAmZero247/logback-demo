package com.sample.demo.logbackdemo;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import ch.qos.logback.classic.LoggerContext;
import com.sample.demo.logbackdemo.component.TestComponent1;
import com.sample.demo.logbackdemo.component.TestComponent2;

@SpringBootApplication
public class LogbackDemoApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(LogbackDemoApplication.class, args);
		TestComponent1 testCom1 = context.getBean(TestComponent1.class);
		testCom1.processStep(100);

		TestComponent2 testCom2 = context.getBean(TestComponent2.class);
		testCom2.processStep(1000);

		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
		loggerContext.stop();
	}

}

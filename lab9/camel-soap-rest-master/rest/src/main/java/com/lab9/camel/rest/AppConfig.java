package com.lab9.camel.rest;

import org.apache.camel.spring.boot.CamelSpringBootApplicationController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = "com.lab9.camel.rest")
public class AppConfig {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(AppConfig.class, args);
        CamelSpringBootApplicationController bean = run.getBean(CamelSpringBootApplicationController.class);
        bean.run();
    }
}

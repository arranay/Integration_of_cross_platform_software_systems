package com.lab9.camel.soap;

import org.apache.camel.spring.boot.CamelSpringBootApplicationController;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.lab9.camel.soap")
public class AppConfig {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(AppConfig.class, args);
        CamelSpringBootApplicationController bean = run.getBean(CamelSpringBootApplicationController.class);
        bean.run();
    }

    @Bean
    public ServletRegistrationBean dispatcherServlet(){
        return new ServletRegistrationBean(new CXFServlet(), "/webservices/*");
    }

}

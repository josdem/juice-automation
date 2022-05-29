package com.josdem.jugoterapia.automation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = "com.josdem.jugoterapia.webclient")
public class JuiceAutomationApplication {

    public static void main(String[] args) {
        SpringApplication.run(JuiceAutomationApplication.class, args);
    }

}

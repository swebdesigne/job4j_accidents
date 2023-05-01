package ru;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Run {
    public static void main(String[] args) {
        BasicConfigurator.configure();
        SpringApplication.run(Run.class, args);
        System.out.println("http://localhost:8080");
    }
}

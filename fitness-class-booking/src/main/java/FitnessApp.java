package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"controller", "dto", "main.java"})
public class FitnessApp {
    public static void main(String[] args) {
        SpringApplication.run(FitnessApp.class, args);
    }
}
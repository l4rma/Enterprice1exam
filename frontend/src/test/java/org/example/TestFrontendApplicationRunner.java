package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * Created by arcuri82
 * https://github.com/arcuri82/testing_security_development_enterprise_systems
 */

@SpringBootApplication
public class TestFrontendApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(TestFrontendApplicationRunner.class, "--spring.profiles.active=test");
    }
}

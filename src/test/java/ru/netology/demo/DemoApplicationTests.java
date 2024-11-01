package ru.netology.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;
    public static GenericContainer<?> devApp = new GenericContainer<>("devapp").withExposedPorts(8080);
    public static GenericContainer<?> prodApp = new GenericContainer<>("prodapp").withExposedPorts(8081);

    @BeforeAll
    public static void setUp() {
        devApp.start();
        prodApp.start();
    }

    @Test
    void contextLoads() {
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://localhost:" + myapp.getMappedPort(8080),
                String.class);
        System.out.println(forEntity.getBody());
    }

    @Test
    void profileProd() {
        ResponseEntity<String> prodForEntity = restTemplate
                .getForEntity("http://localhost:" + prodApp.getMappedPort(8081) + "/profile", String.class);
        Assertions.assertEquals("Current profile is production", prodForEntity.getBody());
    }

}
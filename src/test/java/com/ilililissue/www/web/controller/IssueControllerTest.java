package com.ilililissue.www.web.controller;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "application-test.properties", webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IssueControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void indexTest() {
        String url = "/api/v1/index";
        String body = restTemplate.getForObject(url, String.class);
        assertEquals("index", body);
    }

}

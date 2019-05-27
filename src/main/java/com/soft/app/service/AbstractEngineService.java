package com.soft.app.service;

import com.soft.app.Application;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public abstract class AbstractEngineService {
    private static final Logger LOGGER = LogManager.getLogger(AbstractEngineService.class);

    protected String EngineServer;

    @Autowired
    RestTemplate restTemplate;

    public ResponseEntity<String> getResultString(String urlParam) {
        String url = this.EngineServer + urlParam;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.add("Content-Type", "application/json; charset=utf-8");
            HttpEntity<String> entity = new HttpEntity<String>("", headers);
            return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        } catch (Exception e) {
            LOGGER.error("error : {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public String postWithJson(String urlParam, String json) {
        String url = this.EngineServer + urlParam;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.add("Content-Type", "application/json; charset=utf-8");
            HttpEntity<String> entity = new HttpEntity<String>(json, headers);
            ResponseEntity<String> data= restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            return data.getBody();
        } catch (Exception e) {
            LOGGER.error("error : {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

}

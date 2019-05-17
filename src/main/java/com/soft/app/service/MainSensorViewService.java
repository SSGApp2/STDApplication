package com.soft.app.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MainSensorViewService extends AbstractEngineService {

    public ResponseEntity<String> example() {
        return getResultString("/mainsensorviews/example");
    }
}

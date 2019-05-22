package com.soft.app.service;

import com.soft.app.constant.ServerConstant;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MainSensorViewService extends AbstractEngineService {

    public ResponseEntity<String> example() {
        this.EngineServer = ServerConstant.VCCJobEngine+"/api";
        return getResultString("/mainsensorviews/example");
    }



    public ResponseEntity<String> findByCriteria(String json) {
        this.EngineServer = ServerConstant.VCCJobEngine+"/api";
        return postWithJson("/mainsensorviews/findByCriteria",json);
    }
}

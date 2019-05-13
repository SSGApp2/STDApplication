package com.soft.app.controller;

import com.soft.app.entity.app.ParameterHeader;
import com.soft.app.repository.ParameterHeaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
public class SwaggerController {

    @Autowired
    private ParameterHeaderRepository parameterHeaderRepository;

    @GetMapping
    public List<ParameterHeader> getAllAppParameter(){
        return parameterHeaderRepository.findAll();
    }
}

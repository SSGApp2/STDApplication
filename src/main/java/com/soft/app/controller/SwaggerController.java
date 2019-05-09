package com.soft.app.controller;

import com.soft.app.entity.app.AppParameter;
import com.soft.app.repository.AppParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
public class SwaggerController {

    @Autowired
    private AppParameterRepository appParameterRepository;

    @GetMapping
    public List<AppParameter> getAllAppParameter(){
        return appParameterRepository.findAll();
    }
}

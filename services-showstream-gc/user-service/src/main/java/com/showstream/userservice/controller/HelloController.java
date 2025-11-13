package com.showstream.userservice.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {


    @GetMapping
    public String getStatus(HttpServletRequest  request)
    {
        return "Welcom Server is running "+request.getSession().getId() ;
    }
}

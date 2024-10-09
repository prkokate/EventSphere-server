package com.esphere.EventSphere.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeController {

    @GetMapping("/")
    public String home(){
        System.out.println("Hello");
        return "Welcome to Event Sphere sever";
    }
}

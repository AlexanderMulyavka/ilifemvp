package ru.mts.ilifeweb.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IlifeController {
    @RequestMapping("/greeting")
    public String sayHello(){
        return "HELLO BABY";
    }
}

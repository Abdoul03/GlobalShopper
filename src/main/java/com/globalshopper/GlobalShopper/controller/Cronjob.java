package com.globalshopper.GlobalShopper.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/about")
public class Cronjob {
    @GetMapping
    public String sayHelloToRender() {
        return "Bonjour bienvenue sur GlobalShopper une application de commande collaboratife";
    }
}

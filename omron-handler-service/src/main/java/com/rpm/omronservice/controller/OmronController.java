package com.rpm.omronservice.controller;


import com.rpm.model.Omron;
import com.rpm.omronservice.service.OmronService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OmronController {

    private final OmronService omronService;

    public OmronController(OmronService omronService) {
        this.omronService = omronService;
    }
        @PostMapping("/postItem")
        public String sendVital(@RequestBody Omron omron){
            omronService.sendVital(omron);
            return "Message published successfully";
        }
}

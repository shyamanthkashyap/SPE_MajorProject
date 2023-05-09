package com.example.project.controller;

import com.example.project.entity.Location;
import com.example.project.entity.response.ResponseEntity;
import com.example.project.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping("/state/{country}")
    ResponseEntity<List<String>> getState(@PathVariable String country){
        return new ResponseEntity<>(HttpStatus.OK.value(), locationService.getStates(country));
    }

    @GetMapping("/country")
    ResponseEntity<List<String>> getCountry(){
        return new ResponseEntity<>(HttpStatus.OK.value(), locationService.getCountry());
    }

    @GetMapping("/city/{country}/{state}")
    ResponseEntity<List<Location>> getCountry(@PathVariable String country, @PathVariable String state){
        return new ResponseEntity<>(HttpStatus.OK.value(), locationService.getCities(country, state));
    }

}

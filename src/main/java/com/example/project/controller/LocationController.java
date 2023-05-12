package com.example.project.controller;

import com.example.project.entity.Location;
import com.example.project.entity.response.ResponseEntity;
import com.example.project.service.LocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/location")
public class LocationController {

    private static final Logger logger = LoggerFactory.getLogger(LocationController.class);

    @Autowired
    private LocationService locationService;

    @GetMapping("/state/{country}")
    ResponseEntity<List<String>> getState(@PathVariable String country) throws Exception {
        try {
            logger.info("Executing getState Endpoint",
                    "method", "GET",
                    "path", "/state/{country}",
                    "status", HttpStatus.OK.value()
            );
            return new ResponseEntity<>(HttpStatus.OK.value(), locationService.getStates(country));
        }
        catch(Exception e){
            logger.error("Error Executing getState","status","ERROR","Message",e.getMessage(),"Stacktrace",e.getStackTrace());
            throw new Exception();
        }
    }

    @GetMapping("/country")
    ResponseEntity<List<String>> getCountry() throws Exception {
        try {
            logger.info("Executing getCountry Endpoint",
                    "method", "GET",
                    "path", "/country",
                    "status", HttpStatus.OK.value()
            );
            return new ResponseEntity<>(HttpStatus.OK.value(), locationService.getCountry());
        }
        catch(Exception e){
            logger.error("Error Executing getCountry","status","ERROR","Message",e.getMessage(),"Stacktrace",e.getStackTrace());
            throw new Exception();
        }
    }

    @GetMapping("/city/{country}/{state}")
    ResponseEntity<List<Location>> getCities(@PathVariable String country, @PathVariable String state) throws Exception{
        try {
            logger.info("Executing getCities Endpoint",
                    "method", "GET",
                    "path", "/city/{country}/{state}",
                    "status", HttpStatus.OK.value()
            );
            return new ResponseEntity<>(HttpStatus.OK.value(), locationService.getCities(country, state));
        }
        catch(Exception e){
            logger.error("Error Executing getCities","status","ERROR","Message",e.getMessage(),"Stacktrace",e.getStackTrace());
            throw new Exception();
        }
    }

}

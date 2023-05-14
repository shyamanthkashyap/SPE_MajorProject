package com.example.project.Controller;

import com.example.project.Entity.Location;
import com.example.project.Entity.Response.ResponseEntity;
import com.example.project.Service.LocationService;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/location")
public class LocationController {

    private static final Logger logger = LogManager.getLogger(LocationController.class);

    @Autowired
    private LocationService locationService;

    @GetMapping("/state/{country}")
    ResponseEntity<List<String>> getState(@PathVariable String country) throws Exception {
        StringBuilder reqMessage = new StringBuilder();
        reqMessage.append("Message = \"Executing getState Endpoint\",");
        reqMessage.append("method = [GET],");
        reqMessage.append("path = [/api/location/state/{country}],");
        reqMessage.append("status = "+HttpStatus.OK.value());
        try {
            logger.info(reqMessage);
            return new ResponseEntity<>(HttpStatus.OK.value(), locationService.getStates(country));
        }
        catch(Exception e){
            StringBuilder errMessage = new StringBuilder();
            errMessage.append("Message = \"Error Executing getState\",");
            errMessage.append("method = [GET],");
            errMessage.append("path = [/api/location/state/{country}],");
            errMessage.append("status = "+500);
            errMessage.append(",ExceptionMessage = "+e.getMessage());
            logger.error(errMessage);
            throw new Exception();
        }
    }

    @GetMapping("/country")
    ResponseEntity<List<String>> getCountry() throws Exception {
        StringBuilder reqMessage = new StringBuilder();
        reqMessage.append("Message = \"Executing getCountry Endpoint\",");
        reqMessage.append("method = [GET],");
        reqMessage.append("path = [/api/location/country],");
        reqMessage.append("status = "+HttpStatus.OK.value());
        try {
            logger.info(reqMessage);
            return new ResponseEntity<>(HttpStatus.OK.value(), locationService.getCountry());
        }
        catch(Exception e){
            StringBuilder errMessage = new StringBuilder();
            errMessage.append("Message = \"Error Executing getCountry\",");
            errMessage.append("method = [GET],");
            errMessage.append("path = [/api/location/country],");
            errMessage.append("status = "+500);
            errMessage.append("ExceptionMessage = "+e.getMessage());
            logger.error(errMessage);
            throw new Exception();
        }
    }

    @GetMapping("/city/{country}/{state}")
    ResponseEntity<List<Location>> getCities(@PathVariable String country, @PathVariable String state) throws Exception{
        StringBuilder reqMessage = new StringBuilder();
        reqMessage.append("Message = \"Executing getCities Endpoint\",");
        reqMessage.append("method = [GET],");
        reqMessage.append("path = [/api/location/city/{country}/{state}],");
        reqMessage.append("status = "+HttpStatus.OK.value());
        try {
            logger.info(reqMessage);
            return new ResponseEntity<>(HttpStatus.OK.value(), locationService.getCities(country, state));
        }
        catch(Exception e){
            StringBuilder errMessage = new StringBuilder();
            errMessage.append("Message = \"Error Executing getCities\",");
            errMessage.append("method = [GET],");
            errMessage.append("path = [/api/location/city/{country}/{state}],");
            errMessage.append("status = "+500);
            errMessage.append(",ExceptionMessage = "+e.getMessage());
            logger.error(errMessage);
            throw new Exception();
        }
    }

}

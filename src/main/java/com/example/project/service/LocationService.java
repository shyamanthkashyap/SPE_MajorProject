package com.example.project.service;

import com.example.project.entity.Location;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LocationService {
    List<String> getStates(String country);

    List<String> getCountry();

    List<Location> getCities(String country, String state);

    Location getLocation(Long locationId);
}

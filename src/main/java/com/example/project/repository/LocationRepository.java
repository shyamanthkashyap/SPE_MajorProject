package com.example.project.repository;

import com.example.project.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    @Query(value = "select distinct state from Location where country = ?1")
    List<String> findDistinctByCountry(String country);

    @Query(value = "select country from Location group by country")
    List<String> findDistinctCountries();

    List<Location> findLocationByCountryAndState(String country, String state);
}

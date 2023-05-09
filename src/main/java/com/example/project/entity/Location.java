package com.example.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "location")
// @JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "city",  columnDefinition = "varchar(21) not null")
    private String city;

    @Column(name = "state",  columnDefinition = "varchar(21) not null")
    private String state;

    @Column(name = "country",  columnDefinition = "varchar(21) not null")
    private String country;


}

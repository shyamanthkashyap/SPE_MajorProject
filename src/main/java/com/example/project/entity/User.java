package com.example.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Reference;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username", columnDefinition = "varchar(21) not null")
    private String username;

    @Column(name = "pwd", columnDefinition = "varchar(32) not null")
    private String pwd;

    @Column(name = "profile", columnDefinition = "text")
    private String profile;

    @Column(name = "points", columnDefinition = "integer default 0")
    private long points;

    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "location_id", insertable = true, updatable = false)
    private Location location;




}

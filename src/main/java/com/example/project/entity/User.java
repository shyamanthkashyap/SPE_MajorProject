package com.example.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "username")
    private String username;

    @Column(name = "pwd")
    private String pwd;

    @Column(name = "profile")
    private String profile;

    @Column(name = "points")
    private long points;

    @Column(name="email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "location_id", insertable = true, updatable = false)
    private Location location;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User(String userName, String password, String email) {
        this.username = userName;
        this.pwd = password;
        this.profile=null;
        this.points=0;
        this.location=null;
        this.email=email;
    }

}

package com.example.project.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "thumbs")
public class Thumbs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long thumbId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = true, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "answer_id" ,referencedColumnName = "answer_id", insertable = true, updatable = false)
    private Answers answers;
}

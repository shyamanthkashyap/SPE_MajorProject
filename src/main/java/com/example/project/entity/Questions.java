package com.example.project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "questions")
public class Questions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long questionId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = true, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "sub_category_id", referencedColumnName = "sub_category_id", insertable = true, updatable = false)
    private SubCategory subCategory;

    @Column(name = "title", columnDefinition = "varchar(100) not null")
    private String title;

    @Column(name = "body", columnDefinition = "text not null")
    private String body;

    @Column(name ="post_time", columnDefinition = "datetime not null")
    private java.sql.Timestamp postTime;


}

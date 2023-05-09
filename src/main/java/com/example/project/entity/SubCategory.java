package com.example.project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sub_category")
public class SubCategory implements Serializable {

  @Id
  @Column(name = "sub_category_id")
  private Long subCategoryId;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "category_id", referencedColumnName = "category_id", insertable = true, updatable = false)
  private MainCategory mainCategory;

  @Column(name = "sub_category_name")
  private String subCategoryName;


}

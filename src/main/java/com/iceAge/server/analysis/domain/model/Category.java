package com.iceAge.server.analysis.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String largeCategory;

  @Column
  private String name;

  @Column
  private String detailName;

  @Column(columnDefinition = "TEXT")
  private String example;
}

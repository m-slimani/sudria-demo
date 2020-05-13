package com.sudria.demo.infrastructure;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="ANIMAL_ENTITY")
public class AnimalEntity {

  @Id
  @GeneratedValue
  private Long id;
  @Column(name = "NAME", length = 50, nullable = false)
  private String name;
  @Column(name = "AGE", nullable = false)
  private int age;
  @Column(name = "CATEGORY", length = 50, nullable = false)
  private String category;

  @OneToMany(mappedBy = "animalEntity", cascade = CascadeType.ALL, orphanRemoval = true , fetch = FetchType.EAGER)
  private List<FoodEntity> foodEntities;

}

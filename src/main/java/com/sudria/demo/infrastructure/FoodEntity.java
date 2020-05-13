package com.sudria.demo.infrastructure;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
@Table(name ="FOOD_ENTITY")
public class FoodEntity {

  @Id
  @GeneratedValue
  private Long id;
  @Column(name = "FREQUENCY", length = 50, nullable = false)
  private int frequency;
  @Column(name = "QUANTITY", nullable = false)
  private int quantity;
  @Column(name = "CATEGORY", length = 50, nullable = false)
  private String category;

  @ManyToOne
  private AnimalEntity animalEntity;
}

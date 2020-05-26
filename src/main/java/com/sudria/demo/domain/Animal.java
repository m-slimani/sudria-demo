package com.sudria.demo.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Animal {

  private Long id;
  private String name;
  private int age;
  private String category;
  private List<Food> foods;

  @Getter
  @ToString
  @EqualsAndHashCode
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Food {

    private Long id;
    private int frequency;
    private int quantity;
    private String category;

  }

}

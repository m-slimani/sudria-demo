package com.sudria.demo.application.rappels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {

  private int numberOfPages;
  private String title;

  @Override
  public String toString() {
    return "Book{" +
        "numberOfPages=" + numberOfPages +
        ", title='" + title + '\'' +
        '}';
  }
}

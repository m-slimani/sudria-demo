package com.sudria.demo.application.rappels;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BookService {

  public static void printBook (){
    log.info(
        Book
            .builder()
            .title("toto")
            .numberOfPages(200)
            .build()
            .toString());
  }

}

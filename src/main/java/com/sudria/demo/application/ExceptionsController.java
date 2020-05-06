package com.sudria.demo.application;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v2")
public class ExceptionsController {

  @RequestMapping(value = "/animals", method = RequestMethod.GET)
  public ResponseEntity<List<String>> getAnimals() throws Exception {
    throw new Exception("Internal server error");
  }


  @RequestMapping(value = "/animals/{id}", method = RequestMethod.PUT)
  public ResponseEntity<String> replaceAnimals(
      @PathVariable(value = "id") Long id,
      @RequestBody String animalName) {
    log.info("animal with id :" + id + " is now :" + animalName);
    return new ResponseEntity<>(animalName, HttpStatus.BAD_REQUEST);
  }

  @RequestMapping(value = "/animals/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<String> deleteAnimals(@PathVariable(value = "id") Long id) {
    log.info("animal with id :" + id + " is deleted");
    return new ResponseEntity<>(HttpStatus.OK);
  }

}

package com.sudria.demo.application;

import java.util.Arrays;
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
@RequestMapping("/api/v1")
public class Controller {

  public Controller() {
  }

  @RequestMapping(value = "/animals", method = RequestMethod.GET)
  public ResponseEntity<List<String>> getAnimals() {
    List<String> animals = Arrays.asList("cat", "dog", "turtle");
    return new ResponseEntity<>(animals, HttpStatus.OK);
  }

  @RequestMapping(value = "/animals", method = RequestMethod.POST)
  public ResponseEntity<String> createAnimals(
      @RequestBody String animalName) {
    log.info("animal created :" + animalName);
    return new ResponseEntity<>(animalName, HttpStatus.CREATED);
  }

  @RequestMapping(value = "/animals/{id}", method = RequestMethod.PUT)
  public ResponseEntity<String> replaceAnimals(
      @PathVariable(value = "id") Long id,
      @RequestBody String animalName) {
    log.info("animal with id :" + id + " is now :" + animalName);
    return new ResponseEntity<>(animalName, HttpStatus.OK);
  }

  @RequestMapping(value = "/animals/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<String> deleteAnimals(@PathVariable(value = "id") Long id) {
    log.info("animal with id :" + id + " is deleted");
    return new ResponseEntity<>(HttpStatus.OK);
  }

}

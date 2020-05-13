package com.sudria.demo.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.sudria.demo.domain.Animal;
import com.sudria.demo.domain.AnimalService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class Controller {

  private AnimalService animalService;
  private ObjectMapper objectMapper;

  public Controller(AnimalService animalService, ObjectMapper objectMapper) {
    this.animalService = animalService;
    this.objectMapper = objectMapper;
  }

  @RequestMapping(value = "/animals", method = RequestMethod.GET)
  public ResponseEntity<List<Animal>> getAnimals() {
    return new ResponseEntity<>(animalService.getAnimals(), HttpStatus.OK);
  }

  @RequestMapping(value = "/animals/{id}", method = RequestMethod.GET)
  public ResponseEntity<Animal> getAnimalsById( @PathVariable(value = "id") Long id) {
    try {
      return new ResponseEntity<>(animalService.getAnimals(id), HttpStatus.OK);
    } catch (NotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Animal Not Found", e);
    }
  }

  @RequestMapping(value = "/animals", method = RequestMethod.POST)
  public ResponseEntity<Animal> createAnimals(
      @RequestBody Animal animal) {
    animal = animalService.addAnimal(animal);
    return new ResponseEntity<>(animal, HttpStatus.CREATED);
  }

  @RequestMapping(value = "/animals/{id}", method = RequestMethod.PUT)
  public ResponseEntity<Animal> replaceAnimals(
      @PathVariable(value = "id") Long id,
      @RequestBody Animal animal) {
    animal.setId(id);
    animalService.replaceAnimal(animal);
    return new ResponseEntity<>(animal, HttpStatus.OK);
  }


  @RequestMapping(value = "/animals/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<Animal> deleteAnimals(@PathVariable(value = "id") Long id) {
    animalService.deleteAnimals(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @RequestMapping(value = "/animals/{id}", method = RequestMethod.PATCH, consumes = "application/json-patch+json")
  public ResponseEntity<String> patchAnimals(
      @PathVariable(value = "id") Long id,
      @RequestBody JsonPatch patch)  {
    try {
      animalService.patchAnimals(applyPatchToCustomer(patch, animalService.findAnimal(id)));
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (NotFoundException e) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    } catch (JsonPatchException | JsonProcessingException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  private Animal applyPatchToCustomer(JsonPatch patch, Animal targetAnimal)
      throws JsonPatchException, JsonProcessingException {
    JsonNode patched = patch.apply(objectMapper.convertValue(targetAnimal, JsonNode.class));
    return objectMapper.treeToValue(patched, Animal.class);
  }
}

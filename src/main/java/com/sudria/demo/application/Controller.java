package com.sudria.demo.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.sudria.demo.domain.Animal;
import com.sudria.demo.domain.AnimalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api("Apis de gestin de zoo")
public class Controller {

  private AnimalService animalService;
  private ObjectMapper objectMapper;

  public Controller(AnimalService animalService, ObjectMapper objectMapper) {
    this.animalService = animalService;
    this.objectMapper = objectMapper;
  }


  @ApiOperation(value = "View a list of available animals", response = List.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully retrieved list"),
      @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
      @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
      @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
  })
  @RequestMapping(value = "/animals", method = RequestMethod.GET)
  public ResponseEntity<List<Animal>> getAnimals() {
    return new ResponseEntity<>(animalService.getAnimals(), HttpStatus.OK);
  }

  @RequestMapping(value = "/animals/{id}", method = RequestMethod.GET)
  public ResponseEntity<Animal> getAnimalsById( @PathVariable(value = "id") Long id) {
    try {
      log.info("********************** inside the controller ****************************");
      return new ResponseEntity<>(animalService.getAnimals(id), HttpStatus.OK);
    } catch (NotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Animal Not Found", e);
    }
  }

  @RequestMapping(value = "/animals", method = RequestMethod.POST)
  public ResponseEntity<Animal> createAnimals(
      @ApiParam(value = "Animal object stored in database table", required = true)
      @RequestBody Animal animal) throws NotFoundException {
    animal = animalService.addAnimal(animal);
    return new ResponseEntity<>(animal, HttpStatus.CREATED);
  }

  @RequestMapping(value = "/animals/{id}", method = RequestMethod.PUT)
  public ResponseEntity<Animal> replaceAnimals(
      @PathVariable(value = "id") Long id,
      @RequestBody Animal animal) {
    animal.setId(id);
    animal = animalService.replaceAnimal(animal);
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

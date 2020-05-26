package com.sudria.demo.application;

import com.sudria.demo.domain.food.Food;
import com.sudria.demo.domain.food.FoodService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class FoodController {

  private FoodService foodService;

  public FoodController(FoodService foodService) {
    this.foodService = foodService;
  }

  @RequestMapping(value = "/animals/{id}/foods", method = RequestMethod.GET)
  @CrossOrigin(origins = "http://localhost:4200")
  public ResponseEntity<List<Food>> getFoods( @PathVariable(value = "id") Long animalId) {
    return new ResponseEntity<List<Food>>(foodService.findFoodsByAnimalId(animalId), HttpStatus.OK);
  }

  @RequestMapping(value = "/animals/{id}/foods", method = RequestMethod.POST)
  public ResponseEntity<Food> createFoods(
      @PathVariable(value = "id") Long animalId,
      @RequestBody Food food)  {
    food = foodService.addFoods(animalId, food);
    return new ResponseEntity<>(food, HttpStatus.CREATED);
  }
}

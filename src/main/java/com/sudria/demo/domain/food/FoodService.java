package com.sudria.demo.domain.food;

import com.sudria.demo.infrastructure.FoodDao;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FoodService {

  private FoodDao foodDao;

  public FoodService(FoodDao foodDao) {
    this.foodDao = foodDao;
  }

  public Food addFoods(Long animalId, Food food) {
    return foodDao.addFoods(animalId, food);
  }

  public List<Food> findFoodsByAnimalId(Long animalId) {
    return foodDao.findFoodsByAnimalId(animalId);
  }

}

package com.sudria.demo.infrastructure;

import com.sudria.demo.domain.food.Food;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.stereotype.Service;

@Service
public class FoodDao {

  private AnimalRepository animalRepository;
  private FoodRepository foodRepository;

  public FoodDao(AnimalRepository animalRepository, FoodRepository foodRepository) {
    this.animalRepository = animalRepository;
    this.foodRepository = foodRepository;
  }

  public Food addFoods(Long animalId, Food food) {
    FoodEntity foodEntity = buildFoodEntity(food, animalRepository.findById(animalId).orElse(null));
    return buildFood(foodRepository.save(foodEntity));
  }

  public List<Food> findFoodsByAnimalId() {
    return StreamSupport.stream(foodRepository.findAll().spliterator(), false)
        .map(foodEntity -> buildFood(foodEntity))
        .collect(Collectors.toList());
  }

  public Food findFoodById(Long animalId) {
    return buildFood(foodRepository.findById(animalId).orElse(null));
  }

  public List<Food> findFoodsByAnimalId(Long animalId) {
    return foodRepository.findByAnimalEntityId(animalId)
        .stream()
        .map(foodEntity -> buildFood(foodEntity))
        .collect(Collectors.toList());
  }

  private FoodEntity buildFoodEntity(Food food, AnimalEntity animalEntity) {
    return FoodEntity.builder()
        .category(food.getCategory())
        .frequency(food.getFrequency())
        .quantity(food.getQuantity())
        .animalEntity(animalEntity)
        .build();
  }

  private Food buildFood(FoodEntity foodEntity) {
    return Food.builder()
        .id(foodEntity.getId())
        .frequency(foodEntity.getFrequency())
        .category(foodEntity.getCategory())
        .quantity(foodEntity.getQuantity())
        .build();
  }
}

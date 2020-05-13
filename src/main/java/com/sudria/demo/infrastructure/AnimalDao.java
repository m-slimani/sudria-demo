package com.sudria.demo.infrastructure;

import com.sudria.demo.domain.Animal;
import com.sudria.demo.domain.Animal.Food;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AnimalDao {

  private ZooRepository zooRepository;
  private FoodRepository foodRepository;

  public AnimalDao(ZooRepository zooRepository, FoodRepository foodRepository) {
    this.zooRepository = zooRepository;
    this.foodRepository = foodRepository;
  }

  public List<Animal> findAnimals() {
    return StreamSupport.stream(zooRepository.findAll().spliterator(), false)
        .map(animalEntitie -> buildAnimal(animalEntitie, foodRepository.findByAnimalEntity(animalEntitie)))
        .collect(Collectors.toList());
  }

  public Animal findAnimals(Long id) throws NotFoundException {
    AnimalEntity animalEntity = zooRepository.findById(id).orElseThrow(NotFoundException::new);
    return buildAnimal(animalEntity, foodRepository.findByAnimalEntity(animalEntity));
  }

  public Animal createAnimals(Animal animal) throws NotFoundException {
    AnimalEntity animalEntity = zooRepository.save(buildAnimalEntity(animal));

    animal.getFoods()
        .stream()
        .forEach(food ->
            foodRepository.save(buildFoodEntity(animalEntity, food)));

    return buildAnimal(
        zooRepository.findById(animalEntity.getId()).orElseThrow(NotFoundException::new),
        foodRepository.findByAnimalEntity(animalEntity));
  }


  public void deleteAnimals(Long id) {
    zooRepository.delete(zooRepository.findById(id).get());
  }

  public void updateAnimal(Animal animal) {

    AnimalEntity animalEntity = zooRepository.save(buildAnimalEntity(animal));

    animal.getFoods()
        .stream()
        .forEach(food ->
            foodRepository.save(buildFoodEntity(animalEntity, food)));
  }

  private FoodEntity buildFoodEntity(AnimalEntity animalEntity, Food food) {
    return FoodEntity.builder()
        .category(food.getCategory())
        .frequency(food.getFrequency())
        .quantity(food.getQuantity())
        .animalEntity(animalEntity)
        .build();
  }

  public Animal replaceAnimal(Animal animal) {
    AnimalEntity animalEntity = zooRepository.save(buildAnimalEntity(animal));
    return buildAnimal(animalEntity,  foodRepository.findByAnimalEntity(animalEntity));
  }

  private AnimalEntity buildAnimalEntity(Animal animal) {
    return AnimalEntity
        .builder()
        .name(animal.getName())
        .age(animal.getAge())
        .category(animal.getCategory())
        .build();
  }

  private Animal buildAnimal(AnimalEntity animalEntity, List<FoodEntity> foodEntities) {
    return Animal.builder()
        .id(animalEntity.getId())
        .name(animalEntity.getName())
        .age(animalEntity.getAge())
        .category(animalEntity.getCategory())
        .foods(
            foodEntities
                .stream()
                .map(foodEntity -> Food.builder()
                    .id(foodEntity.getId())
                    .build())
                .collect(Collectors.toList())
        )
        .build();
  }


}

package com.sudria.demo.infrastructure;

import com.sudria.demo.domain.animal.Animal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AnimalDao {

  private AnimalRepository animalRepository;
  private FoodRepository foodRepository;

  public AnimalDao(AnimalRepository animalRepository, FoodRepository foodRepository) {
    this.animalRepository = animalRepository;
    this.foodRepository = foodRepository;
  }

  public List<Animal> findAnimals() {
    return StreamSupport.stream(animalRepository.findAll().spliterator(), false)
        .map(animalEntitie -> buildAnimal(animalEntitie, foodRepository.findByAnimalEntity(animalEntitie)))
        .collect(Collectors.toList());
  }

  public Animal findAnimals(Long id) throws NotFoundException {
    AnimalEntity animalEntity = animalRepository.findById(id).orElseThrow(NotFoundException::new);
    return buildAnimal(animalEntity, foodRepository.findByAnimalEntity(animalEntity));
  }

  public Animal createAnimals(Animal animal) throws NotFoundException {
    AnimalEntity animalEntity = animalRepository.save(buildAnimalEntity(animal));
    return buildAnimal(
        animalRepository.findById(animalEntity.getId()).orElseThrow(NotFoundException::new),
        foodRepository.findByAnimalEntity(animalEntity));
  }


  public void deleteAnimals(Long id) {
    animalRepository.delete(animalRepository.findById(id).get());
  }

  public Animal updateAnimal(Animal animal) {
    return buildAnimal(animalRepository.save(buildAnimalEntity(animal)),
        foodRepository.findByAnimalEntityId(animal.getId()));
  }


  public Animal replaceAnimal(Animal animal) {
    AnimalEntity animalEntity = animalRepository.save(buildAnimalEntity(animal));
    return buildAnimal(animalEntity, foodRepository.findByAnimalEntity(animalEntity));
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
        .foods(foodEntities
            .stream()
            .map(foodEntity -> foodEntity.getId())
            .collect(Collectors.toList()))
        .build();
  }
}

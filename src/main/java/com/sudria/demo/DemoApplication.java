package com.sudria.demo;

import com.sudria.demo.domain.Animal.Food;
import com.sudria.demo.infrastructure.AnimalEntity;
import com.sudria.demo.infrastructure.FoodEntity;
import com.sudria.demo.infrastructure.FoodRepository;
import com.sudria.demo.infrastructure.ZooRepository;
import java.util.Arrays;
import java.util.List;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class DemoApplication implements CommandLineRunner {

  private ZooRepository zooRepository;
  private FoodRepository foodRepository;

  public DemoApplication(ZooRepository zooRepository, FoodRepository foodRepository) {
    this.zooRepository = zooRepository;
    this.foodRepository = foodRepository;
  }

  public static void main(String[] args) {

    SpringApplication.run(DemoApplication.class, args);
    System.out.println("Hello SUDRIA !");
  }

  @Override
  public void run(String... args) {

    log.info("Data initilisation...");
    saveAnimal(1L, "Garfield", 5, "FELINE", Arrays.asList(Food.builder().frequency(2).category("meat").build()));
    saveAnimal(2L, "Nemo", 1, "FISCH", Arrays.asList(Food.builder().frequency(1).category("algue").build()));
  }

  @Transactional
  private void saveAnimal(long id, String name, int age, String category, List<Food> foods) {

    AnimalEntity animalEntity = this.zooRepository.save(
        AnimalEntity
            .builder()
            .id(id)
            .name(name)
            .age(age)
            .category(category)
            .build());

    foods.stream()
        .forEach(food ->
            foodRepository.save(
                FoodEntity
                    .builder()
                    .category(food.getCategory())
                    .frequency(food.getFrequency())
                    .quantity(food.getQuantity())
                    .animalEntity(animalEntity)
                    .build()
            ));
  }

}

package com.sudria.demo;

import com.sudria.demo.infrastructure.AnimalEntity;
import com.sudria.demo.infrastructure.AnimalRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

  @Autowired
  private AnimalRepository animalRepository;

  public DemoApplication(AnimalRepository animalRepository) {
    this.animalRepository = animalRepository;
  }

  public static void main(String[] args) {

    SpringApplication.run(DemoApplication.class, args);
    System.out.println("Hello SUDRIA !");
  }

  @Override
  public void run(String... args) {

    log.info("Data initilisation...");
    saveAnimal(1L, "Garfield", 5, "FELINE");
    saveAnimal(2L, "Nemo", 1, "FISCH");
  }

  private void saveAnimal(long id, String name, int age, String category) {
    this.animalRepository.save(
        AnimalEntity
            .builder()
            .id(id)
            .name(name)
            .age(age)
            .category(category)
            .build());
  }

}

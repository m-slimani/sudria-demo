package com.sudria.demo.domain;

import com.sudria.demo.infrastructure.AnimalDao;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AnimalService {

  private AnimalDao animalDao;

  public AnimalService(AnimalDao animalDao) {
    this.animalDao = animalDao;
  }

  public List<Animal> getAnimals() {
    return animalDao.findAnimals();
  }

  @Cacheable("animals")
  public Animal getAnimals(Long id) throws NotFoundException {
    return animalDao.findAnimals(id);
  }

  public Animal addAnimal(Animal animal) throws NotFoundException {
    return animalDao.createAnimals(animal);
  }

  public void deleteAnimals(Long id) {
    animalDao.deleteAnimals(id);
  }

  public void patchAnimals(Animal animal) {
    animalDao.updateAnimal(animal);
  }

  public Animal findAnimal(Long id) throws NotFoundException {
    return animalDao.findAnimals(id);
  }

  public Animal replaceAnimal(Animal animal) {
    return animalDao.replaceAnimal(animal);
  }
}

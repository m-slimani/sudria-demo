package com.sudria.demo.infrastructure;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends CrudRepository<FoodEntity, Long> {

 List<FoodEntity> findByAnimalEntityId(Long animalEntityId);
 List<FoodEntity> findByAnimalEntity(AnimalEntity animalEntity);

}

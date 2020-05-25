package com.sudria.demo.application.graphql;

import com.sudria.demo.domain.Animal;
import com.sudria.demo.domain.AnimalService;
import io.leangen.graphql.annotations.GraphQLQuery;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class AnimalResolver {

  private AnimalService animalService;

  public AnimalResolver(AnimalService animalService) {
    this.animalService = animalService;
  }

  @GraphQLQuery
  public List<Animal> getAnimals (){
    return animalService.getAnimals();
  }

}

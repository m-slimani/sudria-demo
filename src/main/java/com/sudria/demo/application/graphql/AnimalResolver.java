package com.sudria.demo.application.graphql;

import com.sudria.demo.domain.animal.Animal;
import com.sudria.demo.domain.animal.AnimalService;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class AnimalResolver {

  private AnimalService animalService;

  public AnimalResolver(AnimalService animalService) {
    this.animalService = animalService;
  }

  @GraphQLQuery(name = "animals")
  public List<Animal> getAnimals(@GraphQLArgument(name = "first", defaultValue = "null" ) Integer first) {
    if (Objects.isNull(first)){
      return animalService.getAnimals();
    }
    return animalService.getAnimals()
        .stream()
        .limit(first)
        .collect(Collectors.toList());
  }

}

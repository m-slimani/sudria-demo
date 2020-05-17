package com.sudria.demo.application.graphql;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaGenerator;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@GraphQLApi
public class GraphqlController {

  private final GraphQL graphQL;

  public GraphqlController(AnimalResolver animalResolver) {

    GraphQLSchema schema = new GraphQLSchemaGenerator()
//        .withBasePackages("com.sudria.demo.graphql")
        .withOperationsFromSingletons(animalResolver)
        .generate();
    graphQL = GraphQL.newGraphQL(schema).build();

    log.info("Generated GraphQL schema using SPQR");
  }


  @PostMapping(value = "/graphql/v1",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  @ResponseBody
  public Map<String, Object> executeQuery(@RequestBody Map<String, String> request, HttpServletRequest raw) {
    ExecutionResult executionResult = graphQL.execute(ExecutionInput.newExecutionInput()
        .query(request.get("query"))
        .operationName(request.get("operationName"))
        .context(raw)
        .build());
    return executionResult.toSpecification();
  }

}

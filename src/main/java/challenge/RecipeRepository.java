package challenge;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends MongoRepository<Recipe, String> {

    ResponseEntity<List<Recipe>> findAllByTitleContainsOrDescriptionContainsOrderByTitleAsc(String search);

}

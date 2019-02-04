package challenge;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends MongoRepository<Recipe, String> {

    Recipe findRecipeById(String id);

    List<Recipe> findAllByIngredients(String ingredient);

    List<Recipe> findAllByIngredientsContaining(String ingredient);

    List<Recipe> findAllByTitleContainingOrDescriptionContainingIgnoreCase(String search);

    List<Recipe> findRecipeByTitleOrDescription(String search);

    List<Recipe> findRecipeByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String search);
}

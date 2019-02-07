package challenge;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class RecipeServiceImpl implements RecipeService {

	@Autowired
	private RecipeRepository recipeRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public Recipe save(Recipe recipe) {
		recipeRepository.insert(recipe);
		return recipeRepository.findById(recipe.getId()).get();
	}

	@Override
	public void delete(String id) {
		recipeRepository.deleteById(id);
	}

	@Override
	public Optional<Recipe> get(String id) {
		return recipeRepository.findById(id);
	}

	@Override
	public void update(String id, Recipe recipe) {
		mongoTemplate.updateFirst(
				Query.query(Criteria.where("_id").is(id)),
				Update.update("title", recipe.getTitle())
						.set("description", recipe.getDescription())
						.set("ingredients", recipe.getIngredients()),
				Recipe.class);

	}

	@Override
	public List<Recipe> listByIngredient(String ingredient) {

		return recipeRepository.findAllByIngredientsContaining(ingredient);
	}

	@Override
	public List<Recipe> search(String search) {
		//return recipeRepository.findAllByTitleContainingOrDescriptionContainingIgnoreCase(search);
		//return recipeRepository.findRecipeByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(search);
		return recipeRepository.findRecipeByTitleOrDescription(search);

		//return null;
		//Deve pesquisar nos campos title e description
		//Deve pesquisar em qualquer lugar do texto
		//Deve pesquisar usando case-insensitive
		//Ordenar pelo campo title em ordem alfabética ascendente.
		//Ex: /recipe/search?search=choco
	}

	@Override
	public void like(String id, String userId) {
		Optional<Recipe> recipe = recipeRepository.findById(id);
		recipe.ifPresent(recipe1 -> recipe1.getLikes().add(userId));
		save(recipe.get());
		//user id
	}

	@Override
	public void unlike(String id, String userId) {
		Optional<Recipe> recipe = recipeRepository.findById(id);
		recipe.ifPresent(recipe1 -> recipe1.getLikes().remove(userId));
		this.save(recipe.get());
	}

	@Override
	public RecipeComment addComment(String id, RecipeComment comment) {
		Recipe recipe = recipeRepository.findById(id).get();
		Stack<RecipeComment> comments = new Stack<>();
		comments.push(comment);
		recipe.setComments(comments);
		this.save(recipe);
		return comments.get(0);
	}

	@Override
	public void updateComment(String id, String commentId, RecipeComment comment) {
		Recipe recipe = recipeRepository.findById(id).get();
		recipe.getComments().stream().filter(comment1 -> comment1.equals(commentId))
				.forEach(comment1 -> comment1.setComment(comment.getComment()));
		this.save(recipe);
	}

	@Override
	public void deleteComment(String id, String commentId) {
		Recipe recipe = recipeRepository.findById(id).get();
		recipe.getComments().stream().filter(comment1 -> comment1.equals(commentId));
	}

}

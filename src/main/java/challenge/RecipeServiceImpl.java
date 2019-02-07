package challenge;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
	public void update(String id, Recipe recipe) {
		mongoTemplate.updateFirst(
				Query.query(Criteria.where("_id").is(id)),
				Update.update("title", recipe.getTitle())
						.set("description", recipe.getDescription())
						.set("ingredients", recipe.getIngredients()),
				Recipe.class);

	}

	@Override
	public Optional<Recipe> get(String id) {
		return recipeRepository.findById(id);
	}


    @Override
    public List<Recipe> listByIngredient(String ingredient) {

        return mongoTemplate.find(
                Query.query(Criteria.where("ingredients").is(ingredient)).with(Sort.by("title").ascending()),
                Recipe.class);

    }

    @Override
    public List<Recipe> search(String search) {
	    search.toLowerCase();

        return mongoTemplate.find(Query.query(new Criteria()
                        .orOperator(Criteria.where("title").regex(search, "i"),
                                Criteria.where("description").regex(search, "i")))
                        .with(Sort.by("title").ascending()),
                Recipe.class);
	}


	@Override
	public void like(String id, String userId) {
	    mongoTemplate.updateFirst(
	            Query.query(
	                    Criteria.where("_id").is(id)),
                new Update().addToSet("likes", userId),
                Recipe.class);

	}

	@Override
	public void unlike(String id, String userId) {
		mongoTemplate.updateFirst(
		        Query.query(
		                Criteria.where("_id").is(id)),
                new Update().pull("likes", userId),
                Recipe.class);
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

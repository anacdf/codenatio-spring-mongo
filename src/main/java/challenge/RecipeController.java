package challenge;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/recipe")
public class RecipeController {

	@Autowired
	private RecipeService service;

	@PostMapping
	public Recipe save(@RequestBody Recipe recipe) {
		return service.save(recipe);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Object> update(@RequestBody Recipe recipe, @PathVariable("id") String id ) {
		recipe.setId(id);
	    service.update(id, recipe);
	    return ResponseEntity.ok().build();
	}

	@DeleteMapping(value = "/{id}")
	public void delete(@PathVariable("id") String id) {
		service.delete(id);
	}

	@GetMapping(value = "/{id}")
	public Optional<Recipe> get(@PathVariable("id") String id) {
		return service.get(id);
	}

	@GetMapping(value = "/ingredient")
	public List<Recipe> listByIngredient(@RequestParam String ingredient) {
		return service.listByIngredient(ingredient);
	}

	@GetMapping(value = "/search")
	public List<Recipe> search(@RequestParam String search) {
		return service.search(search);
	}

	@PostMapping(value = "/{id}/like/{userId}")
	public void like(@PathVariable String id,@PathVariable String userId) {
		service.like(id, userId);
	}

	@DeleteMapping(value = "/{id}/like/{userId}")
	public void unlike(@PathVariable String id,@PathVariable String userId) {
		service.unlike(id, userId);
	}


	@PostMapping(value = "/{id}/comment")
	public RecipeComment addComment(@PathVariable String id, @RequestBody RecipeComment comment) {
		return service.addComment(id, comment);
	}

	@PutMapping(value = "/{id}/comment/{commentId}")
	public void updateComment(@PathVariable String id, @PathVariable String commentId,@RequestBody RecipeComment comment ) {
		service.updateComment(id, commentId, comment);
	}

	@DeleteMapping(value = "/{id}/comment/{commentId}")
	public void deleteComment(@PathVariable String id, @PathVariable String commentId) {
		service.deleteComment(id, commentId);
	}

}

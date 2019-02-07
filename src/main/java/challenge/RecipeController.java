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

		//Path to difference: <comments>
        //- actual  : <[challenge.RecipeCommentTest@7be37613, challenge.RecipeCommentTest@e0b6081]>
        //- expected: <null>
        //
        //Path to difference: <ingredients>
        //- actual  : <["ovo", "chocolate", "farinha"]>
        //- expected: <["cenoura", "cebola", "ovo", "carne"]>
        //
        //Path to difference: <description>
        //- actual  : <"Bolo de chocolate natural caseiro">
        //- expected: <"Assado de carne com legumas, ovo e lentilha">
        //
        //Path to difference: <title>
        //- actual  : <"Bolo de chocolate feito com MongoDB em java">
        //- expected: <"Assado de carne com legumes">
        //
        //Path to difference: <id>
        //- actual  : <"5bc698399531146718e31220">
        //- expected: <"5bc932af9531144888cc2bd2">
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

        //- actual  : <"Muito gostoso! Demais!">
        //- expected: <"Update comment">
	}

	@DeleteMapping(value = "/{id}/comment/{commentId}")
	public void deleteComment(@PathVariable String id, @PathVariable String commentId) {
		service.deleteComment(id, commentId);
		// expected:<[1]> but was:<[2]>
	}

}

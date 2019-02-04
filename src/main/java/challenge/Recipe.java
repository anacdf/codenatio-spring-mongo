package challenge;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Classe para mapear a receita no MongoDB
 *
 */
@Document
public class Recipe {

    @Id
    private String id;

    @Indexed(direction = IndexDirection.ASCENDING)
    private String title;
    private String description;
    private List<String> ingredients;
    private List<String> likes;
    private List<RecipeComment> comments;

    public Recipe() {}

    public Recipe(String title, String description, List<String> ingredients, List<String> likes, List<RecipeComment> comments) {
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
        this.likes = likes;
        this.comments = comments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getLikes() {
        return likes;
    }

    public void setLikes(List<String> likes) {
        this.likes = likes;
    }

    public List<RecipeComment> getComments() {
        return comments;
    }

    public void setComments(List<RecipeComment> comments) {
        this.comments = comments;
    }
}

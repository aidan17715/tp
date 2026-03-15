package seedu.sudocook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeleteRecipeCommandTest {
    private RecipeBook recipeBook;
    private Recipe testRecipe;

    @BeforeEach
    public void setUp() {
        recipeBook = new RecipeBook();
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("Water", 1, "Liter"));
        ArrayList<String> steps = new ArrayList<>();
        steps.add("Boil it.");

        testRecipe = new Recipe("Boiled Water", ingredients, steps);
        recipeBook.addRecipe(testRecipe);
    }

    @Test
    public void execute_validIndex_recipeRemoved() {

        assertEquals(2, recipeBook.size());

        DeleteRecipeCommand deleteCommand = new DeleteRecipeCommand(1);
        deleteCommand.execute(recipeBook);

        assertEquals(1, recipeBook.size());
    }

    @Test
    public void execute_invalidIndex_throwsException() {
        DeleteRecipeCommand deleteRecipeCommand = new DeleteRecipeCommand(100);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            deleteRecipeCommand.execute(recipeBook);
        });
    }
}

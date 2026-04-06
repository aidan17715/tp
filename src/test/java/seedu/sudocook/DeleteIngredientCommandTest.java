package seedu.sudocook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DeleteIngredientCommandTest {
    private Inventory inventory;
    private ByteArrayOutputStream output;
    private PrintStream originalOut;

    @BeforeEach
    public void setUp() {
        inventory = new Inventory();
        inventory.addIngredient(new Ingredient("Apple", 10, "pcs"));
        inventory.addIngredient(new Ingredient("Sugar", 500, "g"));
        originalOut = System.out;
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    private String getOutput() {
        return output.toString(StandardCharsets.UTF_8);
    }

    // --- Delete by name (remove all) ---
    @Test
    public void execute_deleteByNameRemoveAll_removesIngredient() {
        DeleteIngredientCommand cmd = new DeleteIngredientCommand("Apple", new Ui());
        cmd.execute(inventory);
        assertEquals(1, inventory.getSize());
        assertTrue(getOutput().contains("Removed all of"));
    }

    // --- Delete by 1-based index (remove all) ---
    @Test
    public void execute_deleteByIndexRemoveAll_removesIngredient() {
        DeleteIngredientCommand cmd = new DeleteIngredientCommand("1", new Ui());
        cmd.execute(inventory);
        assertEquals(1, inventory.getSize());
        assertTrue(getOutput().contains("Removed all of"));
    }

    // --- Delete partial quantity ---
    @Test
    public void execute_deletePartialQuantity_updatesQuantity() {
        DeleteIngredientCommand cmd = new DeleteIngredientCommand("Apple", 3);
        cmd.execute(inventory);
        assertEquals(2, inventory.getSize());
        assertEquals(7.0, inventory.getIngredient(0).getQuantity());
        assertTrue(getOutput().contains("Removed 3.0"));
    }

    // --- Delete when quantity to remove >= existing ---
    @Test
    public void execute_deleteExceedingQuantity_removesAll() {
        DeleteIngredientCommand cmd = new DeleteIngredientCommand("Apple", 100);
        cmd.execute(inventory);
        assertEquals(1, inventory.getSize());
        assertTrue(getOutput().contains("Removed all of"));
    }

    // --- Delete non-existent ingredient ---
    @Test
    public void execute_deleteNonExistent_printsError() {
        DeleteIngredientCommand cmd = new DeleteIngredientCommand("Banana", new Ui());
        cmd.execute(inventory);
        assertEquals(2, inventory.getSize());
        assertTrue(getOutput().contains("not found"));
    }

    // --- Delete by invalid index ---
    @Test
    public void execute_deleteByInvalidIndex_printsError() {
        DeleteIngredientCommand cmd = new DeleteIngredientCommand("99", new Ui());
        cmd.execute(inventory);
        assertEquals(2, inventory.getSize());
        assertTrue(getOutput().contains("not found"));
    }

    // --- execute(RecipeBook) does nothing ---
    @Test
    public void execute_recipeBookOverload_doesNothing() {
        RecipeBook recipeBook = new RecipeBook();
        DeleteIngredientCommand cmd = new DeleteIngredientCommand("Apple", new Ui());
        cmd.execute(recipeBook);
        assertEquals(0, recipeBook.getSize());
    }
}

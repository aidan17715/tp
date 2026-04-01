package seedu.sudocook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddIngredientCommandTest {
    private Inventory inventory;
    private ByteArrayOutputStream output;
    private PrintStream originalOut;

    @BeforeEach
    public void setUp() {
        inventory = new Inventory();
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

    @Test
    public void execute_addWithoutExpiry_addsIngredient() {
        AddIngredientCommand cmd = new AddIngredientCommand("Apple", 5, "pcs");
        cmd.execute(inventory);
        assertEquals(1, inventory.size());
        assertTrue(getOutput().contains("Added"));
        assertTrue(getOutput().contains("Apple"));
    }

    @Test
    public void execute_addWithExpiry_addsIngredient() {
        LocalDate expiry = LocalDate.of(2026, 12, 31);
        AddIngredientCommand cmd = new AddIngredientCommand("Milk", 2, "L", expiry);
        cmd.execute(inventory);
        assertEquals(1, inventory.size());
        assertEquals(expiry, inventory.getIngredient(0).getExpiryDate());
    }

    @Test
    public void execute_addWithNullExpiry_addsIngredient() {
        AddIngredientCommand cmd = new AddIngredientCommand("Salt", 100, "g", null);
        cmd.execute(inventory);
        assertEquals(1, inventory.size());
    }

    @Test
    public void execute_recipeBookOverload_doesNothing() {
        RecipeBook recipeBook = new RecipeBook();
        int initialSize = recipeBook.size();
        AddIngredientCommand cmd = new AddIngredientCommand("Sugar", 50, "g");
        cmd.execute(recipeBook);
        assertEquals(initialSize, recipeBook.size());
    }

    @Test
    public void execute_viaParsedCommand_addsIngredient() {
        Parser parser = new Parser(new Ui());
        Command cmd = parser.parse("add-i n/Butter q/100 u/g");
        cmd.execute(inventory);
        assertEquals(1, inventory.size());
    }
}

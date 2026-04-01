package seedu.sudocook;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;

public class InventoryTest {
    private Inventory testInventory = new Inventory();

    @Test
    public void addIngredient_newIngredient_increasesSize() {
        testInventory.addIngredient(new Ingredient("Sugar", 100, "g"));
        assertEquals(1, testInventory.size());
    }

    @Test
    public void addIngredient_sameNameAndUnit_mergesQuantity() {
        testInventory.addIngredient(new Ingredient("Sugar", 100, "g"));
        testInventory.addIngredient(new Ingredient("Sugar", 50, "g"));
        assertEquals(1, testInventory.size());
        assertEquals(150, testInventory.getIngredient(0).getQuantity());
    }

    @Test
    public void removeIngredient_validIndex_removesCorrectly() {
        testInventory.addIngredient(new Ingredient("Salt", 10, "g"));
        testInventory.removeIngredient(0);
        assertEquals(0, testInventory.size());
    }

    @Test
    public void findIndexByName_missingIngredient_returnsNegativeOne() {
        assertEquals(-1, testInventory.findIndexByName("Garlic"));
    }

    @Test
    public void parserTest_invalidFormat_doesNotAdd() {
        Ui ui = new Ui();
        Parser parser = new Parser(ui);
        Command cmd = parser.parse("add-i Gibberish");
        cmd.execute(testInventory);
        assertEquals(0, testInventory.size());
    }

    // --- Expiry Tests ---
    @Test
    public void addIngredient_withExpiry_storesExpiryDate() {
        LocalDate expiry = LocalDate.of(2026, 12, 31);
        testInventory.addIngredient(new Ingredient("Milk", 2, "L", expiry));
        assertEquals(1, testInventory.size());
        assertEquals(expiry, testInventory.getIngredient(0).getExpiryDate());
    }

    @Test
    public void addIngredient_withoutExpiry_expiryIsNull() {
        testInventory.addIngredient(new Ingredient("Sugar", 100, "g"));
        assertEquals(1, testInventory.size());
        assertNull(testInventory.getIngredient(0).getExpiryDate());
    }

    @Test
    public void addIngredient_twoIngredientsWithExpiry_bothStored() {
        LocalDate expiry1 = LocalDate.of(2026, 6, 1);
        LocalDate expiry2 = LocalDate.of(2026, 12, 31);
        testInventory.addIngredient(new Ingredient("Milk", 1, "L", expiry1));
        testInventory.addIngredient(new Ingredient("Butter", 100, "g", expiry2));
        assertEquals(2, testInventory.size());
        assertEquals(expiry1, testInventory.getIngredient(0).getExpiryDate());
        assertEquals(expiry2, testInventory.getIngredient(1).getExpiryDate());
    }
}

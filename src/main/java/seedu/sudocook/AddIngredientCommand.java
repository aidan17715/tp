package seedu.sudocook;

/**
 * Command to add an ingredient to the inventory.
 */
public class AddIngredientCommand extends Command {
    private final String name;
    private final double quantity;
    private final String unit;

    /**
     * Constructs an AddIngredientCommand with the specified ingredient details.
     *
     * @param name The name of the ingredient
     * @param quantity The quantity of the ingredient
     * @param unit The unit of measurement
     */
    public AddIngredientCommand(String name, double quantity, String unit) {
        super(false);
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    @Override
    public void execute(RecipeBook recipeBook) {
        // This method is not used for inventory operations
    }

    /**
     * Executes the command by adding the ingredient to the inventory.
     *
     * @param inventory The inventory to add the ingredient to
     */
    public void execute(Inventory inventory) {
        assert inventory != null : "Inventory must not be null";
        assert name != null && !name.isEmpty() : "Ingredient name must not be empty";
        assert quantity > 0 : "Ingredient quantity must be positive";
        Ingredient ingredient = new Ingredient(name, quantity, unit);
        inventory.addIngredient(ingredient);
        Ui.printMessage("Added: " + ingredient);
    }
}

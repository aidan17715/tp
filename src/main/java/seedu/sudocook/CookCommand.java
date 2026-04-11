package seedu.sudocook;


import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CookCommand extends Command {
    private static Logger logger = Logger.getLogger("CookCommand");
    int index;
    public CookCommand(boolean isExit, int index) {
        super(isExit);
        this.index = index;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public void execute(Recipe recipe, Inventory inventory) {
        if (recipe == null) {
            return;
        }
        try {
            ArrayList<Ingredient> ingredientRequirements = IngredientRequirements.aggregateFor(recipe);
            for (Ingredient i : ingredientRequirements) {
                int ingredientIndex = inventory.findIndexByName(i.getName());
                if (ingredientIndex < 0
                        || inventory.getIngredient(ingredientIndex).getQuantity() < i.getQuantity()) {
                    logger.log(Level.INFO, "Not enough ingredients for this recipe");
                    throw new RuntimeException("Not enough ingredients");
                }
            }

            for (Ingredient i : ingredientRequirements) {
                Command c = new DeleteIngredientCommand(i.getName(), i.getQuantity());
                c.execute(inventory);
            }
            Ui.printMessage("Cooked Recipe " + recipe.getName());
        } catch (RuntimeException e) {
            Ui.printError("Not enough ingredients");
        }
    }
}

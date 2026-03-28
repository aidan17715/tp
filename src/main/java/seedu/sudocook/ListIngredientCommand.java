package seedu.sudocook;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Command to list all ingredients in the inventory.
 */
public class ListIngredientCommand extends Command {
    private final LocalDate expiryCutoff;

    public ListIngredientCommand() {
        this(null);
    }

    public ListIngredientCommand(LocalDate expiryCutoff) {
        super(false);
        this.expiryCutoff = expiryCutoff;
    }

    @Override
    public void execute(RecipeBook recipeBook) {
        // Not used
    }

    @Override
    public void execute(Inventory inventory) {
        execute(inventory, expiryCutoff);
    }

    @Override
    public void execute(Inventory inventory, LocalDate expiry) {
        ArrayList<Ingredient> ingredients = getIngredientsToDisplay(inventory, expiry);
        if (ingredients.isEmpty()) {
            Ui.printMessage(getEmptyMessage(expiry));
            return;
        }

        StringBuilder sb = new StringBuilder(getHeader(expiry));
        for (int i = 0; i < ingredients.size(); i++) {
            sb.append(i + 1).append(". ").append(ingredients.get(i).toString());
            if (i < ingredients.size() - 1) {
                sb.append("\n");
            }
        }
        Ui.printMessage(sb.toString());
    }

    private ArrayList<Ingredient> getIngredientsToDisplay(Inventory inventory, LocalDate expiry) {
        ArrayList<Ingredient> ingredients = inventory.getIngredients();
        if (expiry == null) {
            return ingredients;
        }

        ArrayList<Ingredient> filteredIngredients = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            LocalDate ingredientExpiry = ingredient.getExpiryDate();
            if (ingredientExpiry != null && ingredientExpiry.isBefore(expiry)) {
                filteredIngredients.add(ingredient);
            }
        }
        return filteredIngredients;
    }

    private String getHeader(LocalDate expiry) {
        if (expiry == null) {
            return "Here are the ingredients in your inventory:\n";
        }
        return "Here are the ingredients in your inventory expiring before " + expiry + ":\n";
    }

    private String getEmptyMessage(LocalDate expiry) {
        if (expiry == null) {
            return "There are no ingredients in the inventory.";
        }
        return "There are no ingredients expiring before " + expiry + ".";
    }
}

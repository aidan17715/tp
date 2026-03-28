package seedu.sudocook;

public class RecommendByInventoryCommand extends Command {

    public RecommendByInventoryCommand() {
        super(false);
    }

    @Override
    public void execute(Inventory inventory, RecipeBook recipes) {
        StringBuilder sb = new StringBuilder();
        int count = 0;

        for (int i = 0; i < recipes.size(); i++) {
            Recipe recipe = recipes.getRecipe(i);
            if (canMake(recipe, inventory)) {
                count++;
                sb.append(count).append(". ").append(recipe.getName()).append("\n");
            }
        }

        if (count == 0) {
            Ui.printMessage("No recipes can be made with the current inventory.");
        } else {
            Ui.printMessage("Recipes you can make with your inventory:\n" + sb.toString().trim());
        }
    }

    private boolean canMake(Recipe recipe, Inventory inventory) {
        for (Ingredient required : recipe.getIngredients()) {
            double available = -1;
            for (int j = 0; j < inventory.size(); j++) {
                Ingredient item = inventory.getIngredient(j);
                if (item.getName().equalsIgnoreCase(required.getName())) {
                    available = item.getQuantity();
                    break;
                }
            }
            if (available < required.getQuantity()) {
                return false;
            }
        }
        return true;
    }
}
package seedu.sudocook;

import java.util.ArrayList;

final class IngredientRequirements {
    private IngredientRequirements() {
    }

    static ArrayList<Ingredient> aggregateFor(Recipe recipe) {
        ArrayList<Ingredient> aggregatedIngredients = new ArrayList<>();
        for (Ingredient ingredient : recipe.getIngredients()) {
            Ingredient existingIngredient = findMatchingIngredient(aggregatedIngredients, ingredient);
            if (existingIngredient == null) {
                aggregatedIngredients.add(new Ingredient(ingredient.getName(), ingredient.getQuantity(),
                        ingredient.getUnit()));
            } else {
                existingIngredient.addQuantity(ingredient.getQuantity(), null);
            }
        }
        return aggregatedIngredients;
    }

    private static Ingredient findMatchingIngredient(ArrayList<Ingredient> ingredients, Ingredient ingredient) {
        for (Ingredient existingIngredient : ingredients) {
            if (existingIngredient.getName().equalsIgnoreCase(ingredient.getName())
                    && existingIngredient.getUnit().equalsIgnoreCase(ingredient.getUnit())) {
                return existingIngredient;
            }
        }
        return null;
    }
}

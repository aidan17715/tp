package seedu.sudocook;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Manages the command history and enables undo functionality.
 * Stores snapshots of application state (RecipeBook and Inventory) after each command.
 */
public class CommandHistory {
    private static final int MAX_HISTORY_SIZE = 50;
    private Stack<HistorySnapshot> history;

    public CommandHistory() {
        this.history = new Stack<>();
    }

    /**
     * Saves a snapshot of the current state.
     * @param recipes The current RecipeBook state
     * @param inventory The current Inventory state
     */
    public void saveSnapshot(RecipeBook recipes, Inventory inventory) {
        if (history.size() >= MAX_HISTORY_SIZE) {
            history.remove(0);
        }
        history.push(new HistorySnapshot(deepCopyRecipeBook(recipes), 
                                         deepCopyInventory(inventory)));
    }

    /**
     * Restores the previous state from history.
     * @param recipes The RecipeBook to restore into
     * @param inventory The Inventory to restore into
     * @return true if undo was successful, false if no history available
     */
    public boolean undo(RecipeBook recipes, Inventory inventory) {
        if (history.isEmpty()) {
            return false;
        }
        
        HistorySnapshot snapshot = history.pop();
        restoreRecipeBook(recipes, snapshot.getRecipeBook());
        restoreInventory(inventory, snapshot.getInventory());
        return true;
    }

    /**
     * Checks if undo is available.
     */
    public boolean canUndo() {
        return !history.isEmpty();
    }

    /**
     * Clears all history.
     */
    public void clear() {
        history.clear();
    }

    /**
     * Gets the current history size.
     */
    public int getHistorySize() {
        return history.size();
    }

    private RecipeBook deepCopyRecipeBook(RecipeBook original) {
        RecipeBook copy = new RecipeBook();
        for (int i = 0; i < original.size(); i++) {
            Recipe recipe = original.getRecipe(i);
            ArrayList<Ingredient> ingredientsCopy = new ArrayList<>();
            for (Ingredient ing : recipe.getIngredients()) {
                ingredientsCopy.add(new Ingredient(ing.getName(), ing.getQuantity(), 
                                                   ing.getUnit(), ing.getExpiryDate()));
            }
            ArrayList<String> stepsCopy = new ArrayList<>(recipe.getSteps());
            copy.addRecipe(new Recipe(recipe.getName(), ingredientsCopy, 
                                     stepsCopy, recipe.getTime()));
        }
        return copy;
    }

    private Inventory deepCopyInventory(Inventory original) {
        Inventory copy = new Inventory();
        for (Ingredient ing : original.getIngredients()) {
            copy.addIngredient(new Ingredient(ing.getName(), ing.getQuantity(), 
                                             ing.getUnit(), ing.getExpiryDate()));
        }
        return copy;
    }

    private void restoreRecipeBook(RecipeBook target, RecipeBook source) {
        // Clear the target (RecipeBook uses 1-based indexing)
        while (target.size() > 0) {
            target.removeRecipe(1);
        }
        // Copy from source
        for (int i = 0; i < source.size(); i++) {
            Recipe recipe = source.getRecipe(i);
            ArrayList<Ingredient> ingredientsCopy = new ArrayList<>();
            for (Ingredient ing : recipe.getIngredients()) {
                ingredientsCopy.add(new Ingredient(ing.getName(), ing.getQuantity(), 
                                                   ing.getUnit(), ing.getExpiryDate()));
            }
            ArrayList<String> stepsCopy = new ArrayList<>(recipe.getSteps());
            target.addRecipe(new Recipe(recipe.getName(), ingredientsCopy, 
                                       stepsCopy, recipe.getTime()));
        }
    }

    private void restoreInventory(Inventory target, Inventory source) {
        // Clear the target (Inventory uses 0-based indexing)
        while (target.size() > 0) {
            target.removeIngredient(0);
        }
        // Copy from source
        for (Ingredient ing : source.getIngredients()) {
            target.addIngredient(new Ingredient(ing.getName(), ing.getQuantity(), 
                                               ing.getUnit(), ing.getExpiryDate()));
        }
    }

    /**
     * Represents a snapshot of application state at a point in time.
     */
    private static class HistorySnapshot {
        private RecipeBook recipeBook;
        private Inventory inventory;

        public HistorySnapshot(RecipeBook recipeBook, Inventory inventory) {
            this.recipeBook = recipeBook;
            this.inventory = inventory;
        }

        public RecipeBook getRecipeBook() {
            return recipeBook;
        }

        public Inventory getInventory() {
            return inventory;
        }
    }
}

package seedu.sudocook;

public class ListRecipeCommand extends Command {
    public ListRecipeCommand() {
        super(false);
    }

    @Override
    public void execute(RecipeBook recipes){
        recipes.listRecipe();
    }
}

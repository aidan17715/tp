package seedu.sudocook;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DeleteRecipeCommand extends Command {
    private static final Logger logger = Logger.getLogger(DeleteRecipeCommand.class.getName());
    private final int index;


    public DeleteRecipeCommand(int index) {
        super(false);
        this.index = index;
    }

    @Override
    public void execute(RecipeBook recipes){
        logger.log(Level.INFO, "Deleting recipe at index: " + index);
        recipes.removeRecipe(index);
    }
}

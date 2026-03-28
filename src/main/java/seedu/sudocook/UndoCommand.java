package seedu.sudocook;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents an undo command that restores the previous application state.
 */
public class UndoCommand extends Command {
    private static final Logger logger = Logger.getLogger("UndoCommand");

    public UndoCommand() {
        super(false);
    }

    @Override
    public void execute(RecipeBook recipes) {
        logger.log(Level.INFO, "UndoCommand received but requires CommandHistory context");
        Ui.printError("Undo command requires proper initialization. This is an internal error.");
    }

    @Override
    public void execute(Inventory inventory) {
        logger.log(Level.INFO, "UndoCommand received but requires CommandHistory context");
        Ui.printError("Undo command requires proper initialization. This is an internal error.");
    }

    /**
     * Executes the undo operation using the provided history manager.
     * @param history The CommandHistory containing state snapshots
     * @param recipes The RecipeBook to restore
     * @param inventory The Inventory to restore
     */
    public void execute(CommandHistory history, RecipeBook recipes, Inventory inventory) {
        if (history == null || !history.canUndo()) {
            logger.log(Level.INFO, "No history available for undo");
            Ui.printMessage("No previous commands to undo.");
            return;
        }

        boolean undoSuccess = history.undo(recipes, inventory);
        if (undoSuccess) {
            logger.log(Level.INFO, "Undo operation successful");
            Ui.printMessage("✓ Last command undone successfully.");
        } else {
            logger.log(Level.WARNING, "Undo operation failed");
            Ui.printMessage("Could not undo. No history available.");
        }
    }
}

package seedu.sudocook;

import java.time.LocalDate;

public class Command {
    public boolean isExit;

    public Command(boolean isExit) {
        this.isExit = isExit;
    }

    public void execute() {

    }

    public void execute(RecipeBook list) {

    }

    public void execute(Inventory inventory) {

    }

    public void execute(Recipe recipe, Inventory inventory) {

    }

    public void execute(Inventory inventory, RecipeBook recipes) {

    }

    public void execute(Inventory inventory, LocalDate expiry){

    }

    public int getIndex() {
        return 0;
    }

}

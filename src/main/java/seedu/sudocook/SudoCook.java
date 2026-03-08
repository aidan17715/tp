package seedu.sudocook;

/**
 * Main class for the SudoCook application.
 * Initializes the application and runs the main loop.
 */
public class SudoCook {

    public static void main(String[] args) {
        Ui ui = new Ui();
        Parser parser = new Parser(ui);
        parser.run();
    }
}

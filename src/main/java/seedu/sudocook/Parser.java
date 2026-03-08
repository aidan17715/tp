package seedu.sudocook;

public class Parser {
    private final Ui ui;

    public Parser(Ui ui) {
        this.ui = ui;
    }

    public void run() {
        ui.printWelcome();
        while (true) {
            String input = ui.readInput();
            if (input.equals("bye")) {
                ui.printBye();
                break;
            } else {
                ui.printError("I don't recognise that command!");
            }
        }
    }
}

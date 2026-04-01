package seedu.sudocook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import seedu.sudocook.util.AnsiColor;


public class Ui {
    // All static variables first
    private static final String DIVIDER = "____________________________________________________________";
    private static final String INDENT = "    ";

    // Instance variables after static variables
    private final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    public static void printLine() {
        System.out.println(INDENT + AnsiColor.cyan(DIVIDER));
    }

    //Formats and prints a response message wrapped between two divider lines.
    public static void formatResponse(String message) {
        System.out.println("");
        for (String line : message.split("\r?\n")) {
            if (line.isEmpty()) {
                System.out.println();
                continue;
            }
            System.out.println(INDENT + " " + line);
        }
        printLine();
    }

    public static String getGradientText(String text, int r1, int g1, int b1, int r2, int g2, int b2) {
        return AnsiColor.gradient(text, r1, g1, b1, r2, g2, b2);
    }

    public static void printWelcome() {
        System.out.println("");
        System.out.println(INDENT + AnsiColor.cyan("Welcome to..."));
        String logo = 
            INDENT + "  ____            _           ____             _    \n" +
            INDENT + " / ___| _   _  __| | ___     / ___|___   ___  | | __\n" +
            INDENT + " \\___ \\| | | |/ _` |/ _ \\   | |   / _ \\ / _ \\ | |/ /\n" +
            INDENT + "  ___) | |_| | (_| | (_) |  | |__| (_) | (_) ||   < \n" +
            INDENT + " |____/ \\__,_|\\__,_|\\___/    \\____\\___/ \\___/ |_|\\_\\";
        
        String[] lines = logo.split("\n");
        for (String line : lines) {
            System.out.println(getGradientText(line, 190, 80, 255, 50, 200, 255)); // Purple to Cyan
        }
        printLine();
    }

    public static void printBye() {
        printMessage("Goodbye! Happy cooking!");
    }

    public static void printError(String message) {
        formatResponse(AnsiColor.red("Oops! " + message));
    }

    public static void printMessage(String message) {
        printGradientMessage(message);
    }

    public static void printGradientMessage(String message) {
        System.out.println("");
        for (String line : message.split("\r?\n")) {
            if (line.isEmpty()) {
                System.out.println();
                continue;
            }
            if (line.contains("\u001B")) {
                System.out.println(INDENT + " " + line);
            } else {
                String gradientLine = getGradientText(line, 190, 80, 255, 50, 200, 255);
                System.out.println(INDENT + " " + gradientLine);
            }
        }
        printLine();
    }

    public String readInput() {
        try {
            System.out.print(INDENT + "> ");
            String line = in.readLine();
            if (line == null) {
                // EOF reached (e.g., during IO redirection tests)
                return "bye";
            }
            return line.trim();
        } catch (IOException e) {
            return "bye";
        }
    }
}

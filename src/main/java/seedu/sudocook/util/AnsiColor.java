package seedu.sudocook.util;

/**
 * Utility class for handling ANSI color codes.
 * Only applies colors when running in an interactive terminal (System.console() != null).
 * This ensures text-ui-tests pass by not including escape sequences in redirected output.
 */
public class AnsiColor {
    private static final boolean ANSI_ENABLED = System.console() != null;

    // Color codes
    public static final String RESET = ANSI_ENABLED ? "\u001B[0m" : "";
    public static final String RED = ANSI_ENABLED ? "\u001B[31m" : "";
    public static final String CYAN = ANSI_ENABLED ? "\u001B[36m" : "";

    /**
     * Creates a cyan colored string (if ANSI is enabled).
     */
    public static String cyan(String text) {
        return CYAN + text + RESET;
    }

    /**
     * Creates a red colored string (if ANSI is enabled).
     */
    public static String red(String text) {
        return RED + text + RESET;
    }

    /**
     * Creates a gradient colored string using RGB values (if ANSI is enabled).
     */
    public static String gradient(String text, int r1, int g1, int b1, int r2, int g2, int b2) {
        if (!ANSI_ENABLED) {
            return text;
        }
        
        StringBuilder sb = new StringBuilder();
        int len = text.length();
        for (int i = 0; i < len; i++) {
            double ratio = (double) i / (len > 1 ? len - 1 : 1);
            int r = (int) (r1 + ratio * (r2 - r1));
            int g = (int) (g1 + ratio * (g2 - g1));
            int b = (int) (b1 + ratio * (b2 - b1));
            sb.append(String.format("\u001B[38;2;%d;%d;%dm", r, g, b));
            sb.append(text.charAt(i));
        }
        sb.append(RESET);
        return sb.toString();
    }

    /**
     * Checks if ANSI colors are enabled.
     */
    public static boolean isEnabled() {
        return ANSI_ENABLED;
    }
}

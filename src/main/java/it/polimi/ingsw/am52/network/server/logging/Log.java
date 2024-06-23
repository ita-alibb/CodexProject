package it.polimi.ingsw.am52.network.server.logging;

import it.polimi.ingsw.am52.settings.VerbosityLevel;

public class Log {

    /**
     * The verbosity level of this log.
     */
    private final VerbosityLevel verbosityLevel;

    /**
     * Create a new log instance, with the specified verbosity level.
     * @param verbosity The verbosity level of the log.
     */
    public Log(VerbosityLevel verbosity) {
        this.verbosityLevel = verbosity;
    }

    /**
     *
     * @return The verbosity level of this log.
     */
    public VerbosityLevel getVerbosityLevel() {
        return this.verbosityLevel;
    }

    /**
     * Print a message to the console, if the message verbosity level
     * is lower or equal to the verbosity level of this log object.
     * @param message The log message to display.
     */
    public void printMessage(LogMessage message) {
        if (getVerbosityValue(message.level()) <= getVerbosityValue(getVerbosityLevel())) {
            System.out.println(message);
        }
    }

    /**
     * Convert VerbosityLevel enum to an integer.
     * @param verbosity The VerbosityLevel enum value.
     * @return The integer value corresponding to the verbosity level.
     */
    private static int getVerbosityValue(VerbosityLevel verbosity) {
        return switch (verbosity) {
            case ERROR -> 1;
            case WARNING -> 2;
            case INFO -> 3;
            case VERBOSE -> 4;
        };
    }
}

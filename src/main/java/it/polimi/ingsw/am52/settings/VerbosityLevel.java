package it.polimi.ingsw.am52.settings;

public enum VerbosityLevel {
    VERBOSE,
    INFO,
    WARNING,
    ERROR;

    public static VerbosityLevel parse(String value) {
        return switch (value.toLowerCase()) {
            case "verbose" -> VerbosityLevel.VERBOSE;
            case "info" -> VerbosityLevel.INFO;
            case "warning" -> VerbosityLevel.WARNING;
            case "error" -> VerbosityLevel.ERROR;
            default -> throw new IllegalArgumentException();
        };
    }
}

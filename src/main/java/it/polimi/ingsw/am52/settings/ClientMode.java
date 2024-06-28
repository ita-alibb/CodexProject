package it.polimi.ingsw.am52.settings;

/**
 * Running mode of client application.
 */
public enum ClientMode {
    /**
     * GUI mode.
     */
    GRAPHICAL,
    /**
     * TUI mode.
     */
    TEXTUAL;

    /**
     * Method to select the type of UI
     * @param value     The selected UI type
     * @return          The converted UI type
     */
    public static ClientMode parse(String value) {
        return switch (value.toLowerCase()) {
            case "gui" -> ClientMode.GRAPHICAL;
            case "tui" -> ClientMode.TEXTUAL;
            default -> throw new IllegalArgumentException();
        };
    }
}

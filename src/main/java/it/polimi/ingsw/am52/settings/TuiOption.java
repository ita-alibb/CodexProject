package it.polimi.ingsw.am52.settings;

/**
 * Represent the tui option for client application.
 */
public class TuiOption extends Option {

    public static final String SHORT_FLAG = "-t";
    public static final String LONG_FLAG = "--tui";

    public TuiOption() {
        super(SHORT_FLAG, LONG_FLAG);
    }

    @Override
    public Object parseValueText(String text) throws IllegalArgumentException {
        throw new IllegalArgumentException("The tui option does not require any argument.");
    }

    @Override
    public String getDescription() {
        return String.format("%s/%s: run client in textual mode (tui), instead of graphical mode (gui).",
                getShortFlag(), getLongFlag());
    }
}

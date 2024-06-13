package it.polimi.ingsw.am52.settings;

/**
 * Represent the help option fo the application, which indicates to display
 * the help on the console, but do not run the application itself.
 * @author Livio B.
 */
public class HelpOption extends Option {

    public static final String SHORT_FLAG = "-h";
    public static final String LONG_FLAG = "--help";

    public HelpOption() {
        super(SHORT_FLAG, LONG_FLAG);
    }

    @Override
    public Object parseValueText(String text) throws IllegalArgumentException {
        throw new IllegalArgumentException("The help option does not require any argument.");
    }

    @Override
    public String getDescription() {
        return String.format("%s/%s: display the usage help for the application.",
                getShortFlag(), getLongFlag());
    }
}

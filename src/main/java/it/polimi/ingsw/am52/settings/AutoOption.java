package it.polimi.ingsw.am52.settings;

/**
 * Represent the auto option for server application.
 */
public class AutoOption extends Option {

    public static final String SHORT_FLAG = "-a";
    public static final String LONG_FLAG = "--auto";

    public AutoOption() {
        super(SHORT_FLAG, LONG_FLAG);
    }

    @Override
    public Object parseValueText(String text) throws IllegalArgumentException {
        throw new IllegalArgumentException("The auto option does not require any argument.");
    }

    @Override
    public String getDescription() {
        return String.format("%s/%s: enable automatic selection of an available port. If the port number is specified, then the server first trys to use that port.",
                getShortFlag(), getLongFlag());
    }
}

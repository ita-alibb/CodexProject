package it.polimi.ingsw.am52.settings;

/**
 * Represent the fixed option for server application. If this option
 * is set on the command line, the server only uses the specified port
 * number for the connection.
 * @author Livio B.
 */
public class FixedPortOption extends Option {

    public static final String SHORT_FLAG = "-f";
    public static final String LONG_FLAG = "--fixed";

    public FixedPortOption() {
        super(SHORT_FLAG, LONG_FLAG);
    }

    @Override
    public Object parseValueText(String text) throws IllegalArgumentException {
        throw new IllegalArgumentException("The fixed option does not require any argument.");
    }

    @Override
    public String getDescription() {
        return String.format("%s/%s: force the server to use only the specified port number for the connection.",
                getShortFlag(), getLongFlag());
    }
}

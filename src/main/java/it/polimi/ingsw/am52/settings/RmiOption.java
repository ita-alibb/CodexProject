package it.polimi.ingsw.am52.settings;

/**
 * Represent the rmi option fo the client, enable the RMI mode of the client.
 */
public class RmiOption extends Option {

    public static final String SHORT_FLAG = "-r";
    public static final String LONG_FLAG = "--rmi";

    public RmiOption() {
        super(SHORT_FLAG, LONG_FLAG);
    }

    @Override
    public Object parseValueText(String text) throws IllegalArgumentException {
        throw new IllegalArgumentException("The rmi option does not require any argument.");
    }

    @Override
    public String getDescription() {
        return String.format("%s/%s: set RMI port number for the server.",
                getShortFlag(), getLongFlag());
    }
}

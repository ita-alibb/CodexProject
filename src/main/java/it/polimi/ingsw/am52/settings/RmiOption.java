package it.polimi.ingsw.am52.settings;

/**
 * Represent the rmi option for server and client application.
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
        return String.format("%s/%s: use RMI network for communication.",
                getShortFlag(), getLongFlag());
    }
}

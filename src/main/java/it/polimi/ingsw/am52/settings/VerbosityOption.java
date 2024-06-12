package it.polimi.ingsw.am52.settings;

/**
 * Represent the verbosity option of the server.
 * @author Livio B.
 */
public class VerbosityOption extends Option<VerbosityLevel> {

    public static final String SHORT_FLAG = "-v";
    public static final String LONG_FLAG = "--verbosity";

    //region Public Static Final Fields

    /**
     * Max allowed verbosity level.
     */
    public static final int MAX_VERBOSITY_LVL = 4;
    /**
     * mIn allowed verbosity level.
     */
    public static final int MIN_VERBOSITY_LVL = 1;

    //endregion

    //region Constructor

    public VerbosityOption() {
        super(SHORT_FLAG, LONG_FLAG, true);
    }

    //endregion

    /**
     * Parse the text of the verbosity argument, and return the relative
     * value of the enumeration. The text of the argument must be an integer
     * value, in the range [1, 4].
     * @param text The text to parse
     * @return The verbosity enum value.
     * @throws IllegalArgumentException If the text is not formatted for an integer,
     * or the value is out of the allowed range.
     */
    @Override
    public VerbosityLevel parseValueText(String text) throws IllegalArgumentException {

        try {
            final int verbosityLevel = Integer.parseInt(text);
            return switch (verbosityLevel) {
                case 1 -> VerbosityLevel.ERROR;
                case 2 -> VerbosityLevel.WARNING;
                case 3 -> VerbosityLevel.INFO;
                case 4 -> VerbosityLevel.VERBOSE;
                default -> throw new IllegalArgumentException(
                      String.format(
                              "Invalid value %d for verbosity option. Range is [%d, %d].",
                              verbosityLevel, MIN_VERBOSITY_LVL, MAX_VERBOSITY_LVL
                      )
                );
            };
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(
                    String.format("Invalid value \"%s\" for verbosity option.", text),ex
            );
        }
    }

    @Override
    public String getDescription() {
        return String.format("%s/%s <lvl>: set the verbosity level for the server logging to <lvl> value. This option require a integer value <lvl>, in range [%d, %d]",
                getShortFlag(), getLongFlag(), MIN_VERBOSITY_LVL, MAX_VERBOSITY_LVL);
    }
}

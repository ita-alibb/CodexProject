package it.polimi.ingsw.am52.settings;

/**
 * Represent the verbosity option of the server
 */
public class LimitOption extends Option<Integer> {

    public static final String SHORT_FLAG = "-l";
    public static final String LONG_FLAG = "--limit";

    //region Public Static Final Fields

    /**
     * Max number of concurrent lobbies on the server.
     */
    public static final int MAX_LOBBIES = ServerSettings.MAX_LOBBIES;
    /**
     * Min number of concurrent lobbies on the server.
     */
    public static final int MIN_LOBBIES = ServerSettings.MIN_LOBBIES;;

    //endregion

    //region Constructor

    public LimitOption() {
        super(SHORT_FLAG, LONG_FLAG, true);
    }

    //endregion

    /**
     * Parse the text of the limit argument, and return the relative
     * value of the maximum allowed lobbies. The text of the argument must be an integer
     * value, in the range [MIN_LOBBIES, MAX_LOBBIES].
     * @param text The text to parse
     * @return The integer value representing the limit of concurrent lobbies on the server.
     * @throws IllegalArgumentException If the text is not formatted for an integer,
     * or the value is out of the allowed range.
     */
    @Override
    public Integer parseValueText(String text) throws IllegalArgumentException {

        try {
            final int maxLobbies = Integer.parseInt(text);
            if (maxLobbies < MIN_LOBBIES || maxLobbies > MAX_LOBBIES)
            {
                throw new IllegalArgumentException(
                      String.format(
                              "Invalid value %d for limit option. Range is [%d, %d].",
                              maxLobbies, MIN_LOBBIES, MAX_LOBBIES
                      )
                );
            }
            return maxLobbies;

        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(
                    String.format("Invalid value \"%s\" for limit option.", text),ex
            );
        }
    }

    @Override
    public String getDescription() {
        return String.format("%s/%s <max>: limit the maximum concurrent games on the server to the specified <max> value. This option require a integer value <max>, in range [%d, %d]",
                getShortFlag(), getLongFlag(), MIN_LOBBIES, MAX_LOBBIES);
    }
}

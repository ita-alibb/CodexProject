package it.polimi.ingsw.am52.settings;

import java.util.Optional;

/**
 * The object that represents the command line arguments of the application.
 */
public class CmdLineArgs {

    //region Private Fields

    /**
     * Whether to show only the help message, without running the application.
     */
    private final boolean showHelp;

    /**
     * The application startup settings. If the isHelp() method return true,
     * this is an empty optional field.
     */
    private final Optional<ApplicationSettings> appSettings;

    //endregion

    //region Publix Static Methods

    /**
     *
     * @return An object indicating that the user required to show the
     * help message, without running the application.
     */
    public static CmdLineArgs getShowHelpSettings() {
        return new CmdLineArgs();
    }

    //endregion

    //region Constructors

    /**
     * Create an object with the settings to run the application.
     * @param settings The application startup settings.
     */
    public CmdLineArgs(ApplicationSettings settings) {
        this.showHelp = false;
        this.appSettings = Optional.of(settings);
    }

    /**
     * Create an object that indicates to show the app help, but
     * don't run the application itself.
     */
    private CmdLineArgs () {
        this.showHelp = true;
        this.appSettings = Optional.empty();
    }

    //endregion

    //region Getters

    /**
     *
     * @return True if the user required to show the help message,
     * without running the application itself.
     */
    public boolean showHelp() {
        return  this.showHelp;
    }

    /**
     * If the user did not require to show the help message,
     * get the startup settings.
     * @return The application startup settings.
     */
    public ApplicationSettings getSettings() {
        return this.appSettings.orElseThrow();
    }

    //endregion
}

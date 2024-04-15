package it.polimi.ingsw.am52.settings;

import java.util.Optional;

/**
 * The settings for running the application.
 */
public class ApplicationSettings {

    //region Private Fields

    /**
     * The running mode of the application (Server, or Client).
     */
    private final Mode mode;

    /**
     * The setting of the application, if it is used as client.
     */
    private final Optional<ClientSettings> clientSettings;

    //endregion

    //region Constructors

    /**
     * Create an object with the specified settings, for running
     * the application in client mode.
     * @param clientSettings The client settings.
     */
    public ApplicationSettings(ClientSettings clientSettings) {
        // Set client mode.
        this.mode = Mode.CLIENT;
        // Set client settings.
        this.clientSettings = Optional.of(clientSettings);
    }

    //endregion

    //region Getters

    /**
     *
     * @return The running mode of this application (Server, or Client).
     */
    public Mode getMode() {
        return this.mode;
    }

    public ClientSettings getClientSettings() {
        if (getMode().equals(Mode.CLIENT)) {
            return this.clientSettings.get();
        }

        throw new IllegalStateException(
                String.format("Cannot get Server settings for running mode %s", getMode()));
    }

    //endregion
}

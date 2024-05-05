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

    /**
     * The setting of the application, if it is used as client.
     */
    private final Optional<ServerSettings> serverSettings;

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
        // The server settings are empty.
        this.serverSettings = Optional.empty();
        // Set client settings.
        this.clientSettings = Optional.of(clientSettings);
    }

    /**
     * Create an object with the specified settings, for running
     * the application in server mode.
     * @param serverSettings The server settings.
     */
    public ApplicationSettings(ServerSettings serverSettings) {
        // Set client mode.
        this.mode = Mode.SERVER;
        // The server settings.
        this.serverSettings = Optional.of(serverSettings);
        // Set client settings are empty.
        this.clientSettings = Optional.empty();
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

    /**
     *
     * @return The server settings, if the application mode is set to SERVER.
     * @throws IllegalStateException If the application mode is set to CLIENT.
     */
    public ServerSettings getServerSettings() {
        if (getMode().equals(Mode.SERVER)) {
            return this.serverSettings.get();
        }

        throw new IllegalStateException(
                String.format("Cannot get Server settings for running mode %s", getMode()));
    }

    /**
     *
     * @return The client settings, if the application mode is set to CLIENT.
     * @throws IllegalStateException If the application mode is set to SERVER.
     */
    public ClientSettings getClientSettings() {
        if (getMode().equals(Mode.CLIENT)) {
            return this.clientSettings.get();
        }

        throw new IllegalStateException(
                String.format("Cannot get Client settings for running mode %s", getMode()));
    }

    //endregion
}

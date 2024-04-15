package it.polimi.ingsw.am52.settings;

public class ClientSettings {

    //region Private Fields

    /**
     * The ip address of the server to connect with.
     */
    private final String ip;
    /**
     * The number of port for the connection.
     */
    private final int port;

    /**
     * The running mode of the client (GUI or TUI)
     */
    private final ClientMode mode;

    /**
     * The network mode (Socket or RMI).
     */
    private final NetworkMode network;

    //endregion

    //region Constructor

    /**
     * Creates an object with the specified client settings.
     * @param ip The ip address of the server to connect with.
     * @param port The number of port used for the connection.
     * @param mode The running mode of the client (GUI or TUI).
     * @param network The network mode (Socket or RMI).
     */
    public ClientSettings(String ip, int port, ClientMode mode, NetworkMode network) {
        this.ip = ip;
        this.port = port;
        this.mode = mode;
        this.network = network;
    }

    //endregion

    //region Getters

    /**
     *
     * @return The ip address of the server where to connect.
     */
    public String getIp() {
        return ip;
    }

    /**
     *
     * @return The number of port for the connection.
     */
    public int getPort() {
        return port;
    }

    /**
     *
     * @return The client mode: Graphical or Textual.
     */
    public ClientMode getMode() {
        return mode;
    }

    /**
     *
     * @return The network: Socket (json) or RMI.
     */
    public NetworkMode getNetwork() {
        return network;
    }


    //endregion
}

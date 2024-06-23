package it.polimi.ingsw.am52.network.server.logging;

import it.polimi.ingsw.am52.settings.VerbosityLevel;

public record LogMessage(String message, VerbosityLevel level) {
    @Override
    public String toString() {
        return String.format("%-8s: %s", level(), message());
    }
}

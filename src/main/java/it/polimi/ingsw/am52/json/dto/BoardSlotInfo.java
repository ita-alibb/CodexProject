package it.polimi.ingsw.am52.json.dto;

import com.fasterxml.jackson.annotation.JsonGetter;

/**
 * Interface created for serialization/deserialization purpose
 */
public interface BoardSlotInfo {
    @JsonGetter("h")
    int getHoriz();
    @JsonGetter("v")
    int getVert();
}

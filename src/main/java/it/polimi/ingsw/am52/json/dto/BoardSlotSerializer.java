package it.polimi.ingsw.am52.json.dto;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import it.polimi.ingsw.am52.model.playingBoards.BoardSlot;

import java.io.IOException;
import java.util.List;

public class BoardSlotSerializer extends JsonSerializer<List<BoardSlot>> {
    /**
     * Custom method to serialize correctly a list of BoardSlot
     */
    @Override
    public void serialize(List<BoardSlot> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartArray();
        for (BoardSlot slot : value) {
            gen.writeStartObject();
            gen.writeObjectField("h", slot.getHoriz());
            gen.writeObjectField("v", slot.getVert());
            gen.writeEndObject();
        }
        gen.writeEndArray();
    }
}

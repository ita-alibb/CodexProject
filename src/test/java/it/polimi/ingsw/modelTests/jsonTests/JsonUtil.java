package it.polimi.ingsw.modelTests.jsonTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Utility class for json serialization and deserialization tests.
 * @author Livio B.
 */
public class JsonUtil {

    /**
     * Check that the specified json node has the specified String value
     * for the specified field.
     * @param node The json node.
     * @param fieldName The field name.
     * @param value The String value of the node's field to check.
     * @author Livio B.
     */
    public static void checkNodeFiledStringValue(JsonNode node, String fieldName, String value) {
        assertEquals(value, node.get(fieldName).asText());
    }

    /**
     * Check that the specified json node has the specified integer value
     * for the specified field.
     * @param node The json node.
     * @param fieldName The field name.
     * @param value The integer value of the node's field to check.
     * @author Livio B.
     */
    public static void checkNodeFiledIntValue(JsonNode node, String fieldName, int value) {
        assertEquals(value, node.get(fieldName).asInt());
    }

    /**
     * Check that the specified json node has the specified boolean value
     * for the specified field.
     * @param node      The json node
     * @param fieldName The field name
     * @param value     The boolean value of the node's field to check
     */
    public static void checkNodeFiledBooleanValue(JsonNode node, String fieldName, boolean value) {
        assertEquals(value, node.get(fieldName).asBoolean());
    }

    /**
     * Check that the specified json node has all and only the specified fields.
     * @param node The json node to check.
     * @param fieldNames The list of field names to check.
     * @author Livio B.
     */
    public static void checkNodeFieldNames(JsonNode node, String... fieldNames) {

        // Collect all json object's fields in a Set<String>.
        Set<String> fields = new HashSet<>();
        // Get the iterator able to iterate over all json fields, and overwrite settings, if found.
        Iterator<String> iter = node.fieldNames();
        while (iter.hasNext()) {
            fields.add(iter.next());
        }

        // Check there are two fields, named "method" and "data".
        assertEquals(fieldNames.length, fields.size());
        for (String field : fieldNames) {
            assertTrue(fields.contains(field));
        }

    }

    /**
     * Get a json node object, from the specified json text string.
     * @param jsonText The json text string.
     * @return The json node object.
     * @author Livio B.
     */
    public static JsonNode getJsonNode(String jsonText) {
        // Create the object mapper.
        ObjectMapper objectMapper = new ObjectMapper();
        // Get the json node object.
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(jsonText);
        } catch (JsonProcessingException ex) {
            assert(false);
        }
        return jsonNode;
    }
}

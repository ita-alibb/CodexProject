package it.polimi.ingsw.settingsTests.optionsTests;

import it.polimi.ingsw.am52.settings.VerbosityLevel;
import it.polimi.ingsw.am52.settings.VerbosityOption;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for the class VerbosityOption.
 */
public class VerbosityOptionTest
{
    /**
     * Test short flag and long flag.
     */
    @Test
    @DisplayName("Verbosity option flags test")
    public void testFlags()
    {
        VerbosityOption option = new VerbosityOption();

        assertEquals("-v", option.getShortFlag());
        assertEquals("--verbosity", option.getLongFlag());

        assertTrue(option.validateOptionFlag("-v"));
        assertTrue(option.validateOptionFlag("--verbosity"));

        assertTrue(option.requiresValue());
    }
    /**
     * Test the parseOptionText method.
     */
    @Test
    @DisplayName("VerbosityOption: parseOptionText() test")
    public void testParse()
    {
        // Check correct values.
        checkParse(VerbosityLevel.ERROR, "1");
        checkParse(VerbosityLevel.WARNING, "2");
        checkParse(VerbosityLevel.INFO, "3");
        checkParse(VerbosityLevel.VERBOSE, "4");

        // Check final static fields.
        String text = String.format("%d", VerbosityOption.MIN_VERBOSITY_LVL);
        checkParse(VerbosityLevel.ERROR, text);
        text = String.format("%d", VerbosityOption.MAX_VERBOSITY_LVL);
        checkParse(VerbosityLevel.VERBOSE, text);

        // Check out of range values.
        text = String.format("%d", VerbosityOption.MIN_VERBOSITY_LVL - 1);
        checkParseThrows(text);
        text = String.format("%d", VerbosityOption.MAX_VERBOSITY_LVL + 1);
        checkParseThrows(text);

        // Check not an integer number text.
        text = "a";
        checkParseThrows(text);
        text = "2.3";
        checkParseThrows(text);
    }

    private static void checkParseThrows(String text) {
        VerbosityOption option = new VerbosityOption();
        assertThrows(IllegalArgumentException.class, () -> option.parseValueText(text));
    }

    private static void checkParse(VerbosityLevel expected, String optionText) {
        VerbosityOption option = new VerbosityOption();
        assertEquals(expected, option.parseValueText(optionText));
    }
}

package it.polimi.ingsw.settingsTests.optionsTests;

import it.polimi.ingsw.am52.settings.LimitOption;
import it.polimi.ingsw.am52.settings.ServerSettings;
import it.polimi.ingsw.am52.settings.VerbosityLevel;
import it.polimi.ingsw.am52.settings.VerbosityOption;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for the class LimitOption.
 */
public class LimitOptionTest
{
    /**
     * Test short flag and long flag.
     */
    @Test
    @DisplayName("Limit option flags test")
    public void testFlags()
    {
        LimitOption option = new LimitOption();

        assertEquals("-l", option.getShortFlag());
        assertEquals("--limit", option.getLongFlag());

        assertTrue(option.validateOptionFlag("-l"));
        assertTrue(option.validateOptionFlag("--limit"));

        assertTrue(option.requiresValue());
    }
    /**
     * Test the parseOptionText method.
     */
    @Test
    @DisplayName("LimitOption: parseOptionText() test")
    public void testParse()
    {
        // Check correct values.
        checkParse(1, "1");
        checkParse(2, "2");
        checkParse(302, "302");
        checkParse(ServerSettings.DEF_MAX_LOBBIES, "1000");

        // Check final static fields.
        String text = String.format("%d", LimitOption.MIN_LOBBIES);
        checkParse(1, text);
        text = String.format("%d", LimitOption.MAX_LOBBIES);
        checkParse(10000, text);

        // Check out of range values.
        text = String.format("%d", LimitOption.MIN_LOBBIES - 1);
        checkParseThrows(text);
        text = String.format("%d", LimitOption.MAX_LOBBIES + 1);
        checkParseThrows(text);

        // Check not an integer number text.
        text = "a";
        checkParseThrows(text);
        text = "2.3";
        checkParseThrows(text);
    }

    private static void checkParseThrows(String text) {
        LimitOption option = new LimitOption();
        assertThrows(IllegalArgumentException.class, () -> option.parseValueText(text));
    }

    private static void checkParse(int expected, String optionText) {
        LimitOption option = new LimitOption();
        assertEquals(expected, option.parseValueText(optionText));
    }
}

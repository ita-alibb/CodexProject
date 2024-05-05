package it.polimi.ingsw.settingsTests.optionsTests;

import it.polimi.ingsw.am52.settings.TuiOption;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for the class TuiOption.
 */
public class TuiOptionTest
{
    /**
     * Test short flag and long flag.
     */
    @Test
    @DisplayName("Tui option flags test")
    public void testFlags()
    {
        TuiOption option = new TuiOption();

        assertEquals("-t", option.getShortFlag());
        assertEquals("--tui", option.getLongFlag());

        assertTrue(option.validateOptionFlag("-t"));
        assertTrue(option.validateOptionFlag("--tui"));

        assertFalse(option.requiresValue());
    }

    /**
     * Test the parseOptionText method.
     */
    @Test
    @DisplayName("TuiOption: parseOptionText() test")
    public void testParse()
    {
        TuiOption option = new TuiOption();

        // The parseOptionText() always throws, because there isn't any
        // required argument for this option.
        assertThrows(IllegalArgumentException.class, () -> option.parseValueText("1"));
        assertThrows(IllegalArgumentException.class, () -> option.parseValueText(""));
        assertThrows(IllegalArgumentException.class, () -> option.parseValueText("t"));
    }
}

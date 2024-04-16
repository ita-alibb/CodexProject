package it.polimi.ingsw.settingsTests.optionsTests;

import it.polimi.ingsw.am52.settings.LimitOption;
import it.polimi.ingsw.am52.settings.RmiOption;
import it.polimi.ingsw.am52.settings.ServerSettings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for the class RmiOption.
 */
public class RmiOptionTest
{
    /**
     * Test short flag and long flag.
     */
    @Test
    @DisplayName("Rmi option flags test")
    public void testFlags()
    {
        RmiOption option = new RmiOption();

        assertEquals("-r", option.getShortFlag());
        assertEquals("--rmi", option.getLongFlag());

        assertTrue(option.validateOptionFlag("-r"));
        assertTrue(option.validateOptionFlag("--rmi"));

        assertFalse(option.requiresValue());
    }

    /**
     * Test the parseOptionText method.
     */
    @Test
    @DisplayName("RmiOption: parseOptionText() test")
    public void testParse()
    {
        RmiOption option = new RmiOption();

        // The parseOptionText() always throws, because there isn't any
        // required argument for this option.
        assertThrows(IllegalArgumentException.class, () -> option.parseValueText("1"));
        assertThrows(IllegalArgumentException.class, () -> option.parseValueText(""));
        assertThrows(IllegalArgumentException.class, () -> option.parseValueText("r"));
    }
}

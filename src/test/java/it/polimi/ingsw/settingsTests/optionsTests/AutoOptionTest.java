package it.polimi.ingsw.settingsTests.optionsTests;

import it.polimi.ingsw.am52.settings.AutoOption;
import it.polimi.ingsw.am52.settings.FixedPortOption;
import it.polimi.ingsw.am52.settings.CmdLineArgsReader;
import it.polimi.ingsw.am52.settings.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for the class AutoOption.
 */
public class AutoOptionTest
{
    /**
     * Test short flag and long flag.
     */
    @Test
    @DisplayName("Auto option flags test")
    public void testFlags()
    {
        AutoOption option = new AutoOption();

        assertEquals("-a", option.getShortFlag());
        assertEquals("--auto", option.getLongFlag());

        assertTrue(option.validateOptionFlag("-a"));
        assertTrue(option.validateOptionFlag("--auto"));

        assertFalse(option.requiresValue());

        for (Option otp : CmdLineArgsReader.getServerOptions()) {
            System.out.println(otp.getDescription());
        }
    }

    /**
     * Test the parseOptionText method.
     */
    @Test
    @DisplayName("AutoOption: parseOptionText() test")
    public void testParse()
    {
        FixedPortOption option = new FixedPortOption();

        // The parseOptionText() always throws, because there isn't any
        // required argument for this option.
        assertThrows(IllegalArgumentException.class, () -> option.parseValueText("1"));
        assertThrows(IllegalArgumentException.class, () -> option.parseValueText(""));
        assertThrows(IllegalArgumentException.class, () -> option.parseValueText("r"));
    }
}

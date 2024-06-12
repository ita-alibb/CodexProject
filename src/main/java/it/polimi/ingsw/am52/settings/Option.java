package it.polimi.ingsw.am52.settings;

import java.util.Objects;

/**
 * Represent a command line option. Any option has a short flag
 * (e.g. "-h") and a long flag (e.g. "--help"), and can require
 * a value.
 * @param <TValue> The type of the option value, if required.
 * @author Livio B.
 */
public abstract class Option<TValue> {

    //region Private Fields

    /**
     * The short flag for this option.
     */
    private final String shortFlag;
    /**
     * The long flag of this option.
     */
    private final String longFlag;

    /**
     * Whether this option requires a value to be specified at the
     * command line.
     */
    private final boolean requiresValue;

    //endregion

    //region Constructor

    /**
     * Creates an option with the specified short flag and long flag,
     * that does not require any value.
     * @param shortFlag The short flag of the option.
     * @param longFlag The long flag of the option.
     */
    protected Option(String shortFlag, String longFlag) {
        this(shortFlag, longFlag, false);
    }

    /**
     * Crates an option with th specified short flag and long flag,
     * and that can require a value.
     * @param shortFlag The short flag of the option.
     * @param longFlag The long flag of the option.
     * @param requiresValue Whether the option requires a value.
     */
    protected Option(String shortFlag, String longFlag, boolean requiresValue) {
        this.shortFlag = shortFlag;
        this.longFlag = longFlag;
        this.requiresValue = requiresValue;
    }

    // endregion

    //region Public Methods

    /**
     * Check if the specified text is a valid flag for this option.
     * @param flag The text to validate.
     * @return True if is a valid flag for this option, false otherwise.
     */
    public boolean validateOptionFlag(String flag) {
        return flag.equals(getShortFlag()) || flag.equals(getLongFlag());
    }

    /**
     * Parse the text representing the value of this option.
     * @param text The text of the value to parse.
     * @return The value.
     * @throws IllegalArgumentException If the text cannot be parsed, or this option
     * does not require nau value.
     */
    public abstract TValue parseValueText(String text) throws IllegalArgumentException;

    //endregion

    //region Getters

    /**
     *
     * @return True if this option requires a value, false otherwise.
     */
    public boolean requiresValue() {
        return this.requiresValue;
    }

    /**
     *
     * @return The text of the short flag of this option.
     */
    public String getShortFlag() {
        return this.shortFlag;
    }

    /**
     *
     * @return The text of the long flag of this option.
     */
    public String getLongFlag() {
        return this.longFlag;
    }

    /**
     *
     * @return The description of this option.
     */
    public abstract String getDescription();

    //endregion

    //region Objetct Overrides

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Option<?> option = (Option<?>) o;
        return Objects.equals(shortFlag, option.shortFlag) && Objects.equals(longFlag, option.longFlag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shortFlag, longFlag);
    }

    //endregion
}

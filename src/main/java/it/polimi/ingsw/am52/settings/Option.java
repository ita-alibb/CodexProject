package it.polimi.ingsw.am52.settings;

import java.util.Objects;

public abstract class Option<TValue> {

    private String shortFlag;
    private String longFlag;

    private boolean requiresValue;

    protected Option(String shortFlag, String longFlag) {
        this(shortFlag, longFlag, false);
    }

    protected Option(String shortFlag, String longFlag, boolean requiresValue) {
        this.shortFlag = shortFlag;
        this.longFlag = longFlag;
        this.requiresValue = requiresValue;
    }

    public boolean validateOptionFlag(String flag) {
        return flag.equals(getShortFlag()) || flag.equals(getLongFlag());
    }

    public abstract TValue parseValueText(String text) throws IllegalArgumentException;

    public boolean requiresValue() {
        return this.requiresValue;
    }

    public String getShortFlag() {
        return this.shortFlag;
    }

    public String getLongFlag() {
        return this.longFlag;
    }

    public abstract String getDescription();

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
}

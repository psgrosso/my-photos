package model.attribute;


import org.jetbrains.annotations.NotNull;

import java.util.Objects;


/**
 * An immutable attribute-value pair
 */
public final class Value implements Comparable<Value> {
    private final Attribute attribute;
    private final String stringValue;
    private final int integerValue;
    private final long longValue;
    // Pre-calculated hash for equals improvement, it can be done this way since this object is immutable
    private final int hash;

    public Value(@NotNull Attribute attribute, @NotNull String value) {
        this.attribute = attribute;
        stringValue = value;
        integerValue = 0;
        longValue = 0L;
        hash = (17 + stringValue.hashCode()) * 31 + attribute.hashCode();
        checkStringValue();
    }

    public Value(@NotNull Attribute attribute, int value) {
        this.attribute = attribute;
        integerValue = value;
        stringValue = null;
        longValue = 0L;
        hash = (17 + integerValue) * 31 + attribute.hashCode();
        checkIntegerValue();
    }

    public Value(@NotNull Attribute attribute, long value) {
        this.attribute = attribute;
        longValue = value;
        stringValue = null;
        integerValue = 0;
        hash = (17 + (int)(longValue ^ longValue >>> 32)) * 31 + attribute.hashCode();
        checkLongValue();
    }

    public String getString() {
        checkStringValue();
        return stringValue;
    }

    public int getInteger() {
        checkIntegerValue();
        return integerValue;
    }

    public long getLong() {
        checkLongValue();
        return longValue;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    @Override
    public int compareTo(@NotNull Value that) {
        if (!attribute.equals(that.attribute)) {
            throw new IllegalArgumentException("Invalid Value: " + that);
        }
        switch (attribute.getType()) {
            case STRING:
                return stringValue.compareToIgnoreCase(that.stringValue);
            case INTEGER:
                return integerValue - that.integerValue;
            default:
                long result = longValue - that.longValue;
                return result == 0 ? 0 : result > 0 ? 1 : -1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Value value = (Value) o;

        // Fail quick
        return hash == value.hash && attribute.equals(value.attribute) &&
                Objects.equals(stringValue, value.stringValue) &&
                integerValue == value.integerValue &&
                longValue == value.longValue;
    }

    @Override
    public int hashCode() {
        return hash;
    }

    @Override
    public String toString() {
        return "Value{" +
                "attribute=" + attribute +
                ", stringValue='" + stringValue + '\'' +
                ", integerValue=" + integerValue +
                ", longValue=" + longValue +
                '}';
    }

    private void checkStringValue() {
        if (attribute.getType() != Attribute.Type.STRING) {
            throw new IllegalArgumentException("Attribute " + attribute + " is not String");
        }
    }

    private void checkIntegerValue() {
        if (attribute.getType() != Attribute.Type.INTEGER) {
            throw new IllegalArgumentException("Attribute " + attribute + " is not Integer");
        }
    }

    private void checkLongValue() {
        if (attribute.getType() != Attribute.Type.LONG) {
            throw new IllegalArgumentException("Attribute " + attribute + " is not Long");
        }
    }
}

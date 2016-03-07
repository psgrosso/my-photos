package model.attribute;


import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class Value implements Comparable<Value> {
    private Attribute attribute;
    private String stringValue;
    private Integer integerValue;
    private Long longValue;

    public Value(@NotNull Attribute attribute, @NotNull String value) {
        this.attribute = attribute;
        stringValue = value;
        checkStringValue();
    }

    public Value(@NotNull Attribute attribute, @NotNull Integer value) {
        this.attribute = attribute;
        integerValue = value;
        checkIntegerValue();
    }

    public Value(@NotNull Attribute attribute, @NotNull Long value) {
        this.attribute = attribute;
        longValue = value;
        checkLongValue();
    }

    public String getString() {
        checkStringValue();
        return stringValue;
    }

    public Integer getInteger() {
        checkIntegerValue();
        return integerValue;
    }

    public Long getLong() {
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
                return integerValue.compareTo(that.integerValue);
            default:
                return longValue.compareTo(that.longValue);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Value value = (Value) o;
        return Objects.equals(attribute, value.attribute) &&
                Objects.equals(stringValue, value.stringValue) &&
                Objects.equals(integerValue, value.integerValue) &&
                Objects.equals(longValue, value.longValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attribute, stringValue, integerValue, longValue);
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
            throw new IllegalArgumentException("Attribute " + attribute + " is not String");
        }
    }

    private void checkLongValue() {
        if (attribute.getType() != Attribute.Type.LONG) {
            throw new IllegalArgumentException("Attribute " + attribute + " is not String");
        }
    }
}

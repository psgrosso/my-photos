package model.attribute;


import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * An immutable representation of attribute value key, that will be used in attribute key-value map @see model.attribute.AttributeMap
 */
public final class Attribute {
    private final Type type;
    private final String name;

    private Attribute(@NotNull final Type type, @NotNull final String name) {
        this.type = type;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attribute attribute = (Attribute) o;

        return type == attribute.type && name.equals(attribute.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, name);
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "type=" + type +
                ", name='" + name + '\'' +
                '}';
    }

    public static Attribute newStringAttribute(@NotNull String name) {
        return new Attribute(Type.STRING, name);
    }

    public static Attribute newIntegerAttribute(@NotNull String name) {
        return new Attribute(Type.INTEGER, name);
    }

    public static Attribute newLongAttribute(@NotNull String name) {
        return new Attribute(Type.LONG, name);
    }

    public enum Type {
        STRING, INTEGER, LONG;
    }
}

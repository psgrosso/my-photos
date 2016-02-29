package model.attribute;


import org.jetbrains.annotations.NotNull;

/**
 * An immutable representation of a value key, that will be used in a key-value map @see model.attribute.AttributeMap
 * @param <T> the value type
 */
public final class Attribute<T> {
    private final Class<T> classType;
    private final String name;

    private Attribute(@NotNull final Class<T> classType, @NotNull final String name) {
        this.classType = classType;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public T cast(Object obj) {
        return classType.cast(obj);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attribute<?> attribute = (Attribute<?>) o;

        return name.equals(attribute.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "type=" + classType.getSimpleName() +
                ", name='" + name + '\'' +
                '}';
    }

    public static Attribute<String> newStringAttribute(@NotNull String name) {
        return new Attribute<>(String.class, name);
    }

    public static Attribute<Integer> newIntegerAttribute(@NotNull String name) {
        return new Attribute<>(Integer.class, name);
    }

    public static Attribute<Long> newLongAttribute(@NotNull String name) {
        return new Attribute<>(Long.class, name);
    }
}

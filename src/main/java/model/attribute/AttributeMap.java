package model.attribute;

import com.google.common.collect.ImmutableMap;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;


/**
 * An immutable collection of attributes (attribute (type, name) - value)
 */
public final class AttributeMap {
    private final ImmutableMap<Attribute<?>,Object> attributes;
    private final AttributeSet attributeSet;
    private final int hashCode;


    private <T> AttributeMap(@NotNull AttributeSet attributeSet,
                             @NotNull ImmutableMap<Attribute<?>,Object> attributes) {
        this.attributeSet = attributeSet;
        this.attributes = attributes;
        hashCode = attributes.hashCode() * 31 + attributeSet.hashCode();
    }

    /**
     * Returns the value associated to the key
     * @param key the key to get its value from
     * @param <T> the attributes type
     * @return the associated value
     * @throws IllegalArgumentException if there is no value for the specified key
     */
    public <T> T get(final Attribute<T> key) {
        Object value = attributes.get(key);
        if (value == null) {
            throw new IllegalArgumentException("Attribute name is not valid");
        }
        return key.cast(value);
    }

    public AttributeMap subset(@NotNull AttributeSet attributeSet) {
        if (!this.attributeSet.contains(attributeSet)) {
            throw new IllegalArgumentException("Invalid attribute set: " + attributeSet);
        }
        ImmutableMap.Builder<Attribute<?>,Object> builder = ImmutableMap.builder();
        for (Attribute<?> attribute : attributeSet) {
            builder.put(attribute, attributes.get(attribute));
        }
        return new AttributeMap(attributeSet, builder.build());
    }

    public AttributeSet getAttributeSet() {
        return attributeSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AttributeMap that = (AttributeMap) o;

        return attributes.equals(that.attributes);
    }

    @Override
    public int hashCode() {
        // Pre-calculated hash, since it's an immutable class
        return hashCode;
    }

    @Override
    public String toString() {
        return "AttributeMap{" + attributes + '}';
    }

    public static Builder builderFor(@NotNull AttributeSet requiredAttributes) {
        return new Builder(requiredAttributes);
    }

    public static final class Builder {
        private final AttributeSet requiredAttributes;
        private final Map<Attribute<?>,Object> builder;

        private Builder(@NotNull AttributeSet requiredAttributes) {
            this.requiredAttributes = requiredAttributes;
            builder = new HashMap<>();
        }

        public <T> Builder with(@NotNull Attribute<T> attribute, @NotNull T value) {
            if (!requiredAttributes.contains(attribute)) {
                throw new IllegalArgumentException(attribute + " is not valid");
            }
            builder.put(attribute, value);
            return this;
        }

        public AttributeMap build() {
            if (requiredAttributes.size() != builder.size()) {
                throw new IllegalArgumentException(builder + " is not valid for " + requiredAttributes);
            }
            return new AttributeMap(requiredAttributes, ImmutableMap.copyOf(builder));
        }
    }
}

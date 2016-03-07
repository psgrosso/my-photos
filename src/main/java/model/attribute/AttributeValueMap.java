package model.attribute;

import com.google.common.collect.ImmutableMap;
import org.jetbrains.annotations.NotNull;


/**
 * An immutable collection of attributes (attribute (type, name) - value)
 */
public class AttributeValueMap implements Comparable<AttributeValueMap> {
    private final AttributeSet attributes;
    private final ImmutableMap<Attribute, Value> values;

    private AttributeValueMap(@NotNull AttributeSet attributes, @NotNull ImmutableMap<Attribute, Value> values) {
        if (attributes.size() != values.size()) {
            throw new IllegalArgumentException(values + " is not valid for " + attributes);
        }
        this.attributes = attributes;
        this.values = values;
    }

    public Value get(@NotNull Attribute attribute) {
        if (!attributes.contains(attribute)) {
            throw new IllegalArgumentException("Invalid attribute : " + attribute);
        }
        return values.get(attribute);
    }

    public AttributeSet getAttributes() {
        return attributes;
    }

    public AttributeValueMap subset(@NotNull AttributeSet attributeSet) {
        if (!attributes.contains(attributeSet)) {
            throw new IllegalArgumentException("Invalid attribute set: " + attributeSet);
        }
        Builder builder1 = builderFor(attributeSet);
        for (Attribute attribute : attributeSet) {
            builder1.with(values.get(attribute));
        }
        return builder1.build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AttributeValueMap that = (AttributeValueMap) o;

        return values.equals(that.values);
    }

    @Override
    public int hashCode() {
        return values.hashCode();
    }

    @Override
    public int compareTo(@NotNull AttributeValueMap that) {
        if (!attributes.equals(that.attributes)) {
            throw new IllegalArgumentException("Incompatible attribute set: " + that.attributes);
        }
        for (Attribute attribute : attributes) {
            int comparison = values.get(attribute).compareTo(that.values.get(attribute));
            if (comparison != 0) {
                return comparison;
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        return "AttributeValueMap{" +
                "attributes=" + attributes +
                ", values=" + values +
                '}';
    }

    public static Builder builderFor(@NotNull AttributeSet requiredAttributes) {
        return new Builder(requiredAttributes);
    }

    public static final class Builder {
        private final AttributeSet requiredAttributes;
        private final ImmutableMap.Builder<Attribute, Value> builder;

        private Builder(@NotNull AttributeSet requiredAttributes) {
            this.requiredAttributes = requiredAttributes;
            builder = ImmutableMap.builder();
        }

        public Builder with(@NotNull Value value) {
            if (!requiredAttributes.contains(value.getAttribute())) {
                throw new IllegalArgumentException(value + " is not valid");
            }
            builder.put(value.getAttribute(), value);
            return this;
        }

        public AttributeValueMap build() {
            return new AttributeValueMap(requiredAttributes, builder.build());
        }
    }
}

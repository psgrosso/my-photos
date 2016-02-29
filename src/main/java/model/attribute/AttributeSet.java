package model.attribute;


import com.google.common.collect.ImmutableSet;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public final class AttributeSet implements Iterable<Attribute<?>> {
    private final ImmutableSet<Attribute<?>> attributeSet;
    private final int hashCode;

    private AttributeSet(@NotNull ImmutableSet<Attribute<?>> attributeSet) {
        this.attributeSet = attributeSet;
        hashCode = attributeSet.hashCode();
    }

    public boolean contains(@NotNull Attribute attribute) {
        return attributeSet.contains(attribute);
    }

    /**
     * Checks that the specified attribute set is completely contained in this attribute set
     * @param another the attribute set to check
     * @return true if another is contained in this attribute set
     */
    public boolean contains(@NotNull AttributeSet another) {
        return attributeSet.containsAll(another.attributeSet);
    }

    public int size() {
        return attributeSet.size();
    }

    @Override
    public Iterator<Attribute<?>> iterator() {
        return attributeSet.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttributeSet that = (AttributeSet) o;
        return attributeSet.equals(that.attributeSet);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public String toString() {
        return "AttributeSet{" + attributeSet + '}';
    }

    public static AttributeSet of(Attribute<?> attribute) {
        return new AttributeSet(ImmutableSet.of(attribute));
    }

    public static AttributeSet of(Attribute<?> attribute1, Attribute<?> attribute2) {
        return new AttributeSet(ImmutableSet.of(attribute1, attribute2));
    }

    public static AttributeSet of(@NotNull Attribute<?> attribute1,
                                  @NotNull Attribute<?> attribute2,
                                  @NotNull Attribute<?> attribute3) {
        return new AttributeSet(ImmutableSet.of(attribute1, attribute2, attribute3));
    }
}

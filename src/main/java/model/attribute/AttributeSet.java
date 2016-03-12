package model.attribute;


import com.google.common.collect.ImmutableSet;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

/**
 * A set of attribute elements
 * It implements an iterable of the attributes it contains.  It will iterate deterministically: in the
 * same order in which the attribute set was created.
 *
 * Note: the order DOES matter, two attribute sets containung the same attributes will fail if they are
 * given in different order
 */
public final class AttributeSet implements Iterable<Attribute> {
    private final ImmutableSet<Attribute> attributeSet;
    // Pre-calculated hash
    private final int hashCode;

    private AttributeSet(@NotNull ImmutableSet<Attribute> attributeSet) {
        this.attributeSet = attributeSet;
        hashCode = attributeSet.hashCode();
    }

    /**
     * Checks that the specified attribute set is completely contained in this attribute set
     * @param another the attribute set to check
     * @return true if another is contained in this attribute set
     */
    public boolean contains(@NotNull Attribute another) {
        return attributeSet.contains(another);
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
    public Iterator<Attribute> iterator() {
        return attributeSet.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttributeSet that = (AttributeSet) o;

        // Try to fail fast
        if (hashCode != that.hashCode || attributeSet.size() != that.attributeSet.size()) {
            return false;
        }
        Iterator<Attribute> thatIterator = that.iterator();
        for (Attribute attribute : attributeSet) {
            if (!attribute.equals(thatIterator.next())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public String toString() {
        return "AttributeSet{" + attributeSet + '}';
    }

    public static AttributeSet of(@NotNull Attribute attribute) {
        return new AttributeSet(ImmutableSet.of(attribute));
    }

    public static AttributeSet of(@NotNull Attribute attribute1, @NotNull Attribute attribute2) {
        return new AttributeSet(ImmutableSet.of(attribute1, attribute2));
    }

    public static AttributeSet of(@NotNull Attribute attribute1,
                                  @NotNull Attribute attribute2,
                                  @NotNull Attribute attribute3) {
        return new AttributeSet(ImmutableSet.of(attribute1, attribute2, attribute3));
    }
}

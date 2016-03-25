package model.photo.element;

import com.google.common.collect.ImmutableMap;
import model.attribute.Attribute;
import model.attribute.AttributeSet;
import model.attribute.Values;
import model.attribute.Value;
import model.photo.PhotoKind;
import model.photo.identifier.LocalPhotoIdentifier;
import model.photo.identifier.PhotoIdentifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;


public abstract class BasePhotoElement implements PhotoElement {
    private final PhotoKind kind;
    private final Values values;
    private final PhotoElement parent;
    private final PhotoIdentifier identifier;
    private ImmutableMap<LocalPhotoIdentifier, PhotoElement> children;

    public BasePhotoElement(@Nullable PhotoElement parent, @NotNull PhotoKind kind,
                            @NotNull Values values) {
        // Check all required attributes are present for this kind
        if (!kind.getRequiredAttributes().equals(values.getAttributes())) {
            throw new IllegalArgumentException("Not valid values " + values + " for " + kind);
        }
        this.parent = parent;
        this.kind = kind;
        this.values = values;
        identifier = new PhotoIdentifier(this, values);
    }

    @Override
    public PhotoKind getKind() {
        return kind;
    }

    @Override
    public Value getValue(@NotNull Attribute attribute) {
        return values.get(attribute);
    }

    @Override
    public PhotoElement getParent() {
        return parent;
    }

    @Override
    public PhotoElement getChild(@NotNull LocalPhotoIdentifier childId) {
        return children.get(childId);
    }

    @Override
    public PhotoIdentifier getIdentifier() {
        return identifier;
    }

    @Override
    public void setChildren(@NotNull Collection<PhotoElement> children) {
        if (this.children != null) {
            throw new UnsupportedOperationException("Children was already set");
        }
        // Sort children by their id so that it's easier to compare PhotoElement families
        SortedMap<LocalPhotoIdentifier, PhotoElement> sortedChildren = new TreeMap<>();
        for (PhotoElement child : children) {
            sortedChildren.put(child.getIdentifier().getLocalIdentifier(), child);
        }
        this.children = ImmutableMap.copyOf(sortedChildren);
    }

    @Override
    public String getName() {
        return values.get(F_NAME).getString();
    }

    @Override
    public Iterator<PhotoElement> iterator() {
        if (children == null) {
            children = ImmutableMap.of();
        }
        return children.values().iterator();
    }

    @Override
    public int compareTo(@NotNull PhotoElement photoElement) {
        return identifier.getLocalIdentifier().compareTo(photoElement.getIdentifier().getLocalIdentifier());
    }

    /**
     * A photo element is equal to another if both have the same:
     * - kind
     * - unique identifier (and therefore have the same kind)
     * - parent identifier (this is also contained in their unique identifiers)
     * - set of attribute-values
     *
     * No equality checks are made on their children, this should be done manually, equals() will only
     * consider attributes inherent to this photo element exclusively
     * @param o the object to compare to
     * @return true if both are equal elements
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasePhotoElement that = (BasePhotoElement) o;

        if (kind != that.getKind()) {
            return false;
        }
        /**
         * For the following tests, we take into account the fact that current LocalIdentifier
         * is always a subset of photo element values
         */
        if (parent == null) {
            if (that.parent != null) {
                return false;
            }
        }
        else if (!parent.getIdentifier().equals(that.parent.getIdentifier())) {
            return false;
        }
        // We're done with parent checks, now check the whole value set (which of course includes local identifier)
        return values.equals(that.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kind, parent);
    }

    @Override
    public String toString() {
        String parentId = (parent == null) ? "" : ("parent=" + parent.getIdentifier().getLocalIdentifier() + ", ");
        return kind + "{" + parentId +
                "id=" + identifier +
                "values=" + values +
                ", children=" + children +
                '}';
    }
}

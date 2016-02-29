package model.photo.element;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import model.attribute.Attribute;
import model.attribute.AttributeMap;
import model.photo.PhotoKind;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;


public abstract class BasePhotoElement implements PhotoElement {
    private final PhotoKind kind;
    private final AttributeMap attributeMap;
    private final PhotoElement parent;
    private ImmutableMap<PhotoElementIdentifier, PhotoElement> children;

    public BasePhotoElement(@NotNull PhotoKind kind, @NotNull AttributeMap attributeMap, PhotoElement parent) {
        // Check all required attributes are present for this kind
        if (!kind.getRequiredAttributes().equals(attributeMap.getAttributeSet())) {
            throw new IllegalArgumentException("Not valid attribute map " + attributeMap + " for " + kind);
        }
        this.parent = parent;
        this.kind = kind;
        this.attributeMap = attributeMap;
    }

    @Override
    public PhotoKind getKind() {
        return kind;
    }

    @Override
    public <T> T getAttribute(@NotNull Attribute<T> key) {
        return attributeMap.get(key);
    }

    @Override
    public PhotoElement getParent() {
        return parent;
    }

    @Override
    public ImmutableList<PhotoElement> getChildren() {
        return children.values().asList();
    }

    @Override
    public PhotoElement getChild(String childId) {
        return children.get(childId);
    }

    @Override
    public PhotoElementIdentifier getIdentifier() {
        return null;
    }

    @Override
    public AttributeMap getId() {
        return null;
    }

    @Override
    public void setChildren(@NotNull Collection<PhotoElement> children) {
        if (this.children != null) {
            throw new UnsupportedOperationException("Children was already set");
        }
        // Sort children by their id
        SortedMap<AttributeMap, PhotoElement> sortedChildren = new TreeMap<>();
        for (PhotoElement child : children) {
            sortedChildren.put(child.getId(), child);
        }
        //this.children = ImmutableMap.copyOf(sortedChildren);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasePhotoElement that = (BasePhotoElement) o;

        if (kind != that.kind) {
            return false;
        }
        if (parent == null) {
            if (that.parent != null) {
                return false;
            }
        }
        else if (that.parent == null) {
            return false;
        }
        else {
            // Both parents are non-null
            if (!parent.equals(that.parent)) {
                return false;
            }
            if (!parent.getId().equals(that.parent.getId())) {
                return false;
            }
        }
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(kind, parent);
    }

    @Override
    public String toString() {
        String parentId = (parent == null) ? "" : ("parent=" + parent.getId() + ", ");
        return kind + "{" + parentId +
                "attributeMap=" + attributeMap +
                ", children=" + children +
                '}';
    }

    public static AttributeMap attributeMapFor(@NotNull String name) {
        return AttributeMap.builderFor(NAME_ATTRIBUTES).with(PhotoElement.F_NAME, name).build();
    }
}

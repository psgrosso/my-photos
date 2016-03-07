package model.photo.identifier;


import model.attribute.AttributeValueMap;
import model.photo.PhotoKind;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;


/**
 * Represents the attributes that uniquely identifies a photo element from its siblings
 * In other words, given a parent photo element, this class uniquely identifies each of its children
 */
public final class LocalPhotoIdentifier implements Comparable<LocalPhotoIdentifier> {
    private final PhotoKind kind;
    private final AttributeValueMap attributes;

    public LocalPhotoIdentifier(@NotNull PhotoKind kind, @NotNull AttributeValueMap attributes) {
        this.kind = kind;
        this.attributes = attributes;
    }

    @Override
    public int compareTo(@NotNull LocalPhotoIdentifier that) {
        if (kind != that.kind) {
            throw new IllegalArgumentException("Invalid local photo identifier: " + that);
        }
        return attributes.compareTo(that.attributes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocalPhotoIdentifier that = (LocalPhotoIdentifier) o;
        return kind == that.kind && attributes.equals(that.attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kind, attributes);
    }

    @Override
    public String toString() {
        return "LocalPhotoIdentifier{" +
                "kind=" + kind +
                ", attributes=" + attributes +
                '}';
    }
}

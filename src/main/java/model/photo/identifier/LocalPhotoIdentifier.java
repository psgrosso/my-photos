package model.photo.identifier;


import model.attribute.Values;
import model.photo.PhotoKind;
import org.jetbrains.annotations.NotNull;


/**
 * An immutable object that represents the values that uniquely identifies a photo element from its siblings
 * In other words, given a parent photo element, this class uniquely identifies each of its children
 */
public final class LocalPhotoIdentifier implements Comparable<LocalPhotoIdentifier> {
    private final PhotoKind kind;
    private final Values values;
    // Pre-calculated hash
    private final int hash;

    public LocalPhotoIdentifier(@NotNull PhotoKind kind, @NotNull Values values) {
        this.kind = kind;
        this.values = values;
        // Pre-calculate hash code, since this object is immutable
        hash = (17 + kind.hashCode()) * 31 + values.hashCode();
    }

    public PhotoKind getKind() {
        return kind;
    }

    @Override
    public int compareTo(@NotNull LocalPhotoIdentifier that) {
        if (kind != that.kind) {
            throw new IllegalArgumentException("Invalid local photo identifier: " + that);
        }
        return values.compareTo(that.values);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocalPhotoIdentifier that = (LocalPhotoIdentifier) o;
        // Fail quick
        return hash == that.hash && kind == that.kind && values.equals(that.values);
    }

    @Override
    public int hashCode() {
        return hash;
    }

    @Override
    public String toString() {
        return "LocalPhotoIdentifier{" +
                "kind=" + kind +
                ", values=" + values +
                '}';
    }
}

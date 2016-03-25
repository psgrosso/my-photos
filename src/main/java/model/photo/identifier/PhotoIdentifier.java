package model.photo.identifier;


import com.google.common.collect.ImmutableList;
import model.attribute.Values;
import model.photo.PhotoKind;
import model.photo.element.PhotoElement;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Objects;


/**
 * This class uniquely identifies a photo element.
 * It contains the identifier for the specified photo element and all the identifiers of its ancestors.
 * Behaves as an iterable, with its parents first (first element will be the PhotoCollection it belongs to).
 * It is an immutable object.
 */
public final class PhotoIdentifier implements Iterable<LocalPhotoIdentifier> {
    private final ImmutableList<LocalPhotoIdentifier> identifiers;
    // Quick access to local identifier (already contained in identifiers
    private final LocalPhotoIdentifier localIdentifier;
    private final int hash;

    public PhotoIdentifier(@NotNull PhotoElement photoElement, @NotNull Values values) {
        final PhotoKind kind;
        ImmutableList.Builder<LocalPhotoIdentifier> builder = ImmutableList.builder();

        kind = photoElement.getKind();
        if (kind != PhotoKind.COLLECTION) {
            builder.addAll(photoElement.getParent().getIdentifier());
        }
        localIdentifier = new LocalPhotoIdentifier(kind, values.subset(kind.getUniqueAttributes()));
        identifiers = builder.add(localIdentifier).build();
        hash = identifiers.hashCode();
    }

    /**
     * Returns the kind of the photo element this identifier represents
     * @return the photo kind
     */
    public PhotoKind getKind() {
        return localIdentifier.getKind();
    }

    /**
     * Returns the local identifier of the photo element this identifier was created from
     * @return the related local photo identifier
     */
    public LocalPhotoIdentifier getLocalIdentifier() {
        return localIdentifier;
    }

    @Override
    public Iterator<LocalPhotoIdentifier> iterator() {
        return identifiers.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhotoIdentifier that = (PhotoIdentifier) o;
        // Fail quick
        return hash == that.hash && Objects.equals(identifiers, that.identifiers);
    }

    @Override
    public int hashCode() {
        return hash;
    }
}

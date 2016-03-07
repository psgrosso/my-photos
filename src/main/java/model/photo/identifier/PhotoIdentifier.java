package model.photo.identifier;


import com.google.common.collect.ImmutableList;
import model.attribute.Attribute;
import model.attribute.AttributeSet;
import model.attribute.AttributeValueMap;
import model.photo.PhotoKind;
import model.photo.element.PhotoElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.Objects;


/**
 * This class uniquely identifies a photo element.
 * It contains the identifier for the specified photo element and all the identifiers of its ancestors.
 * Behaves as an iterable, with its parents first (first element will be the PhotoCollection it belongs to).
 */
public final class PhotoIdentifier implements Iterable<LocalPhotoIdentifier> {
    private final ImmutableList<LocalPhotoIdentifier> identifiers;
    // Quick access to local identifier (already contained in identifiers
    private final LocalPhotoIdentifier localIdentifier;


    private PhotoIdentifier(@Nullable PhotoElement parent, @NotNull AttributeValueMap values) {
        final PhotoKind kind;
        ImmutableList.Builder<LocalPhotoIdentifier> builder = ImmutableList.builder();

        if (parent == null) {
            kind = PhotoKind.COLLECTION;
        }
        else {
            kind = parent.getKind().getChild();
            builder.addAll(parent.getIdentifier());
        }
        localIdentifier = calculateLocalIdentifier(kind, values);
        identifiers = builder.add(localIdentifier).build();
    }

    /**
     * Returns the local identifier for the photo element this identifier was created from
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
        return Objects.equals(identifiers, that.identifiers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifiers);
    }

    public static PhotoIdentifier fromPhotoParent(@Nullable PhotoElement parent, @NotNull AttributeValueMap values) {
        return new PhotoIdentifier(parent, values);
    }

    private static LocalPhotoIdentifier calculateLocalIdentifier(
                                    @NotNull PhotoKind kind, @NotNull AttributeValueMap values) {
        AttributeSet uniqueAttributes = kind.getUniqueAttributes();
        AttributeValueMap.Builder builder = AttributeValueMap.builderFor(uniqueAttributes);
        for (Attribute attribute : uniqueAttributes) {
            builder.with(values.get(attribute));
        }
        return new LocalPhotoIdentifier(kind, builder.build());
    }
}

package model.photo.element;


import model.attribute.Attribute;
import model.attribute.AttributeValueMap;
import model.attribute.Value;
import model.photo.PhotoKind;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;


public final class Photo extends BasePhotoElement {
    public static final Attribute F_SIZE = Attribute.newLongAttribute("size");

    public Photo(@NotNull PhotoElement parent, @NotNull AttributeValueMap attributeMap) {
        super(parent, PhotoKind.PHOTO, attributeMap);
    }

    public static AttributeValueMap attributeMapFor(String name, long size) {
        return AttributeValueMap.builderFor(PhotoKind.PHOTO.getRequiredAttributes())
                            .with(new Value(F_NAME, name))
                            .with(new Value(F_SIZE, size))
                            .build();
    }

    @Override
    public Iterator<PhotoElement> iterator() {
        throw new UnsupportedOperationException("Photo kind does not have children");
    }
}

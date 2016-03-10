package model.photo.element;


import model.attribute.Attribute;
import model.attribute.Values;
import model.attribute.Value;
import model.photo.PhotoKind;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;


public final class Photo extends BasePhotoElement {
    public static final Attribute F_SIZE = Attribute.newLongAttribute("size");

    public Photo(@NotNull PhotoElement parent, @NotNull Values attributeMap) {
        super(parent, PhotoKind.PHOTO, attributeMap);
    }

    public static Values valuesFor(String name, long size) {
        return Values.builderFor(PhotoKind.PHOTO.getRequiredAttributes())
                            .with(new Value(F_NAME, name))
                            .with(new Value(F_SIZE, size))
                            .build();
    }
}

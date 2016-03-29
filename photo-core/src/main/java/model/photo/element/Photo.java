package model.photo.element;


import model.attribute.Attribute;
import model.attribute.Values;
import model.attribute.Value;
import model.photo.PhotoKind;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;


public final class Photo extends BasePhotoElement {
    public static final Attribute F_SIZE = Attribute.newLongAttribute("size");

    public Photo(@NotNull PhotoElement parent, @NotNull Values values) {
        super(parent, PhotoKind.PHOTO, values);
    }

    public static Values valuesFor(@NotNull String name, long size) {
        return PhotoKind.PHOTO.valuesBuilderFor(name)
                            .with(new Value(F_SIZE, size))
                            .build();
    }
}

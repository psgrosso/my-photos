package model.photo.element;


import model.attribute.Attribute;
import model.attribute.AttributeMap;
import model.photo.PhotoKind;
import org.jetbrains.annotations.NotNull;


public class Photo extends BasePhotoElement {
    public static final Attribute<Long> F_SIZE = Attribute.newLongAttribute("size");

    public Photo(@NotNull PhotoElement parent, @NotNull AttributeMap attributeMap) {
        super(PhotoKind.PHOTO, attributeMap, parent);
    }

    public static AttributeMap attributeMapFor(String name, long size) {
        return AttributeMap.builderFor(PhotoKind.PHOTO.getRequiredAttributes())
                            .with(F_NAME, name)
                            .with(F_SIZE, size)
                            .build();
    }
}

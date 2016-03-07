package model.photo.element;


import model.attribute.Attribute;
import model.attribute.AttributeValueMap;
import model.attribute.Value;
import model.photo.PhotoKind;
import org.jetbrains.annotations.NotNull;

public final class PhotoAlbum extends BasePhotoElement {

    public static final Attribute F_MONTH = Attribute.newIntegerAttribute("month");
    public static final Attribute F_DAY = Attribute.newIntegerAttribute("day");

    public PhotoAlbum(@NotNull PhotoElement parent, @NotNull AttributeValueMap values) {
        super(parent, PhotoKind.ALBUM, values);
    }

    public static AttributeValueMap attributeMapFor(String name, int month, int day) {
        return AttributeValueMap.builderFor(PhotoKind.ALBUM.getRequiredAttributes())
                            .with(new Value(F_NAME, name))
                            .with(new Value(F_MONTH, month))
                            .with(new Value(F_DAY, day))
                            .build();
    }
}

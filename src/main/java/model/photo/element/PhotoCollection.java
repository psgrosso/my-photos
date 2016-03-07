package model.photo.element;

import model.attribute.AttributeValueMap;
import model.photo.PhotoKind;
import org.jetbrains.annotations.NotNull;


public final class PhotoCollection extends BasePhotoElement {

    public PhotoCollection(@NotNull AttributeValueMap values) {
        super(null, PhotoKind.COLLECTION, values);
    }
}

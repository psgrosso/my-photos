package model.comparator;

import model.photo.element.PhotoElement;
import org.jetbrains.annotations.NotNull;


public final class PhotoElementResult {
    private PhotoElement photoElement;
    private Status status;

    public PhotoElementResult(@NotNull PhotoElement photoElement, @NotNull Status status) {
        this.photoElement = photoElement;
        this.status = status;
    }

    public enum Status {
        EQUALS,
        NEW_SOURCE,
        NEW_TARGET,
        NOT_EQUAL
    }
}

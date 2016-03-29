package model.comparator;

import model.photo.identifier.PhotoIdentifier;
import org.jetbrains.annotations.NotNull;


public final class PhotoComparatorResult {
    private final PhotoIdentifier photoIdentifier;
    private final Status status;

    public PhotoComparatorResult(@NotNull PhotoIdentifier photoIdentifier, @NotNull Status status) {
        this.photoIdentifier = photoIdentifier;
        this.status = status;
    }

    public enum Status {
        NEW_SOURCE,
        NEW_TARGET,
        DIFFERENT
    }
}

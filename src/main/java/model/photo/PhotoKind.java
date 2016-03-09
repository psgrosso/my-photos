package model.photo;

import model.attribute.AttributeSet;
import model.attribute.Values;
import model.photo.element.Photo;
import model.photo.element.PhotoAlbum;
import model.photo.element.PhotoCollection;
import model.photo.element.PhotoElement;
import model.photo.element.PhotoYear;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public enum PhotoKind {
    COLLECTION {
        @Override
        public PhotoCollection create(@Nullable PhotoElement parent, @NotNull Values values) {
            if (parent != null) {
                throw new IllegalArgumentException("Collection cannot have a parent");
            }
            return new PhotoCollection(values);
        }

        @Override
        public PhotoKind getParent() {
            throw new UnsupportedOperationException("Collection cannot have a parent");
        }

        @Override
        public PhotoKind getChild() {
            return YEAR;
        }
    },
    YEAR {
        @Override
        public PhotoYear create(@NotNull PhotoElement parent, @NotNull Values values) {
            return new PhotoYear(parent, values);
        }

        @Override
        public PhotoKind getParent() {
            return COLLECTION;
        }

        @Override
        public PhotoKind getChild() {
            return ALBUM;
        }
    },
    ALBUM(AttributeSet.of(PhotoAlbum.F_NAME, PhotoAlbum.F_MONTH, PhotoAlbum.F_DAY),
            AttributeSet.of(PhotoAlbum.F_NAME, PhotoAlbum.F_MONTH, PhotoAlbum.F_DAY)) {

        @Override
        public PhotoElement create(@NotNull PhotoElement parent, @NotNull Values values) {
            return new PhotoAlbum(parent, values);
        }

        @Override
        public PhotoKind getParent() {
            return YEAR;
        }

        @Override
        public PhotoKind getChild() {
            return PHOTO;
        }
    },
    PHOTO(AttributeSet.of(Photo.F_NAME, Photo.F_SIZE), AttributeSet.of(Photo.F_NAME)) {
        @Override
        public PhotoElement create(@NotNull PhotoElement parent, @NotNull Values values) {
            return new Photo(parent, values);
        }

        @Override
        public PhotoKind getParent() {
            return ALBUM;
        }

        @Override
        public PhotoKind getChild() {
            throw new UnsupportedOperationException("Photo cannot have children");
        }
    };

    private final AttributeSet required;
    private final AttributeSet unique;

    PhotoKind() {
        // Default behavior for required and unique attributes is just the name for both
        this(PhotoElement.NAME_ATTRIBUTES, PhotoElement.NAME_ATTRIBUTES);
    }

    PhotoKind(@NotNull AttributeSet required, @NotNull AttributeSet unique) {
        this.required = required;
        this.unique = unique;
    }

    public abstract PhotoElement create(PhotoElement parent, @NotNull Values values);

    public abstract PhotoKind getParent();

    public abstract PhotoKind getChild();

    /**
     * Returns an attribute set which are unique inside the container parent
     * @return the attribute set
     */
    public AttributeSet getUniqueAttributes() {
        return unique;
    }

    /**
     * Returns an attribute set with all attributes for this photo element kind
     * @return the attribute set
     */
    public AttributeSet getRequiredAttributes() {
        return required;
    }
}

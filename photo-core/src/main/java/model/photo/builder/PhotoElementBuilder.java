package model.photo.builder;

import model.attribute.Values;
import model.photo.element.PhotoElement;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


public class PhotoElementBuilder {
    private final List<PhotoElementBuilder> children;
    private PhotoElement photoElement;
    private final Values values;
    private final PhotoElementBuilder parent;

    /**
     * Creates the builder.
     * @param rootPhotoElement the root photo element to which this builder will be related
     */
    public PhotoElementBuilder(@NotNull PhotoElement rootPhotoElement) {
        this.photoElement = rootPhotoElement;
        this.values = null;
        this.parent = null;
        this.children = new LinkedList<>();
    }

    /**
     * Creates the builder for the specified values and parent
     * @param parent the parent of this builder
     * @param values the values for this builder
     */
    public PhotoElementBuilder(@NotNull PhotoElementBuilder parent, @NotNull Values values) {
        this.photoElement = null;
        this.values = values;
        this.parent = parent;
        this.children = new LinkedList<>();
    }

    public PhotoElementBuilder addChild(@NotNull Values values) {
        PhotoElementBuilder child = new PhotoElementBuilder(this, values);
        children.add(child);
        return child;
    }

    public PhotoElement build() {
        if (photoElement == null) {
            // We need to create this photo element
            if (parent == null || parent.photoElement == null || values == null) {
                // This cannot happen
                throw new IllegalStateException();
            }
            photoElement = parent.photoElement.getKind().getChild().create(parent.photoElement, values);
        }
        if (!children.isEmpty()) {
            photoElement.setChildren(
                    children.stream()
                            .map(PhotoElementBuilder::build)
                            .collect(Collectors.toList()));
        }
        return photoElement;
    }
}

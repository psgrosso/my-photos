package model.photo.builder;

import model.attribute.Values;
import model.photo.PhotoKind;
import model.photo.element.PhotoCollection;
import model.photo.element.PhotoElement;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


public class PhotoElementBuilder {
    private Values values;
    private List<PhotoElementBuilder> children;

    /**
     * Creates a builder for an initial photo element, that is, a PhotoCollection
     * @param name the collection's name
     */
    public PhotoElementBuilder(@NotNull String name) {
        this(PhotoKind.COLLECTION.valuesBuilderFor(name).build());
    }

    /**
     * Creates the builder.
     * @param values the photo element values
     */
    public PhotoElementBuilder(@NotNull Values values) {
        this.values = values;
        children = new LinkedList<>();
    }

    public PhotoElementBuilder addChild(Values childAttributeMap) {
        PhotoElementBuilder child = new PhotoElementBuilder(childAttributeMap);
        children.add(child);
        return child;
    }

    /**
     * Creates the PhotoCollection
     * This will be the main and only parent of the whole tree of photo elements
     */
    public PhotoCollection build() {
        return (PhotoCollection) build(null);
    }

    private PhotoElement build(PhotoElement parent) {
        PhotoElement newPhotoElement = (parent == null) ? new PhotoCollection(values) :
                                    parent.getKind().getChild().create(parent, values);
        if (!children.isEmpty()) {
            newPhotoElement.setChildren(
                    children.stream()
                            .map(child -> child.build(newPhotoElement)).collect(Collectors.toList()));
        }
        return newPhotoElement;
    }
}

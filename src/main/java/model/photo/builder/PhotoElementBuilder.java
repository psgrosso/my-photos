package model.photo.builder;


import model.attribute.AttributeMap;
import model.photo.element.PhotoCollection;
import model.photo.element.PhotoElement;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


public class PhotoElementBuilder {
    private AttributeMap attributeMap;
    private List<PhotoElementBuilder> children;

    /**
     * Creates the builder.
     * @param attributeMap the photo element attributeMap
     */
    public PhotoElementBuilder(AttributeMap attributeMap) {
        this.attributeMap = attributeMap;
        children = new LinkedList<>();
    }

    public PhotoElementBuilder addChild(AttributeMap childAttributeMap) {
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
        PhotoElement newPhotoElement = (parent == null) ? new PhotoCollection(attributeMap) :
                                    parent.getKind().getChild().create(parent, attributeMap);
        if (!children.isEmpty()) {
            newPhotoElement.setChildren(
                    children.stream()
                            .map(child -> child.build(newPhotoElement)).collect(Collectors.toList()));
        }
        return newPhotoElement;
    }
}

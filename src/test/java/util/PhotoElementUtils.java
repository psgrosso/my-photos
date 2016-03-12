package util;


import model.attribute.Attribute;
import model.attribute.AttributeSet;
import model.attribute.Value;
import model.attribute.Values;
import model.photo.element.Photo;
import model.photo.element.PhotoAlbum;
import model.photo.element.PhotoCollection;
import model.photo.element.PhotoElement;
import model.photo.element.PhotoYear;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static model.photo.PhotoKind.ALBUM;
import static model.photo.PhotoKind.COLLECTION;
import static model.photo.PhotoKind.PHOTO;
import static model.photo.PhotoKind.YEAR;


public class PhotoElementUtils {
    public static Values valuesFrom(Object ... objects) {
        Value value1, value2 = null, value3 = null;

        value1 = newValue(objects[0], 1);
        if (objects.length > 1) {
            value2 = newValue(objects[1], 2);
        }
        if (objects.length > 2) {
            value3 = newValue(objects[2], 3);
        }
        if (objects.length == 1) {
            return Values.builderFor(AttributeSet.of(value1.getAttribute())).with(value1).build();
        }
        if (objects.length == 2) {
            return Values.builderFor(AttributeSet.of(value1.getAttribute(), value2.getAttribute()))
                    .with(value1).with(value2).build();
        }
        return Values.builderFor(AttributeSet.of(value1.getAttribute(), value2.getAttribute(), value3.getAttribute()))
                .with(value1).with(value2).with(value3).build();
    }

    public static AttributeSet setFrom(Object ... objects) {
        if (objects.length == 1) {
            return AttributeSet.of(newAttribute(objects[0], 1));
        }
        if (objects.length == 2) {
            return AttributeSet.of(newAttribute(objects[0], 1), newAttribute(objects[1], 2));
        }
        return AttributeSet.of(newAttribute(objects[0], 1), newAttribute(objects[1], 2), newAttribute(objects[2], 3));
    }

    public static Value newValue(Object value, int counter) {
        if (value instanceof String) {
            return new Value(stringAttribute(counter), (String) value);
        }
        if (value instanceof Integer) {
            return new Value(integerAttribute(counter), (Integer) value);
        }
        return new Value(longAttribute(counter), (Long) value);
    }

    public static Attribute newAttribute(Object value, int counter) {
        if (value instanceof String) {
            return stringAttribute(counter);
        }
        if (value instanceof Integer) {
            return integerAttribute(counter);
        }
        return longAttribute(counter);
    }

    public static Attribute stringAttribute(int counter) {
        return Attribute.newStringAttribute("string" + counter);
    }

    public static Attribute integerAttribute(int counter) {
        return Attribute.newIntegerAttribute("integer" + counter);
    }

    public static Attribute longAttribute(int counter) {
        return Attribute.newLongAttribute("long" + counter);
    }

    /*
    public static final String COLLECTION_NAME = "collectionName";
    public static final AttributeMap ATTRS_COLLECTION = PhotoCollection.attributeMapFor(COLLECTION_NAME);

    public static final int YEAR_VALUE = 1973;
    public static final String YEAR_NAME = String.valueOf(YEAR_VALUE);
    public static final AttributeMap ATTRS_YEAR = PhotoYear.attributeMapFor(YEAR_NAME);

    public static final String ALBUM_NAME = "Birthday";
    public static final int ALBUM_MONTH = 12;
    public static final int ALBUM_DAY = 2;
    public static final AttributeMap ATTRS_ALBUM = PhotoAlbum.attributeMapFor(ALBUM_NAME, ALBUM_MONTH, ALBUM_DAY);

    public static final String PHOTO_NAME = "pabs01.jpg";
    public static final long PHOTO_SIZE = 123456789012345L;
    public static final AttributeMap ATTRS_PHOTO = Photo.attributeMapFor(PHOTO_NAME, PHOTO_SIZE);

    private static int yearCount = 1973;
    private static int albumCount = 1;
    private static int photoCount = 1;
    private static Random random = new Random();


    public static PhotoElement createCollection() {
        return COLLECTION.create(null, ATTRS_COLLECTION);
    }

    public static PhotoElement createYear() {
        return YEAR.create(createCollection(), ATTRS_YEAR);
    }

    public static PhotoElement createAlbum() {
        return ALBUM.create(createYear(), ATTRS_ALBUM);
    }

    public static PhotoElement createPhoto() {
        return PHOTO.create(createAlbum(), ATTRS_PHOTO);
    }

    public static List<AttributeMap> createYearAttributes(int count) {
        List<AttributeMap> result = new LinkedList<>();
        int end = yearCount + count;
        while (yearCount < end) {
            AttributeMap attributeMap = PhotoYear.attributeMapFor(String.format("%d", yearCount++));
            result.add(attributeMap);
        }
        return result;
    }

    public static List<AttributeMap> createAlbumAttributes(int count) {
        List<AttributeMap> result = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            AttributeMap attributeMap = PhotoAlbum.attributeMapFor(
                                String.format("album%04d", albumCount++),
                                random.nextInt(12) + 1,
                                random.nextInt(28) + 1);
            result.add(attributeMap);
        }
        return result;
    }

    public static List<AttributeMap> createPhotoAttributes(int count) {
        List<AttributeMap> result = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            AttributeMap attributeMap = Photo.attributeMapFor(
                    String.format("photo%04d", photoCount++), random.nextLong());
            result.add(attributeMap);
        }
        return result;
    }
    */
}

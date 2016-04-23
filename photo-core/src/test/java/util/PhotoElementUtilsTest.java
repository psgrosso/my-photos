package util;


import model.attribute.Attribute;
import model.attribute.AttributeSet;
import model.attribute.Value;
import model.attribute.Values;
import model.photo.element.Photo;
import model.photo.element.PhotoAlbum;
import model.photo.element.PhotoElement;
import model.photo.element.PhotoYear;

import java.util.Random;

import static model.photo.PhotoKind.*;


public class PhotoElementUtilsTest {
    public static final String COLLECTION_NAME = "collectionName";
    public static final Values COLLECTION_VALUES = COLLECTION.valuesBuilderFor(COLLECTION_NAME).build();
    public static final int YEAR_NAME = 1973;
    public static final Values YEAR_VALUES = YEAR.valuesBuilderFor(String.valueOf(YEAR_NAME)).build();
    public static final String ALBUM_NAME = "Pablo's Birthday";
    public static final int ALBUM_MONTH = 12;
    public static final int ALBUM_DAY = 2;
    public static final Values ALBUM_VALUES = ALBUM
                                    .valuesBuilderFor(ALBUM_NAME)
                                    .with(new Value(PhotoAlbum.F_MONTH, ALBUM_MONTH))
                                    .with(new Value(PhotoAlbum.F_DAY, ALBUM_DAY))
                                    .build();
    public static final String PHOTO_NAME = "test-photo";
    public static final long PHOTO_SIZE = 1234567890L;
    public static final Values PHOTO_VALUES = Photo.valuesFor(PHOTO_NAME, PHOTO_SIZE);

    private static Random random = new Random();
    private static int photoCount;


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

    public static Values newNameValue(String name) {
        return Values.builderFor(AttributeSet.of(PhotoElement.F_NAME))
                .with(new Value(PhotoElement.F_NAME, name)).build();
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

    public static PhotoElement createCollection() {
        return COLLECTION.create(null, COLLECTION_VALUES);
    }

    public static PhotoElement createCollection(String name) {
        return COLLECTION.create(null, COLLECTION.valuesBuilderFor(name).build());
    }

    public static PhotoElement createYear() {
        return createYear(YEAR_NAME);
    }

    public static PhotoElement createYear(int year) {
        return createYear(createCollection(), year);
    }

    public static PhotoElement createYear(PhotoElement collection, int year) {
        return YEAR.create(collection, YEAR.valuesBuilderFor(String.valueOf(year)).build());
    }

    public static PhotoElement createAlbum() {
        return createAlbum(createYear(), ALBUM_MONTH, ALBUM_DAY, ALBUM_NAME);
    }

    public static PhotoElement createAlbum(PhotoElement year, int month, int date, String name) {
        return ALBUM.create(year, PhotoAlbum.valuesFor(name, ((PhotoYear)year).getYear(), month, date));
    }

    public static PhotoElement createPhoto() {
        return PHOTO.create(createAlbum(), PHOTO_VALUES);
    }

    public static PhotoElement createSeqPhoto(PhotoElement album) {
        return createPhoto(album, String.format("photo%04d", photoCount++), random.nextLong());
    }

    public static PhotoElement createPhoto(PhotoElement album, String name, long size) {
        return PHOTO.create(album, Photo.valuesFor(name, size));
    }


    /*

    public static final AttributeMap ATTRS_COLLECTION = PhotoCollection.attributeMapFor(COLLECTION_NAME);

    public static final AttributeMap ATTRS_YEAR = PhotoYear.attributeMapFor(YEAR_NAME);

    public static final AttributeMap ATTRS_ALBUM = PhotoAlbum.attributeMapFor(ALBUM_NAME, ALBUM_MONTH, ALBUM_DAY);

    public static final String PHOTO_NAME = "pabs01.jpg";
    public static final long PHOTO_SIZE = 123456789012345L;
    public static final AttributeMap ATTRS_PHOTO = Photo.attributeMapFor(PHOTO_NAME, PHOTO_SIZE);

    private static int yearCount = 1973;
    private static int albumCount = 1;
    private static int photoCount = 1;





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

    */
}

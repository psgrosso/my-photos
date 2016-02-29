package util;


import model.attribute.AttributeMap;
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
}

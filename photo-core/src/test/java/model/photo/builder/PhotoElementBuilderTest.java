package model.photo.builder;


public class PhotoElementBuilderTest {
/*

TODO test children are sorted properly

    @Test
    public void testHappyPath() {
        PhotoElementBuilder builder = new PhotoElementBuilder(PhotoElementUtilsTest.ATTRS_COLLECTION);
        PhotoCollection collection = builder.build();

        assertNotNull(collection);
        assertEquals(PhotoCollection.class, collection.getClass());
        assertEquals(COLLECTION, collection.getKind());
        assertEquals(COLLECTION_NAME, collection.getAttribute(PhotoCollection.F_NAME));
        assertEquals(collection, new PhotoCollection(PhotoElementUtilsTest.ATTRS_COLLECTION));

    }

    @Test
    public void testChildren() {
        PhotoElementBuilder collectionBuilder = new PhotoElementBuilder(PhotoElementUtilsTest.ATTRS_COLLECTION);

        for (AttributeMap yearAttribute : PhotoElementUtilsTest.createYearAttributes(10)) {
            PhotoElementBuilder yearBuilder = collectionBuilder.addChild(yearAttribute);

            for (AttributeMap albumAttribute : PhotoElementUtilsTest.createAlbumAttributes(9)) {
                PhotoElementBuilder albumBuilder = yearBuilder.addChild(albumAttribute);

                for (AttributeMap photoAttribute : PhotoElementUtilsTest.createPhotoAttributes(9)) {
                    albumBuilder.addChild(photoAttribute);
                }
            }
        }
        collectionBuilder.build();
    }

    @Test
    public void testYears() {
        PhotoElementBuilder mainCollection = new PhotoElementBuilder(PhotoElementUtilsTest.ATTRS_COLLECTION);
        PhotoElementBuilder year1973Builder = mainCollection.addChild(PhotoElementUtilsTest.ATTRS_YEAR);
        //PhotoElementBuilder year2016Builder = mainCollection.addChild(new AttributeMap(PhotoYear.F_NAME, "2016"));

        PhotoCollection collection = mainCollection.build();
        System.out.println(collection);

        assertNotNull(collection);
        assertEquals(PhotoCollection.class, collection.getClass());
        assertEquals(COLLECTION, collection.getKind());
        assertEquals(COLLECTION_NAME, collection.getAttribute(PhotoCollection.F_NAME));

        // Check parent
        PhotoElement year1973 = collection.getChild(YEAR_NAME);
        assertSame(collection, year1973.getParent());
        assertSame(collection, collection.getChild("2016").getParent());
        //assertEquals(COLLECTION_NAME + "|" + YEAR_NAME, year1973.getGlobalId());
    }

    public void testYearInvalid() {
        PhotoElementBuilder mainCollection = new PhotoElementBuilder(PhotoElementUtilsTest.ATTRS_COLLECTION);
        PhotoElementBuilder year1973 = mainCollection.addChild(PhotoElementUtilsTest.ATTRS_YEAR);
        //PhotoElementBuilder year2016 = mainCollection.addChild(new AttributeMap(PhotoYear.F_NAME, "2016"));

        PhotoCollection collection = mainCollection.build();
    }

    @Test
    public void test() {
        PhotoElementBuilder mainCollection = new PhotoElementBuilder(PhotoElementUtilsTest.ATTRS_COLLECTION);
        PhotoElementBuilder year1973 = mainCollection.addChild(PhotoElementUtilsTest.ATTRS_YEAR);
        year1973.addChild(PhotoElementUtilsTest.ATTRS_ALBUM);

        PhotoCollection collection = mainCollection.build();
        System.out.println(collection);
    }*/
}

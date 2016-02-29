package model.attribute;

import org.testng.annotations.Test;

import static org.testng.AssertJUnit.*;


public final class AttributeMapTest {
    private static final String STRING_KEY = "strKey";
    private static final String INTEGER_KEY = "intKey";
    private static final String LONG_KEY = "longKey";
    private static final String STRING_VALUE = "value";
    private static final long LONG_VALUE = 197312021100L;
    private static final Attribute<String> stringAttribute = Attribute.newStringAttribute(STRING_KEY);
    private static final Attribute<Integer> integerAttribute = Attribute.newIntegerAttribute(INTEGER_KEY);
    private static final Attribute<Long> longAttribute = Attribute.newLongAttribute(LONG_KEY);

    @Test
    public void testSingleAttributeInteger() {
        AttributeMap attributeMap = AttributeMap.builderFor(AttributeSet.of(integerAttribute))
                                        .with(integerAttribute, 1973).build();
        assertNotNull(attributeMap.get(integerAttribute));
        assertEquals(1973, (int) attributeMap.get(integerAttribute));
    }

    @Test
    public void testSingleAttributeLong() {
        AttributeMap attributeMap = AttributeMap.builderFor(AttributeSet.of(longAttribute))
                                        .with(longAttribute, LONG_VALUE).build();
        assertNotNull(attributeMap.get(longAttribute));
        assertEquals(LONG_VALUE, (long) attributeMap.get(longAttribute));
    }

    @Test
    public void testSingleAttributeString() {
        AttributeMap attributeMap = AttributeMap.builderFor(AttributeSet.of(stringAttribute))
                                        .with(stringAttribute, STRING_VALUE).build();
        assertNotNull(attributeMap.get(stringAttribute));
        assertEquals(STRING_VALUE, attributeMap.get(stringAttribute));
    }

    @Test
    public void testBuilder() {
        AttributeMap.Builder builder = AttributeMap.builderFor(
                AttributeSet.of(longAttribute, stringAttribute, integerAttribute));
        AttributeMap attributeMap = builder
                .with(longAttribute, LONG_VALUE)
                .with(integerAttribute, 1973)
                .with(stringAttribute, STRING_VALUE)
                .build();

        assertEquals(LONG_VALUE, (long) attributeMap.get(longAttribute));
        assertEquals(1973, (long) attributeMap.get(integerAttribute));
        assertEquals(STRING_VALUE, attributeMap.get(stringAttribute));

        // Compare with another attributeMap created in different order
        assertEquals(attributeMap,
                AttributeMap.builderFor(
                        AttributeSet.of(longAttribute, integerAttribute, stringAttribute))
                        .with(integerAttribute, 1973)
                        .with(stringAttribute, STRING_VALUE)
                        .with(longAttribute, LONG_VALUE)
                        .build());

        // Compare with other attributeMap object not equal
        assertFalse(
                attributeMap.equals(
                    AttributeMap.builderFor(AttributeSet.of(integerAttribute))
                            .with(stringAttribute, STRING_VALUE)
                            .with(integerAttribute, 1973)
                            .with(longAttribute, LONG_VALUE)
                            .build()));

        assertFalse(
                attributeMap.equals(
                    AttributeMap.builderFor(AttributeSet.of(integerAttribute, longAttribute))
                            .with(stringAttribute, STRING_VALUE)
                            .with(longAttribute, LONG_VALUE)
                            .build()));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIllegalKey() {
        AttributeMap attributeMap = AttributeMap.builderFor(AttributeSet.of(integerAttribute, longAttribute))
                                                .with(integerAttribute, 1973).build();
        attributeMap.get(longAttribute);
    }
}

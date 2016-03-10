package model.attribute;

import org.testng.annotations.Test;

import static org.testng.AssertJUnit.*;


public final class ValuesTest {
    private static final String STRING_KEY = "strKey";
    private static final String INTEGER_KEY = "intKey";
    private static final String LONG_KEY = "longKey";
    private static final String STRING_VALUE = "value";
    private static final long LONG_VALUE = 197312021100L;
    private static final Attribute stringAttribute = Attribute.newStringAttribute(STRING_KEY);
    private static final Attribute integerAttribute = Attribute.newIntegerAttribute(INTEGER_KEY);
    private static final Attribute longAttribute = Attribute.newLongAttribute(LONG_KEY);
    private static final Value integerValue = new Value(integerAttribute, 1973);
    private static final Value stringValue = new Value(stringAttribute, STRING_VALUE);
    private static final Value longValue = new Value(longAttribute, LONG_VALUE);

    @Test
    public void testSingleAttributeInteger() {
        Values values = Values.builderFor(AttributeSet.of(integerAttribute))
                                        .with(integerValue).build();
        System.out.println("Testing: " + values);

        assertNotNull(values.get(integerAttribute));
        assertEquals(integerValue, values.get(integerAttribute));

        AttributeSet attributeSet = AttributeSet.of(integerAttribute);
        assertEquals(attributeSet, values.getAttributes());
        assertEquals(values, values.subset(attributeSet));
    }

    @Test
    public void testSingleAttributeLong() {
        Values values = Values.builderFor(AttributeSet.of(longAttribute))
                                        .with(longValue).build();
        assertNotNull(values.get(longAttribute));
        assertEquals(longValue, values.get(longAttribute));

        AttributeSet attributeSet = AttributeSet.of(longAttribute);
        assertEquals(attributeSet, values.getAttributes());
        assertEquals(values, values.subset(attributeSet));
    }

    @Test
    public void testSingleAttributeString() {
        Values values = Values.builderFor(AttributeSet.of(stringAttribute))
                                        .with(stringValue).build();
        assertNotNull(values.get(stringAttribute));
        assertEquals(stringValue, values.get(stringAttribute));

        AttributeSet attributeSet = AttributeSet.of(stringAttribute);
        assertEquals(attributeSet, values.getAttributes());
        assertEquals(values, values.subset(attributeSet));
    }

    @Test
    public void testBuilder() {
        Values values = Values.builderFor(
                AttributeSet.of(longAttribute, stringAttribute, integerAttribute))
                .with(longValue)
                .with(integerValue)
                .with(stringValue)
                .build();

        assertEquals(longValue, values.get(longAttribute));
        assertEquals(integerValue, values.get(integerAttribute));
        assertEquals(stringValue, values.get(stringAttribute));
        assertEquals(AttributeSet.of(integerAttribute, longAttribute, stringAttribute),
                values.getAttributes());

        // Compare with another attributeMap created in different order
        AttributeSet attributeSet = AttributeSet.of(longAttribute, integerAttribute, stringAttribute);
        assertEquals(values,
                Values.builderFor(
                        attributeSet)
                        .with(integerValue)
                        .with(stringValue)
                        .with(longValue)
                        .build());

        // Compare with other attributeMap object not equal
        assertFalse(
                values.equals(
                    Values.builderFor(AttributeSet.of(integerAttribute))
                            .with(integerValue)
                            .build()));

        AttributeSet smallerAttributeSet = AttributeSet.of(stringAttribute, longAttribute);
        Values smallerValues = Values.builderFor(smallerAttributeSet)
                .with(stringValue)
                .with(longValue)
                .build();
        assertFalse(values.equals(smallerValues));

        // Test subsets
        Values smallestValues = Values.builderFor(AttributeSet.of(stringAttribute))
                                        .with(stringValue).build();
        assertEquals(values, values.subset(attributeSet));
        assertEquals(smallerValues, values.subset(smallerAttributeSet));
        assertEquals(smallestValues, values.subset(AttributeSet.of(stringAttribute)));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testSubset() {
        Values values = Values.builderFor(AttributeSet.of(stringAttribute, longAttribute))
                .with(stringValue)
                .with(longValue)
                .build();
        values.subset(AttributeSet.of(stringAttribute, longAttribute, integerAttribute));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testMissingAttribute() {
        Values.builderFor(AttributeSet.of(integerAttribute, longAttribute))
                                                .with(integerValue).build();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIllegalKey() {
        Values.builderFor(AttributeSet.of(integerAttribute, stringAttribute))
                .with(integerValue).with(longValue);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIllegalGet() {
        Values attributeMap = Values.builderFor(AttributeSet.of(integerAttribute, longAttribute))
                .with(integerValue).with(longValue).build();
        // Will throw an exception
        attributeMap.get(stringAttribute);
    }
}

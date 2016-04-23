package model.attribute;

import org.testng.annotations.Test;

import static org.testng.AssertJUnit.*;
import static util.PhotoElementUtilsTest.*;



public final class ValuesTest {
    private static final String STRING_VALUE = "string-value";
    private static final long LONG_VALUE = 197312021100L;

    @Test
    public void testSingleAttributeInteger() {
        Values values = valuesFrom(1973);
        Values sameValues = valuesFrom(1973);
        System.out.println("Testing: " + values);

        assertEquals(values, sameValues);
        assertEquals(0, values.compareTo(sameValues));
        assertEquals(0, sameValues.compareTo(values));
        assertEquals(0, values.compareTo(values));
        assertNotNull(values.get(integerAttribute(1)));
        assertEquals(newValue(1973, 1), values.get(integerAttribute(1)));

        // Test attribute set
        AttributeSet attributeSet = setFrom(1973);
        assertEquals(attributeSet, values.getAttributes());
        assertEquals(values, values.subset(attributeSet));
    }

    @Test
    public void testSingleAttributeIntegerComparison() {
        Values values1963 = valuesFrom(1963);
        Values values1973 = valuesFrom(1973);

        assertTrue(values1963.compareTo(values1973) < 0);
        assertTrue(values1973.compareTo(values1963) > 0);

        // Test with 2 integer attributes
        Values values1 = valuesFrom(1963, 12);
        Values values2 = valuesFrom(1963, 15);
        Values values3 = valuesFrom(1973, 12);

        assertTrue(values1.compareTo(values2) < 0);
        assertTrue(values2.compareTo(values1) > 0);
        assertTrue(values1.compareTo(values3) < 0);
        assertTrue(values3.compareTo(values1) > 0);
        assertTrue(values2.compareTo(values3) < 0);
        assertTrue(values3.compareTo(values2) > 0);
    }

    @Test
    public void testSingleAttributeLong() {
        Values values = valuesFrom(LONG_VALUE);
        Attribute longAttribute = longAttribute(1);
        assertNotNull(values.get(longAttribute));
        assertEquals(newValue(LONG_VALUE, 1), values.get(longAttribute));

        AttributeSet attributeSet = AttributeSet.of(longAttribute);
        assertEquals(attributeSet, values.getAttributes());
        assertEquals(values, values.subset(attributeSet));
    }

    @Test
    public void testSingleAttributeString() {
        Values values = valuesFrom(STRING_VALUE);
        Attribute stringAttribute = stringAttribute(1);
        assertNotNull(values.get(stringAttribute));
        assertEquals(newValue(STRING_VALUE, 1), values.get(stringAttribute));

        AttributeSet attributeSet = AttributeSet.of(stringAttribute);
        assertEquals(attributeSet, values.getAttributes());
        assertEquals(values, values.subset(attributeSet));
    }

    @Test
    public void testSingleAttributeStringComparison() {
        Values values1 = valuesFrom("abd");
        Values values2 = valuesFrom("abc");
        assertTrue(values1.compareTo(values2) > 0);
        assertTrue(values2.compareTo(values1) < 0);
    }

    @Test
    public void testMultipleAttributeComparison() {
        Values values1 = valuesFrom(1973, 12, "pablo");
        Values values2 = valuesFrom(1973, 12, "sebas");
        Values values3 = valuesFrom(1973, 12, "grosso");

        assertTrue(values1.compareTo(values2) < 0);
        assertTrue(values1.compareTo(values3) > 0);
        assertTrue(values2.compareTo(values3) > 0);
        assertTrue(values2.compareTo(values1) > 0);
        assertTrue(values3.compareTo(values1) < 0);
        assertTrue(values3.compareTo(values2) < 0);
    }

    @Test
    public void testBuilder() {
        Attribute longAttribute = longAttribute(1);
        Attribute integerAttribute = integerAttribute(2);
        Attribute stringAttribute = stringAttribute(3);
        Values values = Values.builderFor(
                AttributeSet.of(longAttribute, stringAttribute, integerAttribute))
                .with(newValue(LONG_VALUE, 1))
                .with(newValue(STRING_VALUE, 3))
                .with(newValue(1973, 2))
                .build();

        assertEquals(newValue(LONG_VALUE, 1), values.get(longAttribute));
        assertEquals(newValue(STRING_VALUE, 3), values.get(stringAttribute));
        assertEquals(newValue(1973, 2), values.get(integerAttribute));
        assertEquals(AttributeSet.of(longAttribute, stringAttribute, integerAttribute),
                values.getAttributes());

        // Compare with another attributeMap created in different order
        assertFalse(values.equals(valuesFrom(LONG_VALUE, STRING_VALUE, 1973)));
        assertFalse(values.equals(valuesFrom(1973, LONG_VALUE, STRING_VALUE)));
        assertFalse(values.equals(valuesFrom(1973)));

        // Test subsets
        AttributeSet attributeSet = AttributeSet.of(newAttribute(STRING_VALUE, 3));
        Values smallestValues = Values.builderFor(attributeSet)
                                        .with(newValue(STRING_VALUE, 3)).build();
        assertEquals(smallestValues, values.subset(attributeSet));
        assertEquals(attributeSet, smallestValues.getAttributes());
        assertEquals(values, values.subset(AttributeSet.of(longAttribute, stringAttribute, integerAttribute)));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIncompatibleComparison() {
        Values values1 = valuesFrom(1963);
        Values values2 = valuesFrom(1973, LONG_VALUE);
        values1.compareTo(values2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testInvalidSubset() {
        Values values = valuesFrom(STRING_VALUE, LONG_VALUE);
        values.subset(setFrom(STRING_VALUE, LONG_VALUE, 12));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testMissingAttribute() {
        Values.builderFor(setFrom(1973, LONG_VALUE)).with(newValue(1973, 1)).build();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testExtraAttribute() {
        Values.builderFor(setFrom(1973, LONG_VALUE))
                        .with(newValue(1973, 1))
                        .with(newValue(LONG_VALUE, 2))
                        .with(newValue("pablo", 3));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIllegalGet() {
        Values values = valuesFrom(1973, LONG_VALUE);
        // Will throw an exception
        values.get(newAttribute(1973, 2));
    }
}

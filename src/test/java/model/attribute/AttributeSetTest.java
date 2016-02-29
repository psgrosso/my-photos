package model.attribute;


import static org.testng.AssertJUnit.*;

import org.testng.annotations.Test;

public class AttributeSetTest {
    private static final String STRING_NAME1 = "str-name1";
    private static final Attribute<String> stringAttribute1 = Attribute.newStringAttribute(STRING_NAME1);
    private static final String STRING_NAME2 = "str-name2";
    private static final Attribute<String> stringAttribute2 = Attribute.newStringAttribute(STRING_NAME2);
    private static final String INTEGER_NAME1 = "int-name1";
    private static final Attribute<Integer> stringAttribute = Attribute.newIntegerAttribute(INTEGER_NAME1);

    @Test
    public void testSingleAttribute() {
        AttributeSet attributeSet = AttributeSet.of(stringAttribute1);
        Attribute<String> sameStringAttribute = Attribute.newStringAttribute(STRING_NAME1);
        AttributeSet sameAttributeSet = AttributeSet.of(sameStringAttribute);

        assertEquals(1, attributeSet.size());

        // Test contains() with attributes
        assertTrue(attributeSet.contains(stringAttribute1));
        assertTrue(attributeSet.contains(sameStringAttribute));

        // Test contains() with attribute set
        assertTrue(attributeSet.contains(sameAttributeSet));
        assertEquals(attributeSet, sameAttributeSet);

        for (Attribute<?> attribute : attributeSet) {

        }
    }

    @Test
    public void testMultipleAttributes() {

    }
}

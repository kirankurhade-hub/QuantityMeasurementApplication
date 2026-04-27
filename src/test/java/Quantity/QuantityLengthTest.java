package Quantity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import quantity.LengthUnit;
import quantity.QuantityLength;

public class QuantityLengthTest {

    private static final double EPSILON = 0.000001;

    @Test
    public void testConversion_FeetToInches() {
        assertEquals(12.0,
                QuantityLength.convert(1.0, LengthUnit.FEET, LengthUnit.INCHES),
                EPSILON);
    }

    @Test
    public void testConversion_CentimetersToInches() {
        assertEquals(1.0,
                QuantityLength.convert(2.54, LengthUnit.CENTIMETERS, LengthUnit.INCHES),
                EPSILON);
    }

    @Test
    public void testConversion_RoundTrip() {
        double value = 5.0;

        double converted =
                QuantityLength.convert(value, LengthUnit.FEET, LengthUnit.INCHES);

        double back =
                QuantityLength.convert(converted, LengthUnit.INCHES, LengthUnit.FEET);

        assertEquals(value, back, EPSILON);
    }

    @Test
    public void testConversion_InvalidUnit_Throws() {
        assertThrows(IllegalArgumentException.class, () ->
                QuantityLength.convert(1.0, null, LengthUnit.FEET));
    }

    @Test
    public void testConversion_NaN_Throws() {
        assertThrows(IllegalArgumentException.class, () ->
                QuantityLength.convert(Double.NaN, LengthUnit.FEET, LengthUnit.INCHES));
    }

    @Test
    public void testEquality_SameReference() {
        QuantityLength q1 = new QuantityLength(2.0, LengthUnit.YARDS);
        assertTrue(q1.equals(q1));
    }
}
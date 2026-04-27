package Quantity;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import quantity.Length;
import quantity.LengthUnit;

public class QuantityMeasurementAppTest {

    private static final double EPSILON = 0.01;

    @Test
    public void testLengthUnitEnum_FeetConstant() {
        assertEquals(1.0, LengthUnit.FEET.getConversionFactor(), EPSILON);
    }

    @Test
    public void testConvertToBaseUnit_InchesToFeet() {
        assertEquals(1.0,
                LengthUnit.INCHES.convertToBaseUnit(12.0),
                EPSILON);
    }

    @Test
    public void testConvertFromBaseUnit_FeetToYards() {
        assertEquals(1.0,
                LengthUnit.YARDS.convertFromBaseUnit(3.0),
                EPSILON);
    }

    @Test
    public void testQuantityLengthRefactored_Equality() {
        assertTrue(new Length(1, LengthUnit.FEET)
                .equals(new Length(12, LengthUnit.INCHES)));
    }

    @Test
    public void testQuantityLengthRefactored_ConvertTo() {
        Length result = new Length(1, LengthUnit.FEET)
                .convertTo(LengthUnit.INCHES);
        assertEquals(new Length(12, LengthUnit.INCHES), result);
    }

    @Test
    public void testQuantityLengthRefactored_Add() {
        Length result =
                new Length(1, LengthUnit.FEET)
                        .add(new Length(12, LengthUnit.INCHES),
                                LengthUnit.FEET);

        assertEquals(new Length(2, LengthUnit.FEET), result);
    }

    @Test
    public void testRoundTripConversion_RefactoredDesign() {
        Length original = new Length(5, LengthUnit.FEET);
        Length converted = original.convertTo(LengthUnit.INCHES);
        Length back = converted.convertTo(LengthUnit.FEET);

        assertEquals(original, back);
    }

    @Test
    public void testNullUnit() {
        assertThrows(IllegalArgumentException.class,
                () -> new Length(1, null));
    }

   
}
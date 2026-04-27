package Quantity;

import quantity.LengthUnit;
import quantity.QuantityLength;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class QuantityLengthTest {

    // Yard to Yard (Same)
    @Test
    public void testEquality_YardToYard_SameValue() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength q2 = new QuantityLength(1.0, LengthUnit.YARDS);

        assertTrue(q1.equals(q2));
    }

    // Yard to Yard (Different)
    @Test
   public void testEquality_YardToYard_DifferentValue() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength q2 = new QuantityLength(2.0, LengthUnit.YARDS);

        assertFalse(q1.equals(q2));
    }

    // Yard to Feet
    @Test
    public void testEquality_YardToFeet_EquivalentValue() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength q2 = new QuantityLength(3.0, LengthUnit.FEET);

        assertTrue(q1.equals(q2));
    }

    // Yard to Inches
    @Test
    public void testEquality_YardToInches_EquivalentValue() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength q2 = new QuantityLength(36.0, LengthUnit.INCHES);

        assertTrue(q1.equals(q2));
    }

    // Centimeters to Inches
    @Test
    public void testEquality_CentimetersToInches_EquivalentValue() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.CENTIMETERS);
        QuantityLength q2 = new QuantityLength(0.393701, LengthUnit.INCHES);

        assertTrue(q1.equals(q2));
    }

    // Centimeters to Feet (Non Equivalent)
    @Test
    public void testEquality_CentimetersToFeet_NonEquivalentValue() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.CENTIMETERS);
        QuantityLength q2 = new QuantityLength(1.0, LengthUnit.FEET);

        assertFalse(q1.equals(q2));
    }

    // Reflexive
    @Test
    public void testEquality_SameReference() {
        QuantityLength q1 = new QuantityLength(2.0, LengthUnit.YARDS);

        assertTrue(q1.equals(q1));
    }

    // Null comparison
    @Test
    public void testEquality_NullComparison() {
        QuantityLength q1 = new QuantityLength(2.0, LengthUnit.YARDS);

        assertFalse(q1.equals(null));
    }

    // Transitive Property
    @Test
    public void testEquality_MultiUnit_TransitiveProperty() {

        QuantityLength yard = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength feet = new QuantityLength(3.0, LengthUnit.FEET);
        QuantityLength inch = new QuantityLength(36.0, LengthUnit.INCHES);

        assertTrue(yard.equals(feet));
        assertTrue(feet.equals(inch));
        assertTrue(yard.equals(inch));
    }

    // Complex Multi Unit
    @Test
    public void testEquality_AllUnits_ComplexScenario() {

        QuantityLength q1 = new QuantityLength(2.0, LengthUnit.YARDS);
        QuantityLength q2 = new QuantityLength(6.0, LengthUnit.FEET);
        QuantityLength q3 = new QuantityLength(72.0, LengthUnit.INCHES);

        assertTrue(q1.equals(q2));
        assertTrue(q2.equals(q3));
        assertTrue(q1.equals(q3));
    }
}

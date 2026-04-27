package Quantity;

import org.junit.Test;

import quantity.QuantityMeasurementApp;
import quantity.QuantityWeight;
import quantity.WeightUnit;

import static org.junit.Assert.*;

public class QuantityMeasurementAppTest {

	   @Test
	    public void testEquality_KilogramToGram() {
	        QuantityWeight kg = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
	        QuantityWeight gram = new QuantityWeight(1000.0, WeightUnit.GRAM);

	        assertTrue(QuantityMeasurementApp.demonstrateWeightEquality(kg, gram));
	    }

	    @Test
	    public void testEquality_KilogramToPound() {
	        QuantityWeight kg = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
	        QuantityWeight pound = new QuantityWeight(2.20462, WeightUnit.POUND);

	        assertTrue(QuantityMeasurementApp.demonstrateWeightEquality(kg, pound));
	    }

	    @Test
	    public void testNegativeWeightEquality() {
	        QuantityWeight kg = new QuantityWeight(-1.0, WeightUnit.KILOGRAM);
	        QuantityWeight gram = new QuantityWeight(-1000.0, WeightUnit.GRAM);

	        assertTrue(kg.equals(gram));
	    }

	    @Test
	    public void testZeroWeightEquality() {
	        QuantityWeight kg = new QuantityWeight(0.0, WeightUnit.KILOGRAM);
	        QuantityWeight gram = new QuantityWeight(0.0, WeightUnit.GRAM);

	        assertTrue(kg.equals(gram));
	    }


	    @Test
	    public void testConversion_PoundToKilogram() {
	        QuantityWeight pound = new QuantityWeight(2.20462, WeightUnit.POUND);

	        QuantityWeight result =
	                QuantityMeasurementApp.demonstrateWeightConversion(
	                        pound, WeightUnit.KILOGRAM);

	        assertEquals(new QuantityWeight(1.0, WeightUnit.KILOGRAM), result);
	    }

	    @Test
	    public void testConversion_KilogramToGram() {
	        QuantityWeight kg = new QuantityWeight(1.0, WeightUnit.KILOGRAM);

	        QuantityWeight result =
	                QuantityMeasurementApp.demonstrateWeightConversion(
	                        1.0, WeightUnit.KILOGRAM, WeightUnit.GRAM);

	        assertEquals(new QuantityWeight(1000.0, WeightUnit.GRAM), result);
	    }

	    @Test
	    public void testAddition_CrossUnit_DefaultTarget() {
	        QuantityWeight kg = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
	        QuantityWeight gram = new QuantityWeight(1000.0, WeightUnit.GRAM);

	        QuantityWeight result =
	                QuantityMeasurementApp.demonstrateWeightAddition(kg, gram);

	        assertEquals(new QuantityWeight(2.0, WeightUnit.KILOGRAM), result);
	    }

	    @Test
	    public void testAddition_ExplicitTarget() {
	        QuantityWeight kg = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
	        QuantityWeight gram = new QuantityWeight(1000.0, WeightUnit.GRAM);

	        QuantityWeight result =
	                QuantityMeasurementApp.demonstrateWeightAddition(
	                        kg, gram, WeightUnit.GRAM);

	        assertEquals(new QuantityWeight(2000.0, WeightUnit.GRAM), result);
	    }

	    
	    @Test(expected = IllegalArgumentException.class)
	    public void testNullUnitInConstructor() {
	        new QuantityWeight(1.0, null);
	    }

	    @Test(expected = IllegalArgumentException.class)
	    public void testNullOtherInAddition() {
	        QuantityWeight kg = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
	        kg.add(null);
	    }

	    @Test(expected = IllegalArgumentException.class)
	    public void testNullTargetInConversion() {
	        QuantityWeight kg = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
	        kg.convertTo(null);
	    }

}
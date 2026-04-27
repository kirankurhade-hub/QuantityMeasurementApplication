package Quantity;

import org.junit.Test;

import quantity.Length;
import quantity.Length.LengthUnit;

import static org.junit.Assert.*;

public class QuantityMeasurementAppTest {
	
	@Test
	public void testAddition_ExplicitTargetUnit_Feet(){
		Length length1 = new Length(3, LengthUnit.FEET);
		Length length2 = new Length(24, LengthUnit.INCHES);

		Length result = new Length(5, LengthUnit.FEET);
		assertEquals(result, length1.add(length2, LengthUnit.FEET));
	}

	@Test
	public void testAddition_ExplicitTargetUnit_Inches(){
		Length length1 = new Length(2, LengthUnit.FEET);
		Length length2 = new Length(6, LengthUnit.INCHES);

		Length result = new Length(30, LengthUnit.INCHES);
		assertEquals(result, length1.add(length2, LengthUnit.INCHES));
	}

	@Test
	public void testAddition_ExplicitTargetUnit_Yards(){
		Length length1 = new Length(3, LengthUnit.FEET);
		Length length2 = new Length(3, LengthUnit.FEET);

		Length result = new Length(2, LengthUnit.YARDS);
		assertEquals(result, length1.add(length2, LengthUnit.YARDS));
	}

	@Test
	public void testAddition_ExplicitTargetUnit_Centimeters(){
		Length length1 = new Length(2, LengthUnit.INCHES);
		Length length2 = new Length(3, LengthUnit.INCHES);

		Length result = new Length(12.7, LengthUnit.CENTIMETERS);
		assertEquals(result, length1.add(length2, LengthUnit.CENTIMETERS));
	}

	@Test
	public void testAddition_ExplicitTargetUnit_SameAsFirstOperand(){
		Length length1 = new Length(4, LengthUnit.YARDS);
		Length length2 = new Length(6, LengthUnit.FEET);

		Length result = new Length(6, LengthUnit.YARDS);
		assertEquals(result, length1.add(length2, LengthUnit.YARDS));
	}

	@Test
	public void testAddition_ExplicitTargetUnit_SameAsSecondOperand(){
		Length length1 = new Length(1, LengthUnit.YARDS);
		Length length2 = new Length(3, LengthUnit.FEET);

		Length result = new Length(6, LengthUnit.FEET);
		assertEquals(result, length1.add(length2, LengthUnit.FEET));
	}

	@Test
	public void testAddition_ExplicitTargetUnit_Commutativity(){
		Length length1 = new Length(4, LengthUnit.FEET);
		Length length2 = new Length(8, LengthUnit.INCHES);

		Length result1 = length1.add(length2, LengthUnit.FEET);
		Length result2 = length2.add(length1, LengthUnit.FEET);

		assertEquals(result1, result2);
	}

	@Test
	public void testAddition_ExplicitTargetUnit_WithZero(){
		Length length1 = new Length(9, LengthUnit.FEET);
		Length length2 = new Length(0, LengthUnit.INCHES);

		Length result = new Length(3, LengthUnit.YARDS);
		assertEquals(result, length1.add(length2, LengthUnit.YARDS));
	}

	@Test
	public void testAddition_ExplicitTargetUnit_NegativeValues(){
		Length length1 = new Length(10, LengthUnit.FEET);
		Length length2 = new Length(-4, LengthUnit.FEET);

		Length result = new Length(72, LengthUnit.INCHES);
		assertEquals(result, length1.add(length2, LengthUnit.INCHES));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddition_ExplicitTargetUnit_NullTargetUnit(){
		Length length1 = new Length(2, LengthUnit.FEET);
		Length length2 = new Length(5, LengthUnit.INCHES);

		length1.add(length2, null);
	}

	@Test
	public void testAddition_ExplicitTargetUnit_LargeToSmallScale(){
		Length length1 = new Length(200, LengthUnit.FEET);
		Length length2 = new Length(300, LengthUnit.FEET);

		Length result = new Length(6000, LengthUnit.INCHES);
		assertEquals(result, length1.add(length2, LengthUnit.INCHES));
	}

	@Test
	public void testAddition_ExplicitTargetUnit_SmallToLargeScale(){
		Length length1 = new Length(36, LengthUnit.INCHES);
		Length length2 = new Length(36, LengthUnit.INCHES);

		Length result = new Length(2, LengthUnit.YARDS);
		assertEquals(result, length1.add(length2, LengthUnit.YARDS));
	}

	@Test
	public void testAddition_ExplicitTargetUnit_PrecisionTolerance(){
		Length length1 = new Length(5, LengthUnit.FEET);
		Length length2 = new Length(7, LengthUnit.INCHES);

		Length result = new Length(1.86, LengthUnit.YARDS);
		assertEquals(result, length1.add(length2, LengthUnit.YARDS));
	}
}
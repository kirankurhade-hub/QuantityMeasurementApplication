package Quantity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class QuantityLengthTest {
	@Test
	public void testEquality_FeetToFeeet() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
		QuantityLength q2 = new QuantityLength(1.0, LengthUnit.FEET);
		assertTrue(q1.equals(q2));
	}
	@Test 
	public void testEquality_InchToInch() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.INCH);
		QuantityLength q2 = new QuantityLength(1.0, LengthUnit.INCH);
		assertTrue(q1.equals(q2));
	}
	@Test
	public void testEquality_InchToFeet() {
		QuantityLength q1 = new QuantityLength(12.0, LengthUnit.INCH);
		QuantityLength q2 = new QuantityLength(1.0, LengthUnit.FEET);
		assertTrue(q1.equals(q2));
	}
	@Test
	public void testEquality_Differentvalues() {
		QuantityLength q1 = new QuantityLength(1.0,LengthUnit.FEET);
		QuantityLength q2 = new QuantityLength(2.0,LengthUnit.FEET);
		assertFalse(q1.equals(q2));
	}
	@Test
	public void tetsEquaality_NullComaprison() {
		QuantityLength q1 = new QuantityLength(1.0,LengthUnit.FEET);
		assertFalse(q1.equals(null));
	}
	@Test
	public void testEquality_SameReference() {
		QuantityLength q1 = new QuantityLength(1.0,LengthUnit.FEET);
		assertTrue(q1.equals(q1));
	
	}
	@Test 
	public void testInvalidUnit() {
		assertThrows(IllegalArgumentException.class, ()->{
			new QuantityLength(1.0,null);
		});
	}
}

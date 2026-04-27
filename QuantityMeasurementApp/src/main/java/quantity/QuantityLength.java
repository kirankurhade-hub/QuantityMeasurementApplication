package quantity;

import java.util.Objects;

public class QuantityLength {
	private static final double EPSILON= 0.0001;
	private final double value;
	private final LengthUnit unit;
	public QuantityLength(double value,LengthUnit unit) {
		if(unit == null) {
			throw new IllegalArgumentException("Unit Cannot be null");
		}
		this.value = value;
		this.unit = unit;
	}
	public double getValue() {
		return value;
	}
	public LengthUnit getUnit() {
		return unit;
	}
	private double toBaseUnit() {
		return unit.toFeet(value);
	}
	@Override
	public boolean equals(Object obj) {
		if(this==obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(getClass() != obj.getClass()) {
			return false;
		}
		QuantityLength other  = (QuantityLength) obj;
		double thisvalue = this.toBaseUnit();
		double otherValue = other.toBaseUnit();
	    return Math.abs(thisvalue-otherValue)<EPSILON;
	}
	@Override
	public int hashCode() {
		return Objects.hash(toBaseUnit());
	}
	@Override
	public String toString() {
		return "Quantity(" + value+" , "+unit+")";
	}
	
}

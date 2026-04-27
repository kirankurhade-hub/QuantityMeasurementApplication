package quantity;

import java.util.Objects;

public final class QuantityLength {
	private static final double EPSILON= 0.0001;
	private final double value;
	private final LengthUnit unit;
	public QuantityLength(double value,LengthUnit unit) {
		if(!Double.isFinite(value)) {
			throw new IllegalArgumentException("Enter the finite value");
		}
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
	public static double convert(double value,LengthUnit from,LengthUnit to) {
		if(!Double.isFinite(value)) {
			throw new IllegalArgumentException("Value should  be finite");
		}
		if (from == null || to == null) {
            throw new IllegalArgumentException("Units cannot be null");
		}
		double baseValue = from.toFeet(value);
        return to.fromFeet(baseValue);

	}
	 public QuantityLength convertTo(LengthUnit target) {
	        double converted = convert(this.value, this.unit, target);
	        return new QuantityLength(converted, target);
	    }
	 public QuantityLength add(QuantityLength another) {
		 if(another == null) {
			 throw new IllegalArgumentException("Give the corrected argument");
		 }
		 double thisInFeet = this.toBaseUnit();
		 double anotherInFeet = another.toBaseUnit();
		 double sumInFeet = thisInFeet + anotherInFeet;
		 double result = this.unit.fromFeet(sumInFeet);
		 return new QuantityLength(result,this.unit);
	 }
	 private double toBaseUnit1() {
	        return unit.toFeet(value);
	    }
	 public QuantityLength add(QuantityLength another,LengthUnit target) {
		 if(another == null) {
			 throw new IllegalArgumentException("Please enter the corrected arguments");
		 }
		 if(target == null) {
			 throw new IllegalArgumentException("target should not be null");
		 }
		 double sumInFeet = sumInBaseUnit(another);
		 double resultInFeet = target.fromFeet(sumInFeet);
		 
		 return new QuantityLength(resultInFeet, target);
	 }
	 public double sumInBaseUnit(QuantityLength another) {
		 return this.toBaseUnit() + another.toBaseUnit();
	 }
	 @Override
	    public boolean equals(Object obj) {
	        if (this == obj) return true;
	        if (!(obj instanceof QuantityLength other)) return false;

	        return Math.abs(this.toBaseUnit() - other.toBaseUnit()) < EPSILON;
	    }
	 @Override
	    public int hashCode() {
	        return Objects.hash(toBaseUnit());
	    }
	 @Override
	    public String toString() {
	        return "Quantity(" + value + ", " + unit + ")";
	    }
	
}

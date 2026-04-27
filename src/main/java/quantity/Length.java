package quantity;
import java.util.Objects;

public class Length {

    private final double value;
    private final LengthUnit unit;

    public Length(double value, LengthUnit unit) {
        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");
        
        this.value = value;
        this.unit = unit;
    }

    public Length convertTo(LengthUnit targetUnit) {
        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        double base = convertToBaseUnit();
        double converted = convertFromBaseToTargetUnit(base, targetUnit);
        return new Length(converted, targetUnit);
    }

    public Length add(Length thatLength) {
        return addAndConvert(thatLength, this.unit);
    }

    public Length add(Length thatLength, LengthUnit targetUnit) {
        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");
        return addAndConvert(thatLength, targetUnit);
    }

    private Length addAndConvert(Length length, LengthUnit targetUnit) {
        double base1 = this.convertToBaseUnit();
        double base2 = length.convertToBaseUnit();
        double sumBase = base1 + base2;

        double converted = convertFromBaseToTargetUnit(sumBase, targetUnit);
        return new Length(converted, targetUnit);
    }

    private double convertToBaseUnit() {
        return unit.convertToBaseUnit(value);
    }

    private double convertFromBaseToTargetUnit(double baseValue, LengthUnit targetUnit) {
        return targetUnit.convertFromBaseUnit(baseValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Length)) return false;

        Length that = (Length) o;

        return Double.compare(this.convertToBaseUnit(),
                              that.convertToBaseUnit()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(convertToBaseUnit());
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }

    public static void main(String[] args) {

        // Conversion Tests
        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(12.0, LengthUnit.INCHES);
        Length l3 = new Length(1.0, LengthUnit.CENTIMETERS);

        System.out.println("---- Conversion Tests ----");
        System.out.println("1 Foot to Inches: " + l1.convertTo(LengthUnit.INCHES));
        System.out.println("12 Inches to Feet: " + l2.convertTo(LengthUnit.FEET));
        System.out.println("1 Meter to Feet: " + l3.convertTo(LengthUnit.FEET));

        // Addition Tests (same unit)
        System.out.println("\n---- Addition Same Unit ----");
        Length sum1 = l1.add(new Length(2.0, LengthUnit.FEET));
        System.out.println("1 ft + 2 ft = " + sum1);

        // Addition Tests (different units)
        System.out.println("\n---- Addition Different Units ----");
        Length sum2 = l1.add(l2);  // 1 ft + 12 inches
        System.out.println("1 ft + 12 inches (result in feet) = " + sum2);

        // Addition with Target Unit
        System.out.println("\n---- Addition With Target Unit ----");
        Length sum3 = l1.add(l2, LengthUnit.INCHES);
        System.out.println("1 ft + 12 inches (result in inches) = " + sum3);

        // Equality Test
        System.out.println("\n---- Equality Test ----");
        Length oneFoot = new Length(1.0, LengthUnit.FEET);
        Length twelveInches = new Length(12.0, LengthUnit.INCHES);

        System.out.println("1 ft equals 12 inches? " + oneFoot.equals(twelveInches));

        // More Complex Test
        System.out.println("\n---- Complex Test ----");
        Length complexSum = new Length(2, LengthUnit.CENTIMETERS)
                .add(new Length(3, LengthUnit.FEET), LengthUnit.CENTIMETERS);
   
        System.out.println("2 meters + 3 feet (in meters) = " + complexSum);
    }
}
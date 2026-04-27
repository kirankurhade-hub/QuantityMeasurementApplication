package quantity;
public class QuantityMeasurementApp {

    public static boolean demonstrateLengthEquality(Length l1, Length l2) {
        return l1.equals(l2);
    }
    public static boolean demonstrateLengthComparison(double v1, LengthUnit u1,
                                                      double v2, LengthUnit u2) {
        return new Length(v1, u1).equals(new Length(v2, u2));
    }
    public static Length demonstrateLengthConversion(double value,
                                                     LengthUnit from,
                                                     LengthUnit to) {
        return new Length(value, from).convertTo(to);
    }
    public static Length demonstrateLengthConversion(Length length,
                                                     LengthUnit to) {
        return length.convertTo(to);
    }
    public static Length demonstrateLengthAddition(Length l1, Length l2) {
        return l1.add(l2);
    }
    public static Length demonstrateLengthAddition(Length l1,
                                                   Length l2,
                                                   LengthUnit targetUnit) {
        return l1.add(l2, targetUnit);
    }
    public static void main(String[] args) {
        System.out.println(demonstrateLengthConversion(
                1.0, LengthUnit.FEET, LengthUnit.INCHES));

        System.out.println(demonstrateLengthConversion(
                12.0, LengthUnit.INCHES, LengthUnit.FEET));

        System.out.println(demonstrateLengthConversion(
                2.0, LengthUnit.CENTIMETERS, LengthUnit.FEET));

        Length l1 = new Length(5, LengthUnit.FEET);
        System.out.println(demonstrateLengthConversion(
                l1, LengthUnit.INCHES));


        Length oneFoot = new Length(1, LengthUnit.FEET);
        Length twelveInches = new Length(12, LengthUnit.INCHES);

        System.out.println("1 foot == 12 inches ? "
                + demonstrateLengthEquality(oneFoot, twelveInches));

        System.out.println("3 feet == 36 inches ? "
                + demonstrateLengthComparison(
                        3, LengthUnit.FEET,
                        36, LengthUnit.INCHES));

        System.out.println("1 meter == 3 feet ? "
                + demonstrateLengthComparison(
                        1, LengthUnit.CENTIMETERS,
                        3, LengthUnit.FEET));


        System.out.println("1 ft + 2 ft = "
                + demonstrateLengthAddition(
                        new Length(1, LengthUnit.FEET),
                        new Length(2, LengthUnit.FEET)));

        System.out.println("1 ft + 12 inches (in feet) = "
                + demonstrateLengthAddition(
                        new Length(1, LengthUnit.FEET),
                        new Length(12, LengthUnit.INCHES)));

        System.out.println("1 ft + 12 inches (in inches) = "
                + demonstrateLengthAddition(
                        new Length(1, LengthUnit.FEET),
                        new Length(12, LengthUnit.INCHES),
                        LengthUnit.INCHES));

        System.out.println("2 meters + 3 feet (in meters) = "
                + demonstrateLengthAddition(
                        new Length(2, LengthUnit.CENTIMETERS),
                        new Length(3, LengthUnit.FEET),
                        LengthUnit.CENTIMETERS));

    }
}
package quantity;

public class QuantityMeasurementApp {

    public static void main(String[] args) {

        demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCHES);
        demonstrateLengthConversion(3.0, LengthUnit.YARDS, LengthUnit.FEET);
        demonstrateLengthConversion(36.0, LengthUnit.INCHES, LengthUnit.YARDS);
        demonstrateLengthConversion(1.0, LengthUnit.CENTIMETERS, LengthUnit.INCHES);

        QuantityLength lengthInYards =
                new QuantityLength(2.0, LengthUnit.YARDS);

        demonstrateLengthConversion(lengthInYards, LengthUnit.INCHES);
    }

  
    public static void demonstrateLengthConversion(double value,
                                                   LengthUnit from,
                                                   LengthUnit to) {

        double result = QuantityLength.convert(value, from, to);
        System.out.println("convert(" + value + ", " + from + ", " + to + ") → " + result);
    }

 
    public static void demonstrateLengthConversion(QuantityLength length,
                                                   LengthUnit target) {

        QuantityLength converted = length.convertTo(target);
        System.out.println(length + " → " + converted);
    }
}
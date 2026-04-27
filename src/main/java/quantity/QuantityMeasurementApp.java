package quantity;

import quantity.Length.LengthUnit;

 public class QuantityMeasurementApp {
	
	
	public static boolean demonstrateLengthEquality(Length length1, Length length2) {
		System.out.println("Equal (" + length1.compare(length2) + ")");
		return length1.equals(length2);
	}
		
	public static boolean demonstrateLengthComparison(Length length1, Length length2) {
		System.out.println("Compare (" + length1.compare(length2) + ")");
		return length1.compare(length2);
	}
	
	public static Length demonstrateLengthConversion(double value, LengthUnit fromUnit, LengthUnit toUnit) {
		Length length = new Length(value, fromUnit);
		
		return length.convertTo(toUnit);
	}
	
	public static Length demonstrateLengthConversion(Length length, LengthUnit toUnit) {
		return length.convertTo(toUnit);
	}

	public static Length demonstrateLengthAddition(Length length1, Length length2){
		return length1.add(length2);
	}

	public static Length demonstrateLengthAddition(Length length1, Length length2, LengthUnit targetUnit){
		return length1.add(length2, targetUnit);
	}

	public static void main(String[] args) {
		System.out.println(demonstrateLengthAddition(new Length(1, LengthUnit.FEET), new Length(12, LengthUnit.INCHES), LengthUnit.FEET));

		System.out.println(demonstrateLengthAddition(new Length(1, LengthUnit.FEET), new Length(12, LengthUnit.INCHES), LengthUnit.INCHES));

		System.out.println(demonstrateLengthAddition(new Length(1, LengthUnit.FEET), new Length(12, LengthUnit.INCHES), LengthUnit.YARDS));

		System.out.println(demonstrateLengthAddition(new Length(1, LengthUnit.YARDS), new Length(3, LengthUnit.FEET), LengthUnit.YARDS));

		System.out.println(demonstrateLengthAddition(new Length(36, LengthUnit.INCHES), new Length(1, LengthUnit.YARDS), LengthUnit.FEET));

		System.out.println(demonstrateLengthAddition(new Length(2.54, LengthUnit.CENTIMETERS), new Length(1, LengthUnit.INCHES), LengthUnit.CENTIMETERS));

		System.out.println(demonstrateLengthAddition(new Length(5, LengthUnit.FEET), new Length(0, LengthUnit.INCHES), LengthUnit.YARDS));

		System.out.println(demonstrateLengthAddition(new Length(5, LengthUnit.FEET), new Length(-2, LengthUnit.FEET), LengthUnit.INCHES));
	}
}

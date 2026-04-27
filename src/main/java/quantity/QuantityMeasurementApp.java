package quantity;


import java.time.Year;

 public class QuantityMeasurementApp {

	 public static <U extends IMeasurable> boolean demonstrateEquality(Quantity<U> quantity1, Quantity<U> quantity2){
		 return quantity1.equals(quantity2);
	 }

	 public static <U extends IMeasurable> Quantity<U> demonstrateConversion(Quantity<U> quantity, U targetUnit){
		 return quantity.convertTo(targetUnit);
	 }

	 public static <U extends IMeasurable> Quantity<U> demonstrateAddition(Quantity<U> quantity1, Quantity<U> quantity2){
		 return quantity1.add(quantity2);
	 }

	 public static <U extends IMeasurable> Quantity<U> demonstrateAddition(Quantity<U> quantity1, Quantity<U> quantity2, U targetUnit){
		 return quantity1.add(quantity2, targetUnit);
	 }

	 public static <U extends IMeasurable> Quantity<U> demonstrateSubtraction(Quantity<U> quantity1, Quantity<U> quantity2){
		 return quantity1.subtract(quantity2);
	 }

	 public static <U extends IMeasurable> Quantity<U> demonstrateSubtraction(Quantity<U> quantity1, Quantity<U> quantity2, U targetUnit){
		 return quantity1.subtract(quantity2, targetUnit);
	 }

	public static void main(String[] args) {
	
		Quantity<WeightUnit> weightInGrams = new Quantity<>(1000.0, WeightUnit.GRAM);
		Quantity<WeightUnit> weightInKilograms = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		boolean areEqual = demonstrateEquality(weightInGrams, weightInKilograms);
		System.out.println("Are weights equal? " + areEqual);

		Quantity<WeightUnit> convertedWeight = demonstrateConversion(weightInGrams,
				WeightUnit.KILOGRAM);
		System.out.println("Converted Weight: " + convertedWeight.getValue() + " " +
				convertedWeight.getUnit());

		Quantity<WeightUnit> weightInPounds = new Quantity<>(2.20462, WeightUnit.POUND);
		Quantity<WeightUnit> sumWeight = demonstrateAddition(weightInKilograms, weightInPounds);
		System.out.println("Sum Weight: " + sumWeight.getValue() + " " +
				sumWeight.getUnit());

		Quantity<WeightUnit> sumWeightInGrams = demonstrateAddition(weightInKilograms,
				weightInPounds,
				WeightUnit.GRAM);
		System.out.println("Sum weight in Grams: " + sumWeightInGrams.getValue() + " " +
				sumWeightInGrams.getUnit());

		System.out.println("--------------------------------------------------");

		Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> v2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
		Quantity<VolumeUnit> v3 = new Quantity<>(1.0, VolumeUnit.GALLON);

		System.out.println(v1.equals(v2)); // true
		System.out.println(v3.convertTo(VolumeUnit.LITRE)); // 3.78541 L
		System.out.println(v1.add(v2)); // 2 L
		System.out.println(v1.add(v3, VolumeUnit.MILLILITRE)); // 4785.41 mL
	}

 }

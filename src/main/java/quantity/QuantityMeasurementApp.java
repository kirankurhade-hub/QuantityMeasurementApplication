package quantity;
public class QuantityMeasurementApp {

    public static boolean demonstrateLengthEquality1(Length l1, Length l2) {
        return l1.equals(l2);
    }
    public static boolean demonstrateLengthComparison1(double v1, LengthUnit u1,
                                                      double v2, LengthUnit u2) {
        return new Length(v1, u1).equals(new Length(v2, u2));
    }
    public static Length demonstrateLengthConversion1(double value,
                                                     LengthUnit from,
                                                     LengthUnit to) {
        return new Length(value, from).convertTo(to);
    }
    public static Length demonstrateLengthConversion1(Length length,
                                                     LengthUnit to) {
        return length.convertTo(to);
    }
    public static Length demonstrateLengthAddition1(Length l1, Length l2) {
        return l1.add(l2);
    }
    public static Length demonstrateLengthAddition1(Length l1,
                                                   Length l2,
                                                   LengthUnit targetUnit) {
        return l1.add(l2, targetUnit);
    }  public static boolean demonstrateLengthEquality(Length l1, Length l2) {
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


    public static boolean demonstrateWeightEquality(QuantityWeight w1, QuantityWeight w2) {
        return w1.equals(w2);
    }

    public static boolean demonstrateWeightComparison(double v1, WeightUnit u1,
                                                      double v2, WeightUnit u2) {
        return new QuantityWeight(v1, u1)
                .equals(new QuantityWeight(v2, u2));
    }

    public static QuantityWeight demonstrateWeightConversion(double value,
                                                             WeightUnit from,
                                                             WeightUnit to) {
        return new QuantityWeight(value, from).convertTo(to);
    }

    public static QuantityWeight demonstrateWeightConversion(QuantityWeight weight,
                                                             WeightUnit to) {
        return weight.convertTo(to);
    }

    public static QuantityWeight demonstrateWeightAddition(QuantityWeight w1,
                                                           QuantityWeight w2) {
        return w1.add(w2);
    }

    public static QuantityWeight demonstrateWeightAddition(QuantityWeight w1,
                                                           QuantityWeight w2,
                                                           WeightUnit targetUnit) {
        return w1.add(w2, targetUnit);
    }

 
    public static void main(String[] args) {

        QuantityWeight kg = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight gram = new QuantityWeight(1000.0, WeightUnit.GRAM);
        QuantityWeight pound = new QuantityWeight(2.20462, WeightUnit.POUND);

       
        System.out.println(demonstrateWeightEquality(kg, gram));   // true
        System.out.println(demonstrateWeightEquality(kg, pound));  // true

       
        System.out.println(demonstrateWeightConversion(kg, WeightUnit.GRAM));
        System.out.println(demonstrateWeightConversion(pound, WeightUnit.KILOGRAM));

      
        System.out.println(demonstrateWeightAddition(kg, gram));
        System.out.println(demonstrateWeightAddition(kg, gram, WeightUnit.GRAM));
    }
}
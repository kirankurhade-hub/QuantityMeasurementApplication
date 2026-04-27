package quantity;

public class Quantity<T extends IMeasurable> {
    private double value;
    private T unit;

    public Quantity(double value, T unit){
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Value must be finite");
        }
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        this.value = value;
        this.unit = unit;
    }

    public double getValue(){return value;}

    public T getUnit(){return unit;}

    public Quantity<T> convertTo(T targetUnit){
        if(targetUnit == null){
            throw new IllegalArgumentException("Unit cannot be null");
        }
        if(!targetUnit.getClass().equals(unit.getClass())){
            throw new IllegalArgumentException("Target unit should belong to same class");
        }

        double baseValue = unit.convertToBaseUnit(value);
        double convertValue = targetUnit.convertFromBaseUnit(baseValue);

        return new Quantity<T>(round(convertValue), targetUnit);
    }

    public Quantity<T> add(Quantity<T> other){
        return addAndConvert(other, unit);
    }
    public Quantity<T> add(Quantity<T> other, T targetUnit){
        return addAndConvert(other, targetUnit);
    }

    @Override
    public boolean equals(Object o){
        if(o == this){return true;}
        if(o == null || o.getClass() != this.getClass()){return false;}

        Quantity<?> other = (Quantity<?>) o;
        if(!this.unit.getClass().equals(other.unit.getClass())){return false;}

        return Double.compare(unit.convertToBaseUnit(value), other.unit.convertToBaseUnit(other.value))==0;

    }

    private Quantity<T> addAndConvert(Quantity<T> other, T targetUnit){
        if(targetUnit == null){
            throw new IllegalArgumentException("Unit cannot be null");
        }
        if (!this.unit.getClass().equals(other.unit.getClass())) {
            throw new IllegalArgumentException("Cannot add different measurement categories");
        }
        if(!targetUnit.getClass().equals(unit.getClass())){
            throw new IllegalArgumentException("Target unit should belong to same class");
        }
        double thisBaseValue = unit.convertToBaseUnit(value);
        double otherBaseValue = other.unit.convertToBaseUnit(other.value);

        double totalValue = targetUnit.convertFromBaseUnit(thisBaseValue+otherBaseValue);
        return new Quantity<>(round(totalValue), targetUnit);

    }

  
    private double round(double value){return (double) Math.round(value*100)/100;}

    public String toString(){
        return String.format("%.2f %s", value, unit);
    }

    public static void main(String[] args) {
       
        Quantity<LengthUnit> lengthInFeet = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> lengthInInches = new Quantity<>(120.0, LengthUnit.INCHES);
        boolean isEqual = lengthInFeet.equals(lengthInInches); // true
        System.out.println("Are lengths equal? " + isEqual);

        Quantity<WeightUnit> weightInKilograms = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> weightInGrams = new Quantity<>(1000.0, WeightUnit.GRAM);
        isEqual = weightInKilograms.equals(weightInGrams); // true
        System.out.println("Are weights equal? " + isEqual);

        double convertedLength = (lengthInFeet.convertTo(LengthUnit.INCHES)).getValue();
        System.out.println("10 feet in inches: " + convertedLength);

        Quantity<LengthUnit> totalLength = lengthInFeet.add(lengthInInches, LengthUnit.FEET);
        System.out.println("Total Length in feet: " + totalLength.getValue() + " " + totalLength.getUnit());

        Quantity<WeightUnit> weightInPounds = new Quantity<>(2.0, WeightUnit.POUND);
        Quantity<WeightUnit> totalWeight = weightInKilograms.add(weightInPounds, WeightUnit.KILOGRAM);
        System.out.println("Total Weight in kilograms: " + totalWeight.getValue() + " " + totalWeight.getUnit());
    }

}
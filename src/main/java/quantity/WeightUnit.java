package quantity;

public enum WeightUnit implements IMeasurable {
    MILLIGRAM(0.001),
    GRAM(1.0),
    KILOGRAM(1000.0),
    POUND(453.592),
    TONNE(1000000.0);

    private final double conversionValue;

    WeightUnit(double conversionValue){
        this.conversionValue = conversionValue;
    }
    @Override
    public double getConversionValue(){return conversionValue;}

    @Override
    public double convertToBaseUnit(double value){return value * this.conversionValue;}
    @Override
    public double convertFromBaseUnit(double baseValue){return baseValue/conversionValue;}
    @Override
    public String getUnitName(){return this.name();}
}

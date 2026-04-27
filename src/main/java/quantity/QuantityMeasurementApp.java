package quantity;

public class QuantityMeasurementApp {

    public static void main(String[] args) {
    	
    	QuantityLength l1 = new QuantityLength(1.0, LengthUnit.FEET);
    	QuantityLength l2 = new QuantityLength(12.0, LengthUnit.INCHES);
    	
    	System.out.println(l1.add(l2,LengthUnit.FEET));
    	System.out.println(l1.add(l2,LengthUnit.INCHES));
    	System.out.println(l1.add(l2,LengthUnit.YARDS));
    	
    	
    	QuantityLength l3 = new QuantityLength(2.0, LengthUnit.YARDS);
    	QuantityLength l4 = new QuantityLength(3.0, LengthUnit.FEET);
    	
    	System.out.println(l3.add(l4,LengthUnit.YARDS));
    	System.out.println(l3.add(l4,LengthUnit.FEET));
        
    }

}
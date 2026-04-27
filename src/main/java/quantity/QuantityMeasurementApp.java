package quantity;

public class QuantityMeasurementApp {

    public static void main(String[] args) {
    	
    	QuantityLength l1 = new QuantityLength(1.0, LengthUnit.FEET);
    	QuantityLength l2 = new QuantityLength(12.0, LengthUnit.INCHES);
    	System.out.println("Input: add("+l1+", "+l2+" )");
    	System.out.println("Output: "+l1.add(l2));
    	
    	QuantityLength l3 = new QuantityLength(1.0, LengthUnit.YARDS);
    	QuantityLength l4 = new QuantityLength(3.0, LengthUnit.FEET);
    	System.out.println("Input: add("+l3+", "+l4+" )");
    	System.out.println("Output: "+l3.add(l4));

    	QuantityLength l5 = new QuantityLength(2.54, LengthUnit.CENTIMETERS);
    	QuantityLength l6 = new QuantityLength(1.0, LengthUnit.INCHES);
    	
    	System.out.println("Input: add("+l5+", "+l6+" )");
    	System.out.println("Output: "+l5.add(l6));
        
    }

}
package Utils;

public class Utils {
    public static double getNettoValue(double bruttoValue){
        Integer result = Integer.valueOf((int) (bruttoValue / (1 +  0.23)*100)) ;
        return (double)result/100;
    }

    public static double trimDecimal (double value){
        Integer result = Integer.valueOf((int) value *100);
        return result/100;
    }
}

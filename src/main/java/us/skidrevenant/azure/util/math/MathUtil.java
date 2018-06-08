package us.skidrevenant.azure.util.math;

import java.util.Random;

/**
 * @author SkidRevenant
 * at 28/05/2018
 */
public class MathUtil {

    public static final Random RANDOM = new Random();

    //Computes the magnitude of X and Y
    public static double getMagnitude(double x, double y) {
        return Math.sqrt(x * x + y * y);
    }
}

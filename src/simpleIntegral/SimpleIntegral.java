package simpleIntegral;

import java.util.function.DoubleFunction;
import java.util.Random;

/**
 * Simplest example of integration by Monte Carlo method
 *
 * @author tadaki
 */
public class SimpleIntegral {

    /**
     * Numerical Integral by Monte Carlo method
     *
     * @param func integrant
     * @param from lower limit of integral
     * @param to upper limit of integral
     * @param max upper limit of integrant
     * @param n the number of random numbers
     * @return
     */
    public static double simpleIntegral(DoubleFunction<Double> func,
            double from, double to, double max, int n) {
        return simpleIntegral(func, from, to, max, n, new Random(48L));
    }

    public static double simpleIntegral(DoubleFunction<Double> func,
            double from, double to, double max, int n, Random random) {
        int count = 0;
        for (int i = 0; i < n; i++) {
            double x = (to - from) * random.nextDouble();
            double y = max * random.nextDouble();
            if (y < func.apply(x)) {
                count++;
            }
        }
        return (to - from) * max * count / n;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        double s = simpleIntegral(x -> Math.sin(x), 0, Math.PI, 1, 1000000);
        System.out.println(s);
    }

}

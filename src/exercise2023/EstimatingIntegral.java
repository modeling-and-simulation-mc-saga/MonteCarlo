package exercise2023;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.DoubleFunction;

/**
 *
 * @author tadaki
 */
public class EstimatingIntegral {

    private final DoubleFunction<Double> func;//integrand
    private final double ymax;
    private final double xmin;
    private final double xmax;
    private int count = 0;
    private int allCount = 0;
    private final Random myRandom;

    public EstimatingIntegral(DoubleFunction<Double> func, double ymax,
            double xmin, double xmax, Random myRandom) {
        this.func = func;
        this.ymax = ymax;
        this.xmin = xmin;
        this.xmax = xmax;
        this.myRandom = myRandom;
    }

    /**
     * add one random point and return estimated value
     *
     * @return
     */
    public double addOne() {
        allCount++;
        double x = myRandom.nextDouble() * (xmax - xmin) + xmin;
        double y = myRandom.nextDouble() * ymax;
        if (y < func.apply(x)) {
            count++;
        }
        return ((double) count / allCount) * ymax * (xmax - xmin);
    }

    public Map<String, List<Point2D.Double>> generateRandomSamples(int n) {
        Map<String, List<Point2D.Double>> map = 
                Collections.synchronizedMap(new HashMap<>());
        List<Point2D.Double> accept = new ArrayList<>();
        List<Point2D.Double> reject = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            double x = myRandom.nextDouble() * (xmax - xmin) + xmin;
            double y = myRandom.nextDouble() * ymax;
            if (y < func.apply(x)) {
                accept.add(new Point2D.Double(x, y));
            } else {
                reject.add(new Point2D.Double(x, y));
            }
        }
        map.put("accept", accept);
        map.put("reject", reject);
        return map;
    }

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        int n = 25;

        int numData[] = new int[n];
        for (int i = 0; i < n; i++) {
            numData[i] = (int) Math.pow(2., (double) i);
        }

        Random myRandom = new Random(32943L);
        double xmin = 0.;
        double xmax = Math.PI / 2.;
        double ymax = 1.;
        double expected = Math.PI / 4.;//expected value of integral
        //integrand
        DoubleFunction<Double> func = (x) -> Math.pow(Math.sin(x), 2.);
        EstimatingIntegral sys = new EstimatingIntegral(func,
                ymax, xmin, xmax, myRandom);
        int k = 0;
        int t = 0;
        String filename = EstimatingIntegral.class.getSimpleName() + "-output.txt";
        try ( PrintStream out = new PrintStream(filename)) {
            while (k < n) {
                while (true) {
                    t++;
                    double x = sys.addOne();
                    if (t == numData[k]) {
                        //discrepancy from the exact value
                        double r = Math.abs(x - expected);
                        out.println(t + " " + x + " " + r);
                        break;
                    }
                }
                k++;
            }
        }
        int nSample = 100;
        Map<String, List<Point2D.Double>> map = sys.generateRandomSamples(nSample);
        for (String s : map.keySet()) {
            filename = EstimatingIntegral.class.getSimpleName()
                    + "-" + s + "-output.txt";
            List<Point2D.Double> pList = map.get(s);
            try ( PrintStream out = new PrintStream(filename)) {
                pList.forEach(p -> out.println(p.x + " " + p.y));
            }
        }
    }

}

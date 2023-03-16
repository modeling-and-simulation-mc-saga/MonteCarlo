package findMinima;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.DoubleFunction;

/**
 * Finding minima numerically by changing starting points
 * 
 * @author tadaki
 */
public class FindMinima {

    private final DoubleFunction<Double> function;

    public FindMinima(DoubleFunction<Double> function) {
        this.function = function;
    }

    private List<Point2D.Double> find(double x, double step, double epsilon) {
        List<Point2D.Double> pList = 
                Collections.synchronizedList(new ArrayList<>());
        double v = function.apply(x);
        pList.add(new Point2D.Double(x, v));
        while (step > epsilon) {
            double vp = function.apply(x - step);
            double vf = function.apply(x + step);
            if ((vp - v) * (vf - v) > 0) {
                step *= .5;
            } else {
                if (vp < vf) {
                    v = vp;
                    x = x - step;
                } else {
                    v = vf;
                    x = x + step;
                }
                pList.add(new Point2D.Double(x, v));
            }
        }
        return pList;
    }

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        FindMinima sys = 
                new FindMinima(x -> Math.pow(x, 4.) - 50 * Math.pow(x, 2.));

        List<Point2D.Double> pList = sys.find(-10., .9, 0.0001);
        try ( PrintStream out = new PrintStream("findMinima1.txt")) {
            pList.forEach(p->out.println(p.x+" "+p.y));
        }
        List<Point2D.Double> pList2 = sys.find(1., .9, 0.0001);
        try ( PrintStream out = new PrintStream("findMinima2.txt")) {
            pList2.forEach(p->out.println(p.x+" "+p.y));
        }
    }

}

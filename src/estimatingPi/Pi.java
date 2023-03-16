package estimatingPi;

import java.io.PrintStream;
import java.io.IOException;
import java.util.Random;

/**
 * estimating pi by simulation
 *
 * @author tadaki
 */
public class Pi {

    private int in;
    private int all;
    private final Random myRandom;

    public Pi(Random myRandom) {
        in = 0;
        all = 0;
        this.myRandom = myRandom;
    }

    public double addOne() {

        
        
        
        
        
    
        return (double) in / all;
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
        Pi pi = new Pi(myRandom);
        int k = 0;
        int t = 0;
        String filename = Pi.class.getSimpleName() + "-output.txt";
        try ( PrintStream out = new PrintStream(filename)) {
            while (k < n) {
                while (true) {
                    t++;
                    double x = pi.addOne();
                    if (t == numData[k]) {
                        //discrepancy from the exact value
                        double r = Math.abs(x - Math.PI / 4);
                        out.println(t+" "+x+" "+r);
                        break;
                    }
                }
                k++;
            }
        }
    }

}

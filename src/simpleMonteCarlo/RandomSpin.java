package simpleMonteCarlo;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Random;

/**
 * simple random spin system
 *
 * @author tadaki
 */
public class RandomSpin extends SpinSystem {

    /**
     * @param n the number of spins
     * @param random
     */
    public RandomSpin(int n, Random random) {
        super(n, random);
    }

    /**
     * Generate random interactions
     */
    @Override
    protected void initializeJ() {
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double r = random.nextDouble() - 0.5;
                J[i][j] = r;
                J[j][i] = r;
            }
        }

    }

    /**
     * Monte Carlo simulation of random spin system
     *
     * @param args
     * @throws IOException
     */
    public static void main(String args[]) throws IOException {
        int n = 256;//the number of spins
        int tmax = 100;//Monte Carlo steps
        int numSample = 5;//the number of different initial configurations
        long seed = 32124L;
        RandomSpin sys = new RandomSpin(n, new Random(seed));

        for (int k = 0; k < numSample; k++) {
            String filename = RandomSpin.class.getSimpleName() + "-"
                    + String.valueOf(k) + ".txt";
            try ( PrintStream out = new PrintStream(filename)) {
                sys.initialize();
                out.println(String.valueOf(sys.getEnergy()));
                for (int t = 0; t < tmax; t++) {
                    for (int i = 0; i < n; i++) {
                        sys.oneStep();
                    }
                    out.println(String.valueOf(sys.getEnergy()));
                }
                out.println();
                out.println();
            }
        }
    }
}

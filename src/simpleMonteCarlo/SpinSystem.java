package simpleMonteCarlo;

import java.util.Random;

/**
 *
 * @author tadaki
 */
abstract public class SpinSystem {
    
    protected final int n; //the number of spins
    protected final int[] s; //spins
    protected final double[][] J; //interactions between spins
    protected double energy = 0.; //energy
    protected Random random;

    public SpinSystem(int n, Random random) {
        this.n = n;
        s = new int[n];
        J = new double[n][n];        
        this.random = random;
        initializeJ();
    }

    /**
     * Initial spin configuration
     */
    public void initialize() {
        for (int i = 0; i < n; i++) {
            s[i] = 2 * (int) (2 * random.nextDouble()) - 1;
        }
        evalEnergy();
    }
    
    abstract protected void initializeJ();

    /**
     * One step
     *
     * @return
     */
    public double oneStep() {
        int k = random.nextInt(n); //Random selection of a spin
        int ds = -2 * s[k]; //spin flip
        //energy
        double de = 0.;
        for (int j = 0; j < n; j++) {
            de += -2 * J[k][j] * s[j] * ds;
        }
        //the spin flips if energy decreases
        if (de < 0) {
            energy += de;
            s[k] += ds;
        }
        return de;
    }

    /**
     * Evaluating energy
     *
     * @return
     */
    public double evalEnergy() {
        energy = 0.;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                energy += J[i][j] * s[i] * s[j];
            }
        }
        return energy;
    }
    
    public double getEnergy() {
        return energy;
    }
    
}


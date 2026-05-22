package OWNNBANC;
 
import java.util.Random;
 
public class dinero {
 
    /**
     * Genera un saldo inicial aleatorio entre 1,000 y 99,999
     */
    public static double generarDinero() {
        Random rand = new Random();
        // Saldo entre 1000.00 y 99999.00
        double saldo = 1000 + (rand.nextInt(99000));
        return saldo;
    }
}
 
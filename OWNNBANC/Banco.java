package OWNNBANC;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


public class Banco {
    
    //Cuenta bancaria personal 34567
    
     
   
        private static Set<String> NCuenta = new HashSet<>();
        private String Cuenta;

        public static String generarCuenta() {
            Random rand = new Random();
            String numero;
    
            do {
                StringBuilder tarjeta = new StringBuilder("1-52-");
    
                for (int i = 0; i < 16; i++) {
                    tarjeta.append(rand.nextInt(10));
                }
    
                numero = tarjeta.toString();
    
            } while (NCuenta.contains(numero));
    
            NCuenta.add(numero);
            return numero;
            }
    public void Dinero(){
        
    }

}


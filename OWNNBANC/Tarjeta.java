package OWNNBANC;

    import java.util.HashSet;
    import java.util.Random;
    import java.util.Set;

public class Tarjeta {
    //Generar no. de tarjeta aleatorio 
   
        private static Set<String> tarjetasGeneradas = new HashSet<>();
        private String numeroTarjeta;

        public static String generarNumero() {
            Random rand = new Random();
            String numero;
    
            do {
                StringBuilder tarjeta = new StringBuilder("4052");
    
                for (int i = 0; i < 12; i++) {
                    tarjeta.append(rand.nextInt(10));
                }
    
                numero = tarjeta.toString();
    
            } while (tarjetasGeneradas.contains(numero));
    
            tarjetasGeneradas.add(numero);
            return numero;
        }
    }


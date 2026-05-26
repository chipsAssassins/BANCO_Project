package OWNNBANC;

public class excepcioness {
    String message = "Error: Usuario no encontrado.";
    String message2 = "Error: Saldo insuficiente.";
    String message3 = "Error: Transferencia fallida.";
/* 
    public static class correos_par {
        String[] Abecedario = {
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
            "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z",
        };
        String[] AbecedarioMayus ={ "A","B","C","D","E","F","G","H","I","J","K","L",
        "M","N","O","P","Q","R","S","T","U","V","X","Y","Z";
        }
    }
 */
    public static class CorreoInvalidoException extends RuntimeException {
    public CorreoInvalidoException(String message) {
        super(message);
    }
}
    public static class UsuarioNoEncontradoException extends RuntimeException {
        public UsuarioNoEncontradoException(String message) {
            super(message);
        }
    }

    public static class SaldoInsuficienteException extends RuntimeException {
        public SaldoInsuficienteException(String message) {
            super(message);
        }
    }

    public static class TransferenciaFallidaException extends RuntimeException {
        public TransferenciaFallidaException(String message) {
            super(message);
        }
    }
    /* 
    public static void validarCorreo(String correo) throws CorreoInvalidoException {
    // Verifica que contenga al menos una letra (a-z o A-Z)
    boolean tieneLetra = correo.matches(".*[a-zA-Z].*");
    
    if (!tieneLetra) {
        throw new CorreoInvalidoException("El correo debe contener al menos una letra.");
    }
*/
}


// lblError.setText("Correo o contraseña incorrectos.");
//            txtPass.setText("");
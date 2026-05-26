package OWNNBANC;

public class excepcioness {
    String message = "Error: Usuario no encontrado.";
    String message2 = "Error: Saldo insuficiente.";
    String message3 = "Error: Transferencia fallida.";

    public static class UsuarioNoEncontradoException extends Exception {
        public UsuarioNoEncontradoException(String message) {
            super(message);
        }
    }

    public static class SaldoInsuficienteException extends Exception {
        public SaldoInsuficienteException(String message2) {
            super(message2);
        }
    }

    public static class TransferenciaFallidaException extends Exception {
        public TransferenciaFallidaException(String message3) {
            super(message3);

        }
    }
}
// lblError.setText("Correo o contraseña incorrectos.");
//            txtPass.setText("");
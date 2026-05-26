package OWNNBANC;

public class Transferencias {

    /**
     * Realiza una transferencia de cuenta a cuenta.
     *
     * @param emisor       Usuario que envía el dinero (ya logueado)
     * @param cuentaDestino Número de cuenta del receptor
     * @param monto        Cantidad a transferir
    
     * @return true si la transferencia fue exitosa, false si falló
     */
    public static boolean transferir(Usuarios emisor, String cuentaDestino, double monto) {

        // ── 1. Validar monto positivo
        if (monto <= 0) {
            System.out.println(" El monto debe ser mayor a $0.00.");
            return false;
        }

        // ── 2. Verificar que no se transfiera a sí mismo 
        if (emisor.getNumCuenta().equals(cuentaDestino)) {
            System.out.println(" No puedes transferirte a tu propia cuenta.");
            return false;
        } 
        /* 
        if (emisor.getNumTarjeta().equals(tarjetaDestino)) {
            System.out.println("no puedes transferirte a tu propia tarjeta.");
        }
        */


        // ── 3. Verificar saldo suficiente 
        if (emisor.getDineroEnCuenta() < monto) {
            System.out.printf(" Saldo insuficiente. Saldo actual: %$.2f%n", emisor.getDineroEnCuenta());
            return false;
        }

        // ── 4. Buscar cuenta destino en el CSV 
        Usuarios receptor = csv1_Manager.buscarPorCuenta(cuentaDestino);
        if (receptor == null) {
            System.out.println(" Cuenta destino no encontrada: " + cuentaDestino);
            return false;
        }
        /* 
        Usuarios receptor = csv1_Manager.buscarPorTarjeta(tarjetaDestino);
        if (receptor == null) {
            System.out.println(" Cuenta destino no encontrada: " + tarjetaDestino);
            return false;
        }
        */
 
        // ── 5. Calcular nuevos saldos ─────────────────────────────────────
        double saldoEmisor   = emisor.getDineroEnCuenta() - monto;
        
        double saldoReceptor = receptor.getDineroEnCuenta() + monto;
 
        // ── 6. Persistir cambios en el CSV ────────────────────────────────
        csv1_Manager.actualizarSaldo(emisor.getEmail(), saldoEmisor);
        csv1_Manager.actualizarSaldo(receptor.getEmail(), saldoReceptor);
 
        // ── 7. Actualizar el objeto emisor en memoria ─────────────────────
        emisor.setDineroEnCuenta(saldoEmisor);
/* 
        // ── 8. Confirmación ───────────────────────────────────────────────
        System.out.println("");
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║        TRANSFERENCIA EXITOSA         ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.printf("  Monto enviado   : $%.2f%n", monto);
        System.out.printf("  Destinatario    : %s%n", receptor.getName());
        System.out.printf("  Cuenta destino  : %s%n", receptor.getNumCuenta());
        System.out.printf("  Tu nuevo saldo  : $%.2f%n", saldoEmisor);
        System.out.println("");
 */
        return true;
        
    }
        
}
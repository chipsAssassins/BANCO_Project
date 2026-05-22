package OWNNBANC;
 
import java.io.*;
import java.util.ArrayList;
import java.util.List;
 
public class csv1_Manager {
 
    private static final String FILE_PATH = "C:\\Users\\Angel Alberto\\Desktop\\BANCO_Project\\OWNNBANC\\usuarios.csv";
    private static final String ENCABEZADO = "id,nombre,email,password,NoTarjeta,NCuenta,dineroEnCuenta";
 
    // ─────────────────────────────────────────────
    //  OBTENER SIGUIENTE ID
    // ─────────────────────────────────────────────
    public static int obtenerSiguienteID(String ruta) {
        int maxID = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            br.readLine(); // saltar encabezado
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length > 0 && !datos[0].trim().isEmpty()) {
                    int id = Integer.parseInt(datos[0].trim());
                    if (id > maxID) maxID = id;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return maxID + 1;
    }
 
    // ─────────────────────────────────────────────
    //  GUARDAR USUARIO NUEVO
    // ─────────────────────────────────────────────
    public static void guardarUsuario(Usuarios user) {
        try {
            File file
             = new File(FILE_PATH);
            boolean existe = file.exists();
            FileWriter fw = new FileWriter(file, true);
 
            if (!existe) {
                fw.write(ENCABEZADO + "\n");
            }
 
            int nuevoID = obtenerSiguienteID(FILE_PATH);
            String linea = nuevoID + "," +
                        user.getName() + "," +
                        user.getEmail() + "," +
                        user.getPassword() + "," +
                        user.getNumTarjeta() + "," +
                        user.getNumCuenta() + "," +
                        user.getDineroEnCuenta() + "\n";
            fw.write(linea);
            fw.close();
            System.out.println("✔ Usuario guardado con ID: " + nuevoID);
 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //753008 contra AIKO
 
    // ─────────────────────────────────────────────
    //  LOGIN
    // ─────────────────────────────────────────────
    public static Usuarios login(String email, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            br.readLine(); // encabezado
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] d = linea.split(",");
                if (d.length < 7) continue;
 
                String correo = d[2].trim();
                String pass   = d[3].trim();
 
                if (correo.equals(email.trim()) && pass.equals(password.trim())) {
                    Usuarios u = new Usuarios(
                        d[1].trim(), correo, pass,
                        d[4].trim(), d[5].trim(),
                        Double.parseDouble(d[6].trim())
                    );
                    u.setId(Integer.parseInt(d[0].trim()));
                    return u;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo.");
        }
        return null;
    }
 
    // ─────────────────────────────────────────────
    //  BUSCAR USUARIO POR NÚMERO DE CUENTA
    // ─────────────────────────────────────────────
    public static Usuarios buscarPorCuenta(String numCuenta) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            br.readLine();
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] d = linea.split(",");
                if (d.length < 7) continue;
                if (d[5].trim().equals(numCuenta.trim())) {
                    Usuarios u = new Usuarios(
                        d[1].trim(), d[2].trim(), d[3].trim(),
                        d[4].trim(), d[5].trim(),
                        Double.parseDouble(d[6].trim())
                    );
                    u.setId(Integer.parseInt(d[0].trim()));
                    return u;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al buscar cuenta.");
        }
        return null;
    }
/* 
    public static Usuarios buscarPorTarjeta(String numTarjeta) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            br.readLine();
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] d = linea.split(",");
                if (d.length < 7) continue;
                if (d[5].trim().equals(numTarjeta.trim())) {
                    Usuarios u = new Usuarios(
                        d[1].trim(), d[2].trim(), d[3].trim(),
                        d[4].trim(), d[5].trim(),
                        Double.parseDouble(d[6].trim())
                    );
                    u.setId(Integer.parseInt(d[0].trim()));
                    return u;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al buscar Tarjeta.");
        }
        return null;
    }
 */
    // ─────────────────────────────────────────────
    //  ACTUALIZAR SALDO DE UN USUARIO (por email)
    // ─────────────────────────────────────────────
    public static void actualizarSaldo(String email, double nuevoSaldo) {
        List<String> lineas = new ArrayList<>();
 
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String linea = br.readLine();
            lineas.add(linea); // encabezado
 
            while ((linea = br.readLine()) != null) {
                String[] d = linea.split(",");
                if (d.length >= 7 && d[2].trim().equals(email.trim())) {
                    // Reemplazar columna de saldo
                    d[6] = String.valueOf(nuevoSaldo);
                    lineas.add(String.join(",", d));
                } else {
                    lineas.add(linea);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer para actualizar saldo.");
            return;
        }
 
        // Reescribir el archivo completo
        try (FileWriter fw = new FileWriter(FILE_PATH, false)) {
            for (String l : lineas) {
                fw.write(l + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error al escribir saldo actualizado.");
        }
    }
}
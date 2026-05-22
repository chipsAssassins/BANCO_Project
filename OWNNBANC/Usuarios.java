package OWNNBANC;
 
public class Usuarios {
    private String name;
    private String email;
    private String Password;
    private int id;
    private String numTarjeta;
    private String numCuenta;
    private double dineroEnCuenta;
 
    // Constructor principal
    public Usuarios(String name, String email, String Password,
                    String numTarjeta, String numCuenta, double dineroEnCuenta) {
        this.name = name;
        this.email = email;
        this.Password = Password;
        this.numTarjeta = numTarjeta;
        this.numCuenta = numCuenta;
        this.dineroEnCuenta = dineroEnCuenta;
    }
 
    // Getters
    public String getName()           { return name; }
    public String getEmail()          { return email; }
    public String getPassword()       { return Password; }
    public int    getId()             { return id; }
    public String getNumTarjeta()     { return numTarjeta; }
    public String getNumCuenta()      { return numCuenta; }
    public double getDineroEnCuenta() { return dineroEnCuenta; }
 
    // Setters
    public void setName(String name)                    { this.name = name; }
    public void setEmail(String email)                  { this.email = email; }
    public void setPassword(String Password)            { this.Password = Password; }
    public void setId(int id)                           { this.id = id; }
    public void setNumTarjeta(String numTarjeta)        { this.numTarjeta = numTarjeta; }
    public void setNumCuenta(String numCuenta)          { this.numCuenta = numCuenta; }
    public void setDineroEnCuenta(double dineroEnCuenta){ this.dineroEnCuenta = dineroEnCuenta; }
}
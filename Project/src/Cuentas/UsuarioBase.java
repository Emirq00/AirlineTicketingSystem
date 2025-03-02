package Cuentas;

public interface UsuarioBase {
    public void registrarUsuario(String nombre, int edad, String email, String password);
    public void modificarDatos(String nombre, int edad, String email, String password);
    public void iniciarSesion(String email, String password);
    public void cerrarSesion();
}

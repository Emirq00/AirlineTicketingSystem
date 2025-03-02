package Cuentas;

import java.io.Serializable;
import Pagos.MetodoPago;

import java.util.List;

/**
 * Clase abstracta que representa un usuario en el sistema.
 * Implementa la interfaz {@link Serializable} para permitir la serialización.
 * Esta clase sirve como base para definir diferentes tipos de usuarios, como clientes o administradores.
 */
public abstract class Usuario implements Serializable {
    /**
     * Nombre del usuario.
     */
    private String nombre;

    /**
     * Edad del usuario.
     */
    private int edad;

    /**
     * Contraseña del usuario.
     */
    private String password;

    /**
     * Correo electrónico del usuario.
     */
    private String email;

    /**
     * Historial de búsquedas realizadas por el usuario.
     */
    private List<String> historialBusqueda;

    /**
     * Lista de métodos de pago asociados al usuario.
     */
    private List<MetodoPago> metodosPagos;

    /**
     * Constructor por defecto.
     * Inicializa un usuario sin valores predefinidos.
     */
    public Usuario() {}

    /**
     * Constructor que inicializa un usuario con información básica.
     *
     * @param nombre   el nombre del usuario.
     * @param edad     la edad del usuario.
     * @param password la contraseña del usuario.
     * @param email    el correo electrónico del usuario.
     */
    public Usuario(String nombre, int edad, String password, String email) {
        this.nombre = nombre;
        this.edad = edad;
        this.password = password;
        this.email = email;
    }

    /**
     * Obtiene el nombre del usuario.
     *
     * @return el nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return la contraseña del usuario.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     *
     * @return el correo electrónico del usuario.
     */
    public String getEmail() {
        return email;
    }
}

package Cuentas;

import java.io.Serializable;

/**
 * Clase que representa un administrador en el sistema.
 * Extiende la clase {@link Usuario} e implementa la interfaz {@link Serializable} para permitir la serialización.
 * Un administrador tiene información básica como nombre, edad, contraseña y correo electrónico.
 */
public class Administrador extends Usuario implements Serializable {
    /**
     * Nombre del administrador.
     */
    private String nombre;

    /**
     * Edad del administrador.
     */
    private int edad;

    /**
     * Contraseña del administrador.
     */
    private String password;

    /**
     * Correo electrónico del administrador.
     */
    private String email;

    /**
     * Constructor que inicializa un administrador con la información básica.
     *
     * @param nombre   el nombre del administrador.
     * @param edad     la edad del administrador.
     * @param password la contraseña del administrador.
     * @param email    el correo electrónico del administrador.
     */
    public Administrador(String nombre, int edad, String password, String email) {
        this.nombre = nombre;
        this.edad = edad;
        this.password = password;
        this.email = email;
    }

    /**
     * Obtiene el nombre del administrador.
     *
     * @return el nombre del administrador.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene la contraseña del administrador.
     *
     * @return la contraseña del administrador.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Obtiene el correo electrónico del administrador.
     *
     * @return el correo electrónico del administrador.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el nombre del administrador.
     *
     * @param nombre el nuevo nombre del administrador.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}

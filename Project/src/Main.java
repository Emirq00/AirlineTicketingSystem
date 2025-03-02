import Cuentas.*;
import SistemaDeMenus.*;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * Clase principal del sistema Aeroviajes.
 * Gestiona el flujo principal del programa, incluyendo el inicio de sesión, registro de usuarios y validación de datos.
 */
public class Main {
    /**
     * Base de datos del sistema que contiene información de usuarios y administradores.
     */
    public static BaseDeDatos baseDeDatos = new BaseDeDatos();

    /**
     * Método principal que ejecuta el sistema de menú inicial.
     *
     * @param args argumentos de línea de comandos (no utilizados en este programa).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;
        do {
            try {
                System.out.println("Bienvenido a Aeroviajes");
                System.out.println("1. Iniciar Sesión");
                System.out.println("2. Registrarse");
                System.out.println("3. Salir");
                System.out.print("Seleccione una opción: ");
                if (scanner.hasNextInt()) {
                    opcion = scanner.nextInt();
                    scanner.nextLine();
                } else {
                    System.out.println("Entrada inválida, intente nuevamente");
                    scanner.nextLine();
                    continue;
                }
                switch (opcion) {
                    case 1:
                        System.out.println("Ingrese sus datos");
                        System.out.print("Correo: ");
                        String email = scanner.nextLine();
                        if (!validarEmail(email)) {
                            System.out.println("Dirección de email inválida. Dominios aceptados: (@gmail.com, @hotmail.com, @outlook.com)");
                            break;
                        }
                        System.out.print("Contraseña: ");
                        String password = scanner.nextLine();
                        iniciarSesion(email, password);
                        break;
                    case 2:
                        System.out.println("Ingrese los datos para registrarse");
                        System.out.print("Nombre: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Edad: ");
                        int edad = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Correo: ");
                        String nuevoEmail = scanner.nextLine();
                        if (!validarEmail(nuevoEmail)) {
                            System.out.println("Dirección de email inválida. Dominios aceptados: (@gmail.com, @hotmail.com, @outlook.com)");
                            break;
                        }
                        System.out.print("Contraseña: ");
                        String nuevoPassword = scanner.nextLine();
                        registrarUsuario(nombre, edad, nuevoEmail, nuevoPassword);
                        break;
                    case 3:
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Ingrese una opción correcta");
                }
            } catch (InputMismatchException e) {
                System.out.println("Lo sentimos, entrada inválida");
                scanner.nextLine();
            }
        } while (opcion != 3);
        scanner.close();
    }

    /**
     * Inicia sesión en el sistema con las credenciales proporcionadas.
     * Verifica si el usuario o administrador existe en la base de datos y dirige al menú correspondiente.
     *
     * @param email    el correo electrónico del usuario.
     * @param password la contraseña del usuario.
     */
    public static void iniciarSesion(String email, String password) {
        esperarAleatoriamente();
        if (baseDeDatos.validarUsuario(email, password) || baseDeDatos.validarAdministrador(email, password)) {
            String rol = baseDeDatos.obtenerRol(email);
            if ("Administrador".equals(rol)) {
                System.out.println("Inicio de sesión exitoso como Administrador");
                MenuPrincipalAdministrador.menuAdministrador();
            } else if ("Usuario".equals(rol)) {
                System.out.println("Inicio de sesión exitoso como Usuario");
                MenuPrincipalCliente.menuUsuario(baseDeDatos.obtenerUsuario(email));
            }
        } else {
            System.out.println("Datos incorrectos o email inválido, intente nuevamente");
        }
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * Valida la dirección de correo electrónico y verifica que no esté registrado previamente.
     *
     * @param nombre   el nombre del usuario.
     * @param edad     la edad del usuario.
     * @param email    el correo electrónico del usuario.
     * @param password la contraseña del usuario.
     */
    public static void registrarUsuario(String nombre, int edad, String email, String password) {
        esperarAleatoriamente();
        boolean exito = false;
        do {
            try {
                if (validarEmail(email) && baseDeDatos.obtenerUsuario(email) == null) {
                    Cliente nuevoUsuario = new Cliente(nombre, edad, password, email);
                    baseDeDatos.agregarUsuario(nuevoUsuario);
                    System.out.println("Usuario registrado exitosamente.");
                    exito = true;
                } else {
                    System.out.println("El usuario ya está registrado o el email es inválido.");
                    exito = true;
                }
            } catch (Exception e) {
                System.out.println("Ocurrió un error durante el registro: " + e.getMessage());
            } finally {
                if (!exito) {
                    System.out.println("Inténtelo nuevamente");
                }
            }
        } while (!exito);
    }

    /**
     * Valida si una dirección de correo electrónico pertenece a uno de los dominios aceptados.
     *
     * @param email el correo electrónico a validar.
     * @return true si el correo es válido, false en caso contrario.
     */
    public static boolean validarEmail(String email) {
        String[] dominiosValidos = {"@gmail.com", "@hotmail.com", "@outlook.com", "@aeroviajes.com"};
        for (String dominio : dominiosValidos) {
            if (email.endsWith(dominio)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Simula un retraso aleatorio para representar procesamiento.
     * Muestra un mensaje de "Procesando..." mientras se espera.
     */
    public static void esperarAleatoriamente() {
        Random random = new Random();
        System.out.println("Procesando...");
        int tiempoEspera = random.nextInt(2000);
        try {
            Thread.sleep(tiempoEspera);
        } catch (InterruptedException e) {
            System.out.println("Error durante la espera: " + e.getMessage());
        }
    }
}

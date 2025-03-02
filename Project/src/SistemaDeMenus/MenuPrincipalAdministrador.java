package SistemaDeMenus;

import java.util.Scanner;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import Cuentas.*;
import Organizacion.BaseDeDatosVuelos;
import Tickets.CreacionTickets.*;
import Tickets.FormatoTickets.*;

import static SistemaDeMenus.Menu.vuelosDisponibles;

public class MenuPrincipalAdministrador extends Menu {
    private static Scanner scanner = new Scanner(System.in);
    private static boolean incorrectEntry;
    private static BaseDeDatos baseDeDatos = new BaseDeDatos();
    private static BaseDeDatosVuelos baseDeDatosVuelos = new BaseDeDatosVuelos();

    public static void menuAdministrador() {
        CompraTicket.iniciarVuelosPrueba();
        consultarTodosLosVuelosArchivo();
        int decision = 0;
        do {
            System.out.println("\n======== Bienvenido a nuestra página, Administrador ========");
            System.out.println("1.- Mostrar vuelos disponibles");
            System.out.println("2.- Gestionar Usuarios");
            System.out.println("3.- Salir");
            do {
                System.out.print("Ingresa tu entrada: ");
                incorrectEntry = false;
                try {
                    decision = scanner.nextInt();
                    if (decision < 1 || decision > 3) {
                        System.out.println("* Ingrese una entrada válida");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("* Ingrese una entrada numérica");
                    scanner.nextLine();
                    incorrectEntry = true;
                }
            } while (incorrectEntry || decision < 1 || decision > 3);

            switch (decision) {
                case 1:
                    mostrarVuelos();
                    break;
                case 2:
                    gestionarUsuarios();
                    break;
                case 3:
                    System.out.println("Regresando...");
                    return;
                default:
                    System.out.println("* Ingrese una opción válida");
            }
        } while (decision != 3);

        System.out.println("Saliendo...");
        scanner.close();
    }

    public static void mostrarVuelos() {
        System.out.println("Mostrando vuelos disponibles...");
        for(int i: vuelosDisponibles.keySet()){
            System.out.println(i+".- "+vuelosDisponibles.get(i).mostrarInformacionVuelo());
        }
        int opcion = 0;
        do {
            try {
                System.out.println("¿Qué desea hacer?");
                System.out.println("1. Agregar Vuelo");
                System.out.println("2. Eliminar Vuelo");
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
                        agregarVuelo();
                        break;
                    case 2:
                        eliminarVuelo();
                        break;
                    case 3:
                        System.out.println("Saliendo del submenú...");
                        break;
                    default:
                        System.out.println("Ingrese una opción correcta");
                }
            } catch (Exception e) {
                System.out.println("Ocurrió un error: " + e.getMessage());
                scanner.nextLine();
            }
        } while (opcion != 3);
    }

    private static void agregarVuelo() {
        try {
            System.out.print("Ingrese el origen del vuelo: ");
            String origen = scanner.nextLine();
            System.out.print("Ingrese el destino del vuelo: ");
            String destino = scanner.nextLine();
            System.out.print("Ingrese la fecha y hora del vuelo (AAAA-MM-DDTHH:MM): ");
            LocalDateTime fechaHora = LocalDateTime.parse(scanner.nextLine());
            System.out.print("Ingrese el precio del vuelo: ");
            double precio = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Ingrese la duración del vuelo (en horas): ");
            int duracion = scanner.nextInt();
            scanner.nextLine();
            Vuelo nuevoVuelo = new VueloRedondo(origen, destino, fechaHora, precio, duracion);
            baseDeDatosVuelos.agregarVuelo(nuevoVuelo);
            System.out.println("Vuelo agregado exitosamente.");
        } catch (Exception e) {
            System.out.println("Ocurrió un error al agregar el vuelo: " + e.getMessage());
        }
    }

    private static void eliminarVuelo() {
        try {
            System.out.print("Ingrese el número del vuelo a eliminar: ");
            int numeroVuelo = scanner.nextInt();
            scanner.nextLine();
            if (baseDeDatosVuelos.obtenerVuelo(numeroVuelo) != null) {
                baseDeDatosVuelos.eliminarVuelo(numeroVuelo);
                System.out.println("Vuelo eliminado exitosamente.");
            } else {
                System.out.println("Número de vuelo no encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Ocurrió un error al eliminar el vuelo: " + e.getMessage());
        }
    }

    public static void gestionarUsuarios() {
        int opcion = 0;
        do {
            try {
                System.out.println("Usuarios registrados:");
                for (Cliente usuario : baseDeDatos.getBaseDeDatosUsuarios().values()) {
                    System.out.println(usuario.getEmail());
                }
                System.out.println("1. Eliminar Usuario");
                System.out.println("2. Regresar al Menú del Administrador");
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
                        System.out.print("Ingrese el correo del usuario a eliminar: ");
                        String email = scanner.nextLine();
                        eliminarUsuario(email);
                        
                        break;
                    case 2:
                        System.out.println("Regresando al menú del administrador...");
                        break;
                    default:
                        System.out.println("Ingrese una opción correcta");
                }
            } catch (InputMismatchException e) {
                System.out.println("Ocurrió un error: " + e.getMessage());
            }
        } while (opcion != 2);
    }

    public static void eliminarUsuario(String email) {
        if (baseDeDatos.obtenerUsuario(email) != null) {
            baseDeDatos.eliminarUsuario(email);
            System.out.println("Usuario eliminado exitosamente.");
        } else {
            System.out.println("Usuario no encontrado.");
        }
    }
}

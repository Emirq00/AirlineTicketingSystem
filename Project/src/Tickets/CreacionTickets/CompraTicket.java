package Tickets.CreacionTickets;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import Cuentas.Cliente;
import Pagos.*;
import Tickets.FormatoTickets.*;

public class CompraTicket{

    public static Scanner scanner = new Scanner(System.in);

    public static void iniciarVuelosPrueba() {
        ObjectOutputStream fileOut;
        
        Vuelo vuelo1 = new VueloRedondo("Mexico", "Japon", LocalDateTime.of(2024, 12, 23, 19, 30), 70000, 7);
        Vuelo vuelo2 = new VueloRedondo("Uruguay", "Argentina", LocalDateTime.of(2025, 2, 4, 13, 0), 56000, 10);
        Vuelo vuelo3 = new VueloSimple("México", "España", LocalDateTime.of(2024, 12, 2, 15, 30), 45000);
        Vuelo vuelo4 = new VueloSimple("Canada", "Estados Unidos", LocalDateTime.of(2024, 12, 29, 21, 45), 10000);
        Vuelo vuelo5 = new VueloSimple("India", "China", LocalDateTime.of(2024, 12, 30, 20, 0), 24000);
        
        try {
            fileOut = new ObjectOutputStream(new FileOutputStream("Vuelos"));
            fileOut.writeObject(vuelo1);
            fileOut.writeObject(vuelo2);
            fileOut.writeObject(vuelo3);
            fileOut.writeObject(vuelo4);
            fileOut.writeObject(vuelo5);
            fileOut.close();
            System.out.println("Vuelos guardados exitosamente.");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    

    /**
     * Método en el que vamos a generar todo el proceso de compra de un ticket, en el que se le pedirá al usuario que seleccione un vuelo
     * y en base al tipo de vuelo se reasignarán algunos atributos como el precio y la cantidad de asientos disponibles.
     *
     * @param vueloSeleccionado Vuelo seleccionado anteriormente por el usuario que se asignará al momento de la compra del ticket.
     */
    public static void comprarTicket(Vuelo vueloSeleccionado, Cliente cliente) {
        boolean entradaInvalida, indecision;
        Ticket ticket = null;
        do {
            entradaInvalida = false;
            indecision = false;

            vueloSeleccionado.mostrarAsientos();
            System.out.println("\n¿Qué tipo de ticket desea comprar?");
            System.out.println("1. Standard");
            System.out.println("2. Premium");
            System.out.println("3. VIP");
            System.out.println("4. Salir");
            int opcionTipoTicket = -1;
            do {
                try {
                    System.out.print("Ingrese su entrada: ");
                    opcionTipoTicket = scanner.nextInt();
                    scanner.nextLine(); // Consumir el salto de línea pendiente

                    switch (opcionTipoTicket) {
                        case 1 -> ticket = new StandardTicket(vueloSeleccionado);
                        case 2 -> ticket = new PremiumTicket(vueloSeleccionado);
                        case 3 -> ticket = new VipTicket(vueloSeleccionado);
                        case 4 -> {
                            System.out.println("Regresando...");
                            return;
                        }
                        default -> {
                            System.out.println(" *Opción no válida");
                            entradaInvalida = true;
                        }
                    }
                } catch (InputMismatchException e) {
                    System.out.println(" *Ingrese una entrada numérica");
                    scanner.nextLine();
                    entradaInvalida = true;
                }
            } while (entradaInvalida);

            String asientoSeleccionado = null;
            do {
                entradaInvalida = false;
                try {
                    System.out.print("Ingrese su asiento: ");
                    asientoSeleccionado = scanner.nextLine();
                    if (ticket.getVuelo().getAsientosDisponibles().containsKey(asientoSeleccionado)) {
                        int tipoAsiento = ticket.getVuelo().getAsientosDisponibles().get(asientoSeleccionado);
                        if ((tipoAsiento == 1 && ticket instanceof StandardTicket) ||
                                (tipoAsiento == 2 && ticket instanceof PremiumTicket) ||
                                (tipoAsiento == 3 && ticket instanceof VipTicket)) {
                            System.out.println("Asiento reservado exitosamente");
                            ticket.setAsiento(asientoSeleccionado);
                        } else {
                            System.out.println("Ingrese un asiento asociado al tipo de ticket seleccionado");
                            entradaInvalida = true;
                        }
                    } else {
                        System.out.println("Ingrese un asiento válido");
                        entradaInvalida = true;
                    }
                } catch (InputMismatchException e) {
                    System.out.println(" *Ingrese una entrada válida");
                    entradaInvalida = true;
                }
            } while (entradaInvalida);

            System.out.println("\nResumen de compra:");

            System.out.println(ticket.getVuelo().mostrarInformacionCompra(asientoSeleccionado, ticket.getTipoTicket()));
            System.out.println("¿Qué deseas hacer?");
            System.out.println("1. Proseguir al pago");
            System.out.println("2. Regresar a la selección de tickets");
            System.out.println("3. Regresar al menú principal");

            int opcionPostResumen = -1;
            do {
                entradaInvalida = false;
                try {
                    System.out.print("Ingrese su entrada: ");
                    opcionPostResumen = scanner.nextInt();
                    scanner.nextLine(); // Consumir el salto de línea pendiente

                    switch (opcionPostResumen) {
                        case 1 -> indecision = false;
                        case 2 -> indecision = true;
                        case 3 -> {
                            System.out.println("Regresando...");
                            return;
                        }
                        default -> {
                            System.out.println(" *Opción no válida");
                            entradaInvalida = true;
                        }
                    }
                } catch (InputMismatchException e) {
                    System.out.println(" *Ingrese una entrada numérica");
                    scanner.nextLine();
                    entradaInvalida = true;
                }
            } while (entradaInvalida);

        } while (indecision);

        // Obtener el costo real del ticket
        double costoVuelo = ticket.getPrecioVueloTicket();

        int opcionMetodoPago = -1;
        do {
            try {
                System.out.println("==== Seleccione el método de pago ====");
                System.out.println("1) Efectivo");
                System.out.println("2) Tarjeta de crédito o débito");
                System.out.println("3) Transferencia");
                System.out.println("4) Salir del menú de compra");
                System.out.print("Seleccione una opción: ");
                opcionMetodoPago = scanner.nextInt();
                scanner.nextLine();

                switch (opcionMetodoPago) {
                    case 1:
                        menuEfectivo(scanner, cliente, costoVuelo);
                        break;
                    case 2:
                        menuTarjeta(scanner, cliente, costoVuelo);
                        break;
                    case 3:
                        menuTransferencia(scanner, cliente, costoVuelo);
                        break;
                    case 4:
                        System.out.println("Saliendo del menú de compra");
                        break;
                    default:
                        System.out.println("Opción inválida");
                }
            } catch (InputMismatchException e) {
                System.out.println("Lo sentimos, solo se deben ingresar números");
                scanner.nextLine(); // Limpiar el buffer
            }
        } while (opcionMetodoPago != 4);

        // Guardar el ticket comprado
        try (ObjectOutputStream fileOut = new ObjectOutputStream(new FileOutputStream("src/Tickets/TicketsComprados/"+cliente.getNombre()))) {
            fileOut.writeObject(ticket);
        } catch (IOException e) {
            System.out.println("Error al guardar el ticket: " + e.getMessage());
        }

        System.out.println("¡Compra realizada con éxito!");
    }

     /**
     * Procesa el pago en efectivo.
     *
     * @param cin     {@link Scanner} para entrada del usuario.
     * @param cliente el cliente que realiza el pago.
     * @param monto   el monto a pagar.
     */
    public static void menuEfectivo(Scanner cin, Cliente cliente, double monto) {
        System.out.println("==== Pago en Efectivo ====");

        PagoEfectivo pagoEfectivo=null;

        for (MetodoPago metodo : cliente.getMetodosPagos()) {
            if (metodo instanceof PagoEfectivo) {
                pagoEfectivo = (PagoEfectivo) metodo;
                break;
            }
        }

        if (pagoEfectivo == null) {
            System.out.println("Lo sentimos, no cuenta con metodo de pago en efectivo");
        }
        else{

            InformacionPago info=new InformacionPago();
            info.setMonto(monto);

            // Realizar el pago
            pagoEfectivo.pagar(info);
        }

    }

    /**
     * Procesa el pago con tarjeta de crédito o débito.
     *
     * @param cin     {@link Scanner} para entrada del usuario.
     * @param cliente el cliente que realiza el pago.
     * @param monto   el monto a pagar.
     */
    public static void menuTarjeta(Scanner cin, Cliente cliente, double monto) {
        TarjetaCredito tarjeta = null;
        for (MetodoPago metodo : cliente.getMetodosPagos()) {
            if (metodo instanceof TarjetaCredito) {
                tarjeta = (TarjetaCredito) metodo;
                break;
            }
        }
        try{
            if(tarjeta!=null){
                System.out.println("==== Pago con Tarjeta ====");

                System.out.print("Número de tarjeta (8 dígitos): ");
                int numTarjeta = cin.nextInt();
                while (numTarjeta<10000000 || numTarjeta>99999999){
                    System.out.println("Debe tener 8 digitos el numero de tarjeta");
                    numTarjeta=cin.nextInt();
                }
                cin.nextLine();
                System.out.print("Ingrese los tres números posteriores de su tarjeta (CVV): ");
                int cvv = cin.nextInt();
                cin.nextLine(); // Consumir el salto de línea pendiente
                while (cvv < 100 || cvv > 999){
                    System.out.println("Debe tener 3 digitos el cvv");
                    cvv=cin.nextInt();
                }
                System.out.print("Nombre del propietario: ");
                String nombreTitular = cin.nextLine();
                System.out.print("Ingrese la fecha de expiración de la tarjeta (formato: dd-MM-yyyy): ");
                String fecha = cin.nextLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate fechaExpiracion = LocalDate.parse(fecha, formatter);

                InformacionPago infoPago = new InformacionPago();
                infoPago.setMonto(monto);
                infoPago.setNumeroTarjeta(numTarjeta);
                infoPago.setCvv(cvv);
                infoPago.setNombreTitular(nombreTitular);
                infoPago.setFecha(fechaExpiracion);
                tarjeta.pagar(infoPago);
            }
            else{
                System.out.println("Lo sentimos, no cuenta con metodo de pago usando Tarjeta de Credito");
            }
        }catch (InputMismatchException e){
            System.out.println("Lo sentimos, entrada inválida");
        }
        catch (DateTimeParseException e){
            System.out.println("Formato de fecha incorrecto");
        }
    }

    /**
     * Procesa el pago mediante transferencia bancaria.
     *
     * @param cin     {@link Scanner} para entrada del usuario.
     * @param cliente el cliente que realiza el pago.
     * @param monto   el monto a pagar.
     */
    public static void menuTransferencia(Scanner cin, Cliente cliente, double monto) {
        System.out.println("==== Pago por Transferencia ====");

        Transferencia transferencia = null;
        for (MetodoPago metodo : cliente.getMetodosPagos()) {
            if (metodo instanceof Transferencia) {
                transferencia = (Transferencia) metodo;
                break;
            }
        }

        if (transferencia == null) {
            System.out.println("Lo sentimos, no cuenta con metodo de pago por Transferencia");
        }
        else{
            InformacionPago info=new InformacionPago();
            System.out.println("Ingrese la cuenta asociada: ");
            int cuenta= cin.nextInt();
            info.setNumeroTarjeta(cuenta);
            info.setMonto(monto);
            transferencia.pagar(info);
        }

    }
}

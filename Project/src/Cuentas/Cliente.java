package Cuentas;

import Pagos.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Clase que representa un cliente en el sistema.
 * Extiende la clase {@link Usuario} e implementa las interfaces {@link Observer} y {@link Serializable}.
 * Permite gestionar los métodos de pago de un cliente y observar cambios en el estado de disponibilidad de servicios.
 */
public class Cliente extends Usuario implements Observer, Serializable {

    /**
     * Lista de métodos de pago registrados por el cliente.
     */
    private List<MetodoPago> metodosPagos;

    /**
     * Constructor por defecto.
     * Inicializa la lista de métodos de pago vacía.
     */
    public Cliente() {
        this.metodosPagos = new ArrayList<>();
    }

    /**
     * Constructor que inicializa un cliente con información personal y una lista vacía de métodos de pago.
     *
     * @param nombre   el nombre del cliente.
     * @param edad     la edad del cliente.
     * @param password la contraseña del cliente.
     * @param email    el correo electrónico del cliente.
     */
    public Cliente(String nombre, int edad, String password, String email) {
        super(nombre, edad, password, email);
        this.metodosPagos = new ArrayList<>();
    }

    /**
     * Obtiene la lista de métodos de pago registrados por el cliente.
     *
     * @return una lista de métodos de pago.
     */
    public List<MetodoPago> getMetodosPagos() {
        return metodosPagos;
    }

    /**
     * Permite al cliente visualizar las compras realizadas.
     * Actualmente, la implementación retorna un valor vacío.
     *
     * @return una cadena representando las compras realizadas.
     */
    public String verCompras() {
        // Implementación pendiente
        return "";
    }

    /**
     * Permite al cliente agregar un método de pago mediante un menú interactivo.
     * Los métodos disponibles son efectivo, transferencia bancaria y tarjeta de crédito.
     *
     * @param cin un objeto {@link Scanner} para la entrada de datos.
     */
    public void addMetodoPago(Scanner cin) {
        int opcion = -1;
        do {
            try {
                System.out.println("==== Menú de Registro de Métodos de Pago ====");
                System.out.println("1) Agregar efectivo");
                System.out.println("2) Agregar fondos a la cuenta");
                System.out.println("3) Agregar Tarjeta de Crédito");
                System.out.println("4) Salir");
                System.out.print("Seleccione una opción: ");
                opcion = cin.nextInt();

                switch (opcion) {
                    case 1:
                        agregarPagoEfectivo(cin);
                        break;

                    case 2:
                        agregarTransferencia(cin);
                        break;

                    case 3:
                        agregarTarjetaCredito(cin);
                        break;

                    case 4:
                        System.out.println("Saliendo del menú...");
                        break;

                    default:
                        System.out.println("Opción inválida. Por favor, seleccione una opción del 1 al 4.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Solo se esperan valores numéricos.");
                cin.nextLine();
            }
        } while (opcion != 4);
    }

    /**
     * Permite visualizar todos los métodos de pago registrados por el cliente.
     * Si no hay métodos de pago registrados, se informa al usuario.
     */
    public void verMetodosPago() {
        if (getMetodosPagos() == null || getMetodosPagos().isEmpty()) {
            System.out.println("Lo sentimos, no cuenta con ningún método de pago registrado.");
        } else {
            for(MetodoPago x:getMetodosPagos()){
                System.out.println("-----------------------");
                System.out.println("Nombre del propietario: "+getNombre());
                System.out.println("Tipo: "+x.getTipo());
                System.out.println("Detalles: "+x.getDetalles());
                if(x.getClass().getName().equals("Pagos.TarjetaCredito")){
                    System.out.println("Cvv: "+((TarjetaCredito) x).getCvv());
                }
                if(x.getClass().getName().equals("Pagos.TarjetaCredito")){
                    System.out.println("Numero de tarjeta: "+((TarjetaCredito) x).getNumeroTarjeta());
                }
                if(x.getClass().getName().equals("Pagos.TarjetaCredito")){
                    System.out.println("Fecha de expiración: "+((TarjetaCredito) x).getFechaExpiracion());
                }
                if(x.getClass().getName().equals("Pagos.TarjetaCredito")){
                    System.out.println("Limite de crédito: "+((TarjetaCredito) x).getLimiteCredito());
                }
                if(x.getClass().getName().equals("Pagos.PagoEfectivo")){
                    System.out.println("Cash: "+((PagoEfectivo) x).getCashDisponible());
                }
                if(x.getClass().getName().equals("Pagos.Transferencia")){
                    System.out.println("Fondos: "+((Transferencia) x).getMonto());
                }
                if(x.getClass().getName().equals("Pagos.Transferencia")){
                    System.out.println("Cuenta asociado: "+((Transferencia) x).getCuenta());
                }
                System.out.println("-----------------------");
            }
        }
    }

    /**
     * Agrega un método de pago en efectivo al cliente.
     * Si ya existe uno, simplemente agrega más fondos.
     *
     * @param cin un objeto {@link Scanner} para la entrada de datos.
     */
    private void agregarPagoEfectivo(Scanner cin) {
        PagoEfectivo cash = null;
        boolean existeMetodo = false;
        for (MetodoPago x : getMetodosPagos()) {
            if (x instanceof PagoEfectivo) {
                cash = (PagoEfectivo) x;
                existeMetodo = true;
                break;
            }
        }

        if (!existeMetodo) {
            cash = new PagoEfectivo();
            getMetodosPagos().add(cash);
            System.out.println("Método de pago en efectivo agregado exitosamente.");
        }
        try {
            System.out.print("Indique la cantidad de efectivo a ingresar: ");
            double monto = cin.nextDouble();
            if (monto < 0) {
                System.out.println("No puede ingresar cantidades negativas.");
            } else {
                cash.agregarCash(monto);
                System.out.println("Monto ingresado exitosamente.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida, se esperan valores numéricos.");
        }
    }

    /**
     * Agrega un método de pago por transferencia al cliente.
     * Si ya existe uno, simplemente agrega más fondos.
     *
     * @param cin un objeto {@link Scanner} para la entrada de datos.
     */
    private void agregarTransferencia(Scanner cin) {
        Transferencia transferencia = null;
        boolean existeMetodo = false;
        for (MetodoPago x : getMetodosPagos()) {
            if (x instanceof Transferencia) {
                transferencia = (Transferencia) x;
                existeMetodo = true;
                break;
            }
        }
        if (!existeMetodo) {
            transferencia = new Transferencia();
            getMetodosPagos().add(transferencia);
            System.out.println("Método de pago por transferencia agregado exitosamente.");
        }
        System.out.print("Indique el monto a ingresar: ");
        try {
            double cantidad = cin.nextDouble();
            if (cantidad < 0) {
                System.out.println("No se pueden ingresar montos negativos.");
            } else {
                transferencia.agregarFondos(cantidad);
                System.out.println("Operación exitosa.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida, se esperan valores numéricos.");
        }
    }

    /**
     * Agrega una tarjeta de crédito al cliente.
     * Si ya existe una tarjeta registrada, no se permite agregar otra.
     *
     * @param cin un objeto {@link Scanner} para la entrada de datos.
     */
    private void agregarTarjetaCredito(Scanner cin) {
        boolean existeMetodo = false;
        for (MetodoPago x : getMetodosPagos()) {
            if (x instanceof TarjetaCredito) {
                System.out.println("Lo sentimos, ya cuenta con una tarjeta de crédito registrada.");
                existeMetodo = true;
                break;
            }
        }
        if (!existeMetodo) {
            TarjetaCredito tarjeta = new TarjetaCredito(getNombre());
            getMetodosPagos().add(tarjeta);
            System.out.println("Tarjeta de crédito agregada exitosamente.");
        }
    }

    /**
     * Método de la interfaz {@link Observer} que notifica al cliente sobre cambios en la disponibilidad de asientos.
     *
     * @param mensaje mensaje si un asiento está disponible.
     */
    @Override
    public void actualizar(String mensaje) {
        System.out.println("Hola, " + getNombre() + ". " + mensaje);
    }
}

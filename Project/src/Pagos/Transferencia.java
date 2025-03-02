package Pagos;

import Cuentas.Cliente;
import Cuentas.Usuario;

import java.util.HashSet;
import java.util.Random;

/**
 * Clase que representa un método de pago mediante transferencia bancaria.
 * Extiende la clase abstracta {@link MetodoPago} y define la lógica específica para manejar transferencias entre cuentas.
 */
public class Transferencia extends MetodoPago {
    /**
     * Número único de cuenta asociado a la transferencia.
     */
    private int cuenta;

    /**
     * Monto disponible en la cuenta para realizar transferencias.
     */
    private double montDisponible;

    /**
     * Estado actual de la transferencia (por ejemplo, "Pendiente", "Completada").
     */
    private String estado;

    /**
     * Conjunto estático para garantizar la unicidad de los números de cuenta generados.
     */
    private static final HashSet<Integer> listaTarjetas = new HashSet<>();

    /**
     * Generador aleatorio para asignar números únicos a las cuentas.
     */
    private static final Random random = new Random();

    /**
     * Constructor que inicializa una transferencia con datos predeterminados.
     * La cuenta se genera automáticamente y el estado inicial es "Pendiente".
     */
    public Transferencia() {
        super("Transferencia Bancaria", "Pago mediante transferencia entre cuentas");
        this.montDisponible = 0.0;
        this.cuenta = generarNumeroUnico();
        this.estado = "Pendiente";
    }

    /**
     * Genera un número único para identificar la cuenta asociada a la transferencia.
     *
     * @return un número único de cuenta.
     */
    private int generarNumeroUnico() {
        int numero;
        do {
            numero = random.nextInt((99999999 - 10000000) + 1) + 10000000;
        } while (!listaTarjetas.add(numero));
        return numero;
    }

    /**
     * Realiza una transferencia bancaria utilizando la información proporcionada.
     * Verifica si los fondos disponibles son suficientes y si los datos de la cuenta coinciden.
     * Si la transferencia es exitosa, actualiza el estado y descuenta los fondos de la cuenta origen.
     *
     * @param informacionPago información detallada del pago a realizar.
     */
    @Override
    public void pagar(InformacionPago informacionPago) {
        if (!validarMetodoPago(this.montDisponible, informacionPago)) {
            System.out.println("La transferencia no pudo completarse. Verifique los datos.");
            return;
        }

        // Simular el descuento de fondos de la cuenta origen
        this.montDisponible -= informacionPago.getMonto();
        this.estado = "Completada";

        System.out.println("Transferencia completada exitosamente. Monto de la transferencia: " + informacionPago.getMonto());
        System.out.println("Fondos restantes: " + montDisponible);
    }

    /**
     * Valida si el método de pago es adecuado para realizar la transferencia.
     * Comprueba que el número de cuenta coincide y que hay fondos suficientes en la cuenta origen.
     *
     * @param montDisponible  monto disponible en la cuenta origen.
     * @param informacionPago información del pago a validar.
     * @return true si el método de pago es válido, false en caso contrario.
     */
    @Override
    public boolean validarMetodoPago(double montDisponible, InformacionPago informacionPago) {
        if (montDisponible <= 0) {
            System.out.println("El monto de la transferencia debe ser mayor a cero.");
            return false;
        }
        if (montDisponible < informacionPago.getMonto()) {
            System.out.println("Fondos insuficientes en la cuenta de origen.");
            return false;
        }
        if (cuenta != informacionPago.getNumeroTarjeta()) {
            System.out.println("El número de cuenta no coincide.");
            return false;
        }
        return true;
    }

    /**
     * Agrega fondos a la cuenta origen.
     *
     * @param monto el monto a agregar.
     */
    public void agregarFondos(double monto) {
        montDisponible += monto;
    }

    /**
     * Obtiene el monto disponible en la cuenta para realizar transferencias.
     *
     * @return el monto disponible.
     */
    public double getMonto() {
        return montDisponible;
    }

    /**
     * Obtiene el número único de la cuenta asociada a la transferencia.
     *
     * @return el número de la cuenta.
     */
    public int getCuenta() {
        return cuenta;
    }
}

package Pagos;

import Cuentas.Cliente;
import Cuentas.Usuario;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Random;

/**
 * Clase que representa un método de pago mediante tarjeta de crédito.
 * Extiende la clase abstracta {@link MetodoPago} y define la lógica específica para manejar transacciones con tarjetas de crédito.
 */
public class TarjetaCredito extends MetodoPago {
    /**
     * Número único de la tarjeta de crédito.
     */
    private int numeroTarjeta;

    /**
     * Fecha de expiración de la tarjeta.
     */
    private LocalDate fechaExpiracion;

    /**
     * Código de seguridad (CVV) de la tarjeta.
     */
    private int cvv;

    /**
     * Límite de crédito disponible en la tarjeta.
     */
    private double limiteCredito;

    /**
     * Generador aleatorio para asignar números y CVVs únicos.
     */
    private static final Random random = new Random();

    /**
     * Nombre del titular de la tarjeta.
     */
    private String nombreTitular;

    /**
     * Conjunto estático para garantizar la unicidad de los números de tarjeta generados.
     */
    private static final HashSet<Integer> listaTarjetas = new HashSet<>();

    /**
     * Constructor que inicializa una tarjeta de crédito con datos predeterminados para pruebas.
     *
     * @param nombreTitular el nombre del titular de la tarjeta.
     */
    public TarjetaCredito(String nombreTitular) {
        super("Tarjeta de Crédito", "Pago con tarjeta bancaria");
        this.numeroTarjeta = generarNumeroTarjetaUnico();
        this.cvv = random.nextInt(100, 999);
        this.limiteCredito = 50000; // Límite inicial estándar
        this.fechaExpiracion = LocalDate.of(2028, 11, 16);
        this.nombreTitular = nombreTitular;
    }

    /**
     * Genera un número de tarjeta único y lo registra en la lista de tarjetas.
     *
     * @return un número único de tarjeta.
     */
    private int generarNumeroTarjetaUnico() {
        int numero;
        do {
            numero = random.nextInt((99999999 - 10000000) + 1) + 10000000;
        } while (!listaTarjetas.add(numero));
        return numero;
    }

    /**
     * Realiza un pago utilizando la tarjeta de crédito.
     * Verifica si el límite de crédito es suficiente antes de realizar la transacción.
     *
     * @param informacionPago información detallada del pago a realizar.
     */
    @Override
    public void pagar(InformacionPago informacionPago) {
        if (validarMetodoPago(this.limiteCredito, informacionPago)) {
            this.limiteCredito -= informacionPago.getMonto();
            System.out.println("Pago realizado exitosamente por un monto de: " + informacionPago.getMonto());
        } else {
            System.out.println("Proceso de pago rechazado. Intente nuevamente.");
        }
    }

    /**
     * Valida si el método de pago es adecuado para cubrir el costo de la transacción.
     * Comprueba que el número de tarjeta, CVV y nombre del titular coincidan, y que el límite de crédito sea suficiente.
     *
     * @param costo           el costo de la transacción.
     * @param informacionPago información del pago a validar.
     * @return true si el método de pago es válido, false en caso contrario.
     * @throws IllegalArgumentException si el número de tarjeta o el CVV no son válidos.
     */
    @Override
    public boolean validarMetodoPago(double costo, InformacionPago informacionPago) {
        if (String.valueOf(informacionPago.getNumeroTarjeta()).length() != 8) {
            throw new IllegalArgumentException("Número de tarjeta inválido.");
        }
        if (String.valueOf(informacionPago.getCvv()).length() != 3) {
            throw new IllegalArgumentException("CVV inválido.");
        }
        if (this.limiteCredito < costo) {
            System.out.println("Fondos insuficientes.");
            return false;
        }
        return this.numeroTarjeta == informacionPago.getNumeroTarjeta()
                && this.cvv == informacionPago.getCvv()
                && Objects.equals(this.nombreTitular, informacionPago.getNombreTitular())
                && this.fechaExpiracion.equals(informacionPago.getFecha());
    }

    /**
     * Obtiene el número único de la tarjeta de crédito.
     *
     * @return el número de la tarjeta.
     */
    public int getNumeroTarjeta() {
        return numeroTarjeta;
    }

    /**
     * Obtiene la fecha de expiración de la tarjeta.
     *
     * @return la fecha de expiración.
     */
    public LocalDate getFechaExpiracion() {
        return fechaExpiracion;
    }

    /**
     * Obtiene el código de seguridad (CVV) de la tarjeta.
     *
     * @return el CVV de la tarjeta.
     */
    public int getCvv() {
        return cvv;
    }

    /**
     * Obtiene el límite de crédito disponible en la tarjeta.
     *
     * @return el límite de crédito disponible.
     */
    public double getLimiteCredito() {
        return limiteCredito;
    }
}

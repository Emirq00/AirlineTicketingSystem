package Pagos;

import java.time.LocalDate;

/**
 * Clase que representa la información de un pago.
 * Contiene los datos relacionados con una transacción, como el número de tarjeta, CVV,
 * nombre del titular, monto y fecha del pago.
 */
public class InformacionPago {
    /**
     * Número de la tarjeta de crédito o débito asociada al pago.
     */
    private int numeroTarjeta;

    /**
     * Código de seguridad (CVV) de la tarjeta.
     */
    private int cvv;

    /**
     * Nombre del titular de la tarjeta.
     */
    private String nombreTitular;

    /**
     * Monto del pago en formato decimal.
     */
    private double monto;

    /**
     * Fecha en que se realiza el pago.
     */
    private LocalDate fecha;

    /**
     * Constructor por defecto.
     * Inicializa una instancia de la clase sin valores predeterminados.
     */
    public InformacionPago() {}

    /**
     * Obtiene el número de la tarjeta asociada al pago.
     *
     * @return el número de la tarjeta.
     */
    public int getNumeroTarjeta() {
        return numeroTarjeta;
    }

    /**
     * Establece el número de la tarjeta asociada al pago.
     *
     * @param numeroTarjeta el número de la tarjeta.
     */
    public void setNumeroTarjeta(int numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
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
     * Establece el código de seguridad (CVV) de la tarjeta.
     *
     * @param cvv el CVV de la tarjeta.
     */
    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    /**
     * Obtiene el nombre del titular de la tarjeta.
     *
     * @return el nombre del titular de la tarjeta.
     */
    public String getNombreTitular() {
        return nombreTitular;
    }

    /**
     * Establece el nombre del titular de la tarjeta.
     *
     * @param nombreTitular el nombre del titular de la tarjeta.
     */
    public void setNombreTitular(String nombreTitular) {
        this.nombreTitular = nombreTitular;
    }

    /**
     * Obtiene el monto del pago.
     *
     * @return el monto del pago.
     */
    public double getMonto() {
        return monto;
    }

    /**
     * Establece el monto del pago.
     *
     * @param monto el monto del pago.
     */
    public void setMonto(double monto) {
        this.monto = monto;
    }

    /**
     * Obtiene la fecha en que se realiza el pago.
     *
     * @return la fecha del pago.
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha en que se realiza el pago.
     *
     * @param fecha la fecha del pago.
     */
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}

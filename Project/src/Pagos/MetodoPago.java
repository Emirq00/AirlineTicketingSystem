package Pagos;

/**
 * Clase abstracta que representa un método de pago.
 * Sirve como base para definir diferentes tipos de métodos de pago, como tarjetas de crédito,
 * débito, pagos electrónicos, entre otros.
 */
public abstract class MetodoPago {
    /**
     * Tipo del método de pago (por ejemplo, "Tarjeta de crédito", "Efectivo").
     */
    private String tipo;

    /**
     * Detalles adicionales sobre el método de pago.
     */
    private String detalles;

    /**
     * Constructor de la clase MetodoPago.
     *
     * @param tipo    El tipo del método de pago.
     * @param detalles Detalles adicionales del método de pago.
     */
    public MetodoPago(String tipo, String detalles) {
        this.tipo = tipo;
        this.detalles = detalles;
    }

    /**
     * Obtiene el tipo del método de pago.
     *
     * @return el tipo del método de pago.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo del método de pago.
     *
     * @param tipo el tipo del método de pago.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene los detalles adicionales del método de pago.
     *
     * @return los detalles del método de pago.
     */
    public String getDetalles() {
        return detalles;
    }

    /**
     * Realiza el pago utilizando la información proporcionada.
     * Este método debe ser implementado por las clases que hereden de MetodoPago.
     *
     * @param informacionPago información detallada del pago a realizar.
     */
    public abstract void pagar(InformacionPago informacionPago);

    /**
     * Valida si el método de pago es adecuado para cubrir el costo de una transacción.
     * Este método debe ser implementado por las clases que hereden de MetodoPago.
     *
     * @param costo            el costo de la transacción.
     * @param informacionPago  información del pago a validar.
     * @return true si el método de pago es válido, false en caso contrario.
     */
    public abstract boolean validarMetodoPago(double costo, InformacionPago informacionPago);
}

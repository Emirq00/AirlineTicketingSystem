package Pagos;

/**
 * Clase que representa un método de pago realizado en efectivo.
 * Extiende la clase abstracta {@link MetodoPago} y define la lógica específica para manejar pagos en efectivo.
 */
public class PagoEfectivo extends MetodoPago {
    /**
     * Cantidad disponible en efectivo para realizar el pago.
     */
    private double cantidad;

    /**
     * Constructor por defecto.
     * Inicializa un método de pago en efectivo con una cantidad disponible de 0.0.
     */
    public PagoEfectivo() {
        super("Pago en Efectivo", "Pago realizado en efectivo");
        this.cantidad = 0.0;
    }

    /**
     * Realiza el pago en efectivo utilizando la información proporcionada.
     * Verifica si el efectivo disponible es suficiente para cubrir el monto de la transacción.
     * Si el pago se realiza con éxito, actualiza la cantidad disponible y muestra el cambio entregado.
     *
     * @param informacionPago información detallada del pago a realizar.
     */
    @Override
    public void pagar(InformacionPago informacionPago) {
        if (!validarMetodoPago(this.cantidad, informacionPago)) {
            System.out.println("El pago en efectivo no pudo completarse. El monto es insuficiente.");
            return;
        }
        double montoCobrado = informacionPago.getMonto();
        cantidad -= montoCobrado;
        System.out.println("Pago en efectivo completado exitosamente. Monto del pago: " + informacionPago.getMonto());
        System.out.println("Cambio entregado: " + this.cantidad);
    }

    /**
     * Valida si el efectivo disponible es suficiente para cubrir el costo de la transacción.
     *
     * @param disponible      cantidad de efectivo disponible.
     * @param informacionPago información del pago a validar.
     * @return true si el efectivo disponible es suficiente, false en caso contrario.
     */
    @Override
    public boolean validarMetodoPago(double disponible, InformacionPago informacionPago) {
        double montoCosto = informacionPago.getMonto();
        if (disponible < montoCosto) {
            System.out.println("El efectivo entregado es insuficiente para cubrir el costo.");
            return false;
        }
        return true;
    }

    /**
     * Obtiene la cantidad disponible en efectivo para realizar pagos.
     *
     * @return la cantidad de efectivo disponible.
     */
    public double getCashDisponible() {
        return cantidad;
    }


    /**
     * Agrega más efectivo a la cantidad disponible.
     *
     * @param monto el monto a agregar.
     */
    public void agregarCash(double monto) {
        this.cantidad += monto;
    }
}

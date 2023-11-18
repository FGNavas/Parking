import java.util.Random;

/**
 * La clase Coche representa un hilo que simula el comportamiento de un coche
 * que entra y sale de un parking de forma repetida
 */
public class Coche extends Thread {

    /**
     * Número único que identifica al coche.
     */
    private int numeroCoche;

    /**
     * Instancia del parking al que el coche tiene acceso.
     */
    private Parking parking;

    /**
     * Constructor que inicializa un nuevo objeto Coche con un número único
     * y una referencia al parking al que tiene acceso.
     *
     * @param numeroCoche Número único que identifica al coche.
     * @param parking     Instancia del parking al que el coche tiene acceso.
     */
    public Coche(int numeroCoche, Parking parking) {
        this.numeroCoche = numeroCoche;
        this.parking = parking;
    }

    /**
     * Método que se ejecuta cuando se inicia el hilo. Simula el comportamiento
     * de un coche que entra y sale del parking en un bucle infinito.
     */
    @Override
    public void run() {
        while (true) {
            /* El programa inicia con todos los vehiculos queriendo entrar si se quiere una entrada aleatoria solo
            hay que cambiar el espera() de despues de salir a la posicion antes de entrar */
            espera();
            parking.entrar(numeroCoche, this);
            espera();
            parking.salir(numeroCoche, this);
            // espera();

        }
    }

    /**
     * Obtiene el número único que identifica al coche.
     *
     * @return Número único del coche.
     */
    public int getNumeroCoche() {
        return numeroCoche;
    }

    /**
     * Método que simula una espera aleatoria para representar el tiempo que
     * el coche permanece estacionado o fuera del parking.
     */
    public void espera() {
        try {
            //Se puede establecer el maximo tiempo que se desee o establecer un valor fijo
            Thread.sleep(new Random().nextInt(10000));
            // Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

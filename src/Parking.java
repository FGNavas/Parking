import java.util.*;


/**
 * La clase Parking representa un estacionamiento con capacidad establecida en el MainParking que
 * gestiona la entrada y salida de coches.
 */
public class Parking {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";
    public static final String YELLOW = "\u001B[33m";

    /**
     * Número total de plazas disponibles en el parking.
     */
    private int numeroPlazas;
    /**
     * Número actual de plazas ocupadas en el parking.
     */
    private int plazasOcupadas = 0;
    /**
     * Lista que representa las plazas del parking, cada una asociada a un coche.
     */
    private List<Coche> plazas = new ArrayList<>(numeroPlazas);


    private Queue<Coche> colaEspera = new LinkedList<>();

    /**
     * Constructor que inicializa un nuevo objeto Parking con un número total
     * de plazas, creando una lista de plazas inicialmente vacía(se establece mediante nulos).
     *
     * @param numeroPlazas Número total de plazas disponibles en el parking.
     */
    public Parking(int numeroPlazas) {
        this.numeroPlazas = numeroPlazas;
        // Inicializar todas la plazas a null para que esten vacias
        for (int i = 0; i < numeroPlazas; i++) {
            plazas.add(null);
        }

    }

    /**
     * Método sincronizado que simula la entrada de un coche al parking. Espera
     * hasta que haya plazas disponibles, luego estaciona el coche en la primera
     * plaza libre y notifica a otros coches en espera.
     *
     * @param numeroCoche Número único que identifica al coche que desea entrar.
     * @param cocheEnt    Instancia del objeto Coche que desea entrar al parking.
     */
    public synchronized void entrar(int numeroCoche, Coche cocheEnt) {
        // Añadir el coche a la cola de espera
        synchronized (this) {
            colaEspera.add(cocheEnt);

            // El vehículo queda a la espera hasta que haya una plaza libre
            while (colaEspera.peek().getNumeroCoche() != cocheEnt.getNumeroCoche() || numeroPlazas == plazasOcupadas) {
                try {
                    System.out.println(RED + "El coche " + numeroCoche + " está esperando para entrar." + RESET);
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            // Al entrar aumenta así se controla la capacidad máxima del parking
            plazasOcupadas++;
            System.out.println(GREEN + "El coche " + numeroCoche + " entra en el parking. Plazas ocupadas = " + plazasOcupadas+ "/"+numeroPlazas+RESET);

            // Se realiza una búsqueda en el array para encontrarle una plaza vacía
            boolean plazaEncon = false;
            for (int i = 0; !plazaEncon && i < plazas.size(); i++) {
                synchronized (this) {
                    if (plazas.get(i) == null) {
                        // Encuentra la primera plaza disponible
                        plazas.set(i, cocheEnt);
                        plazaEncon = true;
                        System.out.println(GREEN + "\t Coche estacionado en la plaza " + (i + 1) + RESET);
                    }
                }
            }

            // Remover el coche de la cola después de entrar (fuera del bucle for)
            colaEspera.poll();
            // Notificamos que el vehiculo ha entrado
            notifyAll();
        }
    }


    /**
     * Método sincronizado que simula la salida de un coche del parking. Reduce
     * el número de plazas ocupadas y libera la plaza que ocupa el coche.
     *
     * @param numeroCoche Número único que identifica al coche que desea salir.
     * @param cocheSal    Instancia del objeto Coche que desea salir del parking.
     */
    public synchronized void salir(int numeroCoche, Coche cocheSal) {
        //No deberia intentar salir del parking si no ha entrado pero establecemos este mecanismo para asegurar
        if (plazasOcupadas > 0) {
            //Reducimos los coches que hay dentro del parking
            plazasOcupadas--;
            System.out.println(BLUE + "El coche " + numeroCoche + " sale del parking. Plazas ocupadas = " + plazasOcupadas + RESET);
            //Realizamos una busqueda de su plaza mediante su identificador (numero coche"aka matricula")
            for (int i = 0; i < plazas.size(); i++) {
                synchronized (this) {
                    // Hay que asegurarse de que no sea null para que no de problemas el getNumeroCoche()
                    if (plazas.get(i) != null && plazas.get(i).getNumeroCoche() == numeroCoche) {
                        // Encuentra la plaza que ocupa el coche y la deja libre
                        plazas.set(i, null);
                        System.out.println(BLUE + "\t Coche deja libre la plaza " + (i + 1) + RESET);
                        // Se puede establecer un brake para que no continue buscando
                        // break;
                    }
                }
            }

            // Mostrar el primero en la cola para comprobar que es el siguiente que entra
            if (colaEspera.isEmpty()) {
                System.out.println(YELLOW + "No hay coches en la cola" + RESET);
            } else {
                System.out.println(YELLOW + "Primer coche en la cola: " + colaEspera.peek().getNumeroCoche() + RESET);
            }
            // notificamos a todos los hilos la salida para que lo reciban todos los que estan esperando
            notifyAll();
        }
    }

}


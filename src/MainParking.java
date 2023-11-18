/**
 * La clase MainParking es la clase principal.
 * Se encarga de inicializar un objeto Parking y crear varios objetos Coche,
 * cada uno ejecutándose en un hilo separado para simular el comportamiento
 * concurrente de coches entrando y saliendo del parking.
 */

public class MainParking {

    public static void main(String[] args) {
        //Si se desea compilar e introducir parametros personalizados se debe implementar un metodo para ello

        // parametros fijos
        int plazasTotales = 5;
        int numeroCoches = 10;


        // Crear una instancia del objeto Parking con la cantidad de plazas especificada
        Parking parking = new Parking(plazasTotales);

        // Crear y ejecutar hilos de coches que son los que entran y salen
        for (int i = 0; i < numeroCoches; i++) {
            new Coche(i + 1, parking).start();

        }


    }

}

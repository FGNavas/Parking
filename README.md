# Práctica Sincronización de Hilos: Parking


## Enunciado

Escribe una clase llamada Parking que reciba el número de plazas del parking y el número de coches existentes en el
sistema. Se deben crear tantos threads como coches haya. El parking dispondrá de una única entrada y una única salida.
En la entrada de vehículos habrá un dispositivo de control que permita o impida el acceso de los mismos al parking,
dependiendo del estado actual del mismo (plazas de aparcamiento disponibles). Los tiempos de espera de los vehículos
dentro del parking son aleatorios. En el momento en que un vehículo sale del parking, notifica al dispositivo de control
el número de la plaza que tenía asignada y se libera la plaza que estuviera ocupando, quedando así estas nuevamente
disponibles. Un vehículo que ha salido del parking esperará un tiempo aleatorio para volver a entrar nuevamente en el
mismo. Por tanto, los vehículos estarán entrando y saliendo indefinidamente del parking. Es importante que se diseñe el
programa de tal forma que se asegure que, antes o después, un vehículo que permanece esperando a la entrada del parking
entrará en el mismo (no se produzca inanición).

## Explicación Inicial

El programa se ideó de manera inicial solo con un contador, para controlar el número de vehículos en el parking. Al
releer el enunciado del ejercicio, se comprobó que se debía establecer una plaza y tratar la inanición, por lo que se
agregaron dichas funcionalidades después.

## Clases

### MainParking

Clase que se encarga de inicializar el objeto Parking y los hilos coches, en la cantidad introducida por el usuario y la
cantidad establecida.

### Parking

Clase encargada de gestionar la salida del vehículo y la entrada, lleva en conteo de los vehículos en el interior y de
las plazas ocupadas y libres.

#### Métodos

- **entrar()**: gestiona la entrada del vehículo y de la asignación de la plaza libre, gestiona la cola de espera.

- **salir()**: gestiona la salida del vehículo y se encarga de buscar la plaza que ocupaba para dejarla libre.

### Coche

Clase que simula los vehículos.

#### Métodos

- **run()**: Método que realiza el comportamiento del vehículo.

- **espera()**: Método auxiliar para simular un tiempo de espera aleatorio entre 0 y 10 segundos.

## Ejecución del Programa

El programa se inicia en MainParking, que será el encargado de preparar el parking con su número de plazas y los coches.
Debido a que el método run() de Coche inicia en parking.entrar, todos los coches quieren entrar a la vez. Si se quiere 
un comportamiento aleatorio, se debe añadir un espera() delante de parking.entrar y eliminar el último.
El vehiculo quiere entrar por lo que se añade a la cola de espera (lista colaEspera), si las plazas ocupadas son menos 
que el máximo de plazas el vehiculo entrara y se le asignara una plaza libre y se le saca de la cola de espera. 
En caso de encontrarse el parking lleno permanecera a la espera, como en la colaEspera están los vehiculos se evita la 
inanición, otorgando solo permiso para entrar al que este en la primera posición de la cola. Una vez que haya espacio 
el vehiculo sale de la cola de espera. Para la salida no hay cola, la salida es libre, lo que el programa realiza cuando 
un vehiculo sale es liberar la plaza ocupada y restarse de la ocupación.




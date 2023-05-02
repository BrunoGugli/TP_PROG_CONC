import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;


public class Main {
    private static final Logger logger = Logger.getLogger("logtp1");
    public Main() {
    }
    public static void main(String[] args) throws InterruptedException {

        /***
         * Los 4 contadores para cada proceso y
         * el contador de imagenes totalmente modificadas (objetos de la clase Counter).
         */
        Counter contador1 = new Counter();
        Counter contador2 = new Counter();
        Counter contadorModified = new Counter();
        Counter contador3 = new Counter();
        Counter contador4 = new Counter();

        /**
         * La cantidad de hilos asignados para cada proceso.
         * Se pide el valor por consola al usuario.
         */
        int cantHilosProc1, cantHilosProc2, cantHilosProc3, cantHilosProc4;

        Scanner input = new Scanner(System.in);
        System.out.println("Ingrese la cantidad de hilos para el proceso 1: ");
        cantHilosProc1 = input.nextInt();

        System.out.println("Ingrese la cantidad de hilos para el proceso 2: ");
        cantHilosProc2 = input.nextInt();

        System.out.println("Ingrese la cantidad de hilos para el proceso 3: ");
        cantHilosProc3 = input.nextInt();

        System.out.println("Ingrese la cantidad de hilos para el proceso 4: ");
        cantHilosProc4 = input.nextInt();



        /***
         * El contenedor de imagenes.
         */
        ContImg cont = new ContImg();


        /***
         * Lista donde se copiaran las imagenes mediante
         * el proceso 4 una vez pasen por los 3 procesos
         * correspondientes.
         */
        ContImg listaCopia = new ContImg();


        long StartTime = System.currentTimeMillis();

        /**
         * Array de hilos en uso.
         */
        ArrayList<Thread> threads = new ArrayList<>();

        /**
         * Inicialización de todos los hilos.
         */
        for (int i = 1; i <= cantHilosProc1; i++) {
            Thread thread1 = new Thread(new Process1(cont, contador1), "Hilo 1" + i);
            thread1.start();
            threads.add(thread1);
        }
        for (int i = 1; i <= cantHilosProc2; i++) {
            Thread thread2 = new Thread(new Process2(cont, contador2, contadorModified, cantHilosProc2), "Hilo 2" + i);
            thread2.start();
            threads.add(thread2);
        }
        for (int i = 1; i <= cantHilosProc3; i++) {
            Thread thread3 = new Thread(new Process3(cont, contador3, cantHilosProc2), "Hilo 3" + i);
            thread3.start();
            threads.add(thread3);
        }
        for (int i = 1; i <= cantHilosProc4; i++) {
            Thread thread4 = new Thread(new Process4(cont, listaCopia, contador4), "Hilo 4" + i);
            thread4.start();
            threads.add(thread4);
        }


        /**
         * Logger.
         */
        try {
            FileHandler fileHandler = new FileHandler("Resources/logtp1.txt", false);
            SimpleFormatter formatter = new SimpleFormatter() {
                private static final String format = "[%1$tF %1$tT] [%2$s] %3$s %n";
                @Override
                public synchronized String format(java.util.logging.LogRecord lr) {
                    return String.format(format,
                            new java.util.Date(lr.getMillis()),
                            lr.getLevel().getLocalizedName(),
                            lr.getMessage()
                    );
                }
            };
            fileHandler.setFormatter(formatter);
            logger.setUseParentHandlers(false);
            logger.addHandler(fileHandler);
            logger.setLevel(Level.INFO);

            while (!allThreadsTerminated(threads)) {
                synchronized (listaCopia) {
                    logger.info("Cantidad de imágenes insertadas en el primer contenedor  : " + contador1.getCount());
                    logger.info("Cantidad de imágenes completamente mejoradas  : " + contadorModified.getCount());
                    logger.info("Cantidad de imágenes ajustadas  : " + contador3.getCount());
                    logger.info("Cantidad de imágenes traspasadas al segundo contenedor  : " + contador4.getCount());
                    for (int i = 0; i < threads.size(); i++) {
                        logger.info("Estado del " + threads.get(i).getName() + " : " + threads.get(i).getState());
                    }
                    logger.info("-----------------------------------------------------------------------------------------");
                }
                TimeUnit.MILLISECONDS.sleep(500);
            }

            //Una escritura más en el logger con todos los hilos en TERMINATED
            logger.info("Cantidad de imágenes insertadas en el primer contenedor  : " + contador1.getCount());
            logger.info("Cantidad de imágenes completamente mejoradas  : " + contadorModified.getCount());
            logger.info("Cantidad de imágenes ajustadas  : " + contador3.getCount());
            logger.info("Cantidad de imágenes traspasadas al segundo contenedor  : " + contador4.getCount());
            for (int i = 0; i < threads.size(); i++) {
                logger.info("Estado del " + threads.get(i).getName() + " : " + threads.get(i).getState());
            }
            logger.info("-----------------------------------------------------------------------------------------");


        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error al crear el archivo de registro.", e);
        }


        /**
         * Cálculo del tiempo de ejecución total del programa.
         */
        long endTime = System.currentTimeMillis();
        long totalTime = (endTime - StartTime);

        System.out.println("Tiempo total de la ejecución: " + totalTime + " milisegundos");
    }

    /**
     * Comprueba si todos los hilos de un array de <Thread>
     * están en estado TERMINATED
     *
     *
     * @param threads el ArrayList<Thread> que contiene a los
     *                hilos a comprobar.
     * @return true si todos los hilos están en TERMINATED -
     *         false si alguno de los hilos todavía no está en TERMINATED
     */
    public static boolean allThreadsTerminated(ArrayList<Thread> threads){
        for(Thread th : threads){
            if(!th.getState().equals(Thread.State.TERMINATED)){
                return false;
            }
        }
        return true;
    }


}

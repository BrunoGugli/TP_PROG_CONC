import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class Main {
    public Main() {
    }
    public static void main(String[] args) throws InterruptedException {
            long startTime = System.currentTimeMillis();
            
            /***
             * Lista donde se copiaran las imagenes mediante
             * el proceso 4 una vez pasen por los 3 procesos
             * correspondientes.
             */
            ArrayList<Image> ListaCopia = new ArrayList<>();

            /***
             * Los 4 contadores para cada proceso y
             * el contador de imagenes totalmente modificadas (objetos de la clase Counter).
             */
            Counter contador1 = new Counter();
            Counter contador2 = new Counter();
            Counter contadorModified = new Counter();
            Counter contador3 = new Counter();
            Counter contador4 = new Counter();
            int cantHilosProc2 = 4;

        /***
         * El contenedor de imagenes.
         */
            ContImg cont = new ContImg(ListaCopia);

            /**
             * La instanciación de todos los procesos.
             */
            Process1 P11 = new Process1(cont, contador1,cantHilosProc2);
            Process1 P12 = new Process1(cont, contador1,cantHilosProc2);

            Process2 P21 = new Process2(cont, contador2, contadorModified,cantHilosProc2);
            Process2 P22 = new Process2(cont, contador2, contadorModified,cantHilosProc2);
            Process2 P23 = new Process2(cont, contador2, contadorModified,cantHilosProc2);
            Process2 P24 = new Process2(cont, contador2, contadorModified,cantHilosProc2);

            Process3 P31 = new Process3(cont, contador3,cantHilosProc2);
            Process3 P32 = new Process3(cont, contador3,cantHilosProc2);
            Process3 P33 = new Process3(cont, contador3,cantHilosProc2);

            Process4 P41 = new Process4(cont, contador4);
            Process4 P42 = new Process4(cont, contador4);


            Thread thread11 = new Thread(P11, "Hilo 11");
            Thread thread12 = new Thread(P12, "Hilo 12");

            Thread thread21 = new Thread(P21, "Hilo 21");
            Thread thread22 = new Thread(P22, "Hilo 22");
            Thread thread23 = new Thread(P23, "Hilo 23");
            Thread thread24 = new Thread(P24, "Hilo 24");

            Thread thread31 = new Thread(P31, "Hilo 31");
            Thread thread32 = new Thread(P32, "Hilo 32");
            Thread thread33 = new Thread(P33, "Hilo 33");

            Thread thread41 = new Thread(P41, "Hilo 41");
            Thread thread42 = new Thread(P42, "Hilo 42");

            /**
             * Array de hilos para luego escribir su estado en el log.
             */
            Thread threads[];
            threads = new Thread[11];

            threads[0] = thread11;
            threads[1] = thread12;
            threads[2] = thread21;
            threads[3] = thread22;
            threads[4] = thread23;
            threads[5] = thread24;
            threads[6] = thread31;
            threads[7] = thread32;
            threads[8] = thread33;
            threads[9] = thread41;
            threads[10] = thread42;

            long threadStartTime = System.currentTimeMillis();

            thread11.start();
            thread12.start();
            thread21.start();
            thread22.start();
            thread23.start();
            thread24.start();
            thread31.start();
            thread32.start();
            thread33.start();
            thread41.start();
            thread42.start();

            /**
             * FileWriter para escribir el log cada 500 milisegundos.
             */
            try (FileWriter file = new FileWriter("E:\\FACU\\ProgConc\\TP1_PC_LosPowerRanger_v11_Entregable_Final\\logtp1.txt"); PrintWriter pw = new PrintWriter(file);) {
                while (!(thread41.getState().equals(Thread.State.TERMINATED)) || !(thread42.getState().equals(Thread.State.TERMINATED))) {
                    synchronized (ListaCopia) {
                        pw.println("Cantidad de imágenes insertadas en el primer contenedor  : " + contador1.getCount());
                        pw.println("Cantidad de imágenes completamente mejoradas  : " + contadorModified.getCount());
                        pw.println("Cantidad de imágenes ajustadas  : " + contador3.getCount());
                        pw.println("Cantidad de imágenes traspasadas al segundo contenedor  : " + contador4.getCount());
                        for (int i = 0; i < 11; i++) {
                            pw.println("Estado del " + threads[i].getName() + " : " + threads[i].getState());
                        }
                        pw.println("-----------------------------------------------------------------------------------------");
                    }
                    TimeUnit.MILLISECONDS.sleep(500);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            /**
             * Cálculo del tiempo de ejecución total del programa.
             */
            long endTime = System.currentTimeMillis();
            long totalThreadTime = (endTime - threadStartTime);
            long totalTime = (endTime - startTime);

            System.out.println("Tiempo total de ejecución de todo el programa: " + totalTime + " milisegundos.");
            System.out.println("Tiempo total de la ejecución de los hilos: " + totalThreadTime + " milisegundos");

    }

}

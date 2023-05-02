import java.util.ArrayList;

/**
 * Modela el proceso 2, también implementa la interfaz Runneable.
 */
public class Process2 implements Runnable {
    private ContImg cont;
    private Counter contador2;
    private Counter contadorModified;

    /**
     * Constructor con parámetros.
     *
     * @param cont El contenedor general de imagenes.
     * @param contador2 El contador propio del proceso.
     * @param contadorModified El contador que lleva el registro de imagenes
     *                         totalmente modificadas por los  3 hilos.
     *
     */
    public Process2(ContImg cont, Counter contador2, Counter contadorModified) {
        this.cont = cont;
        this.contadorModified = contadorModified;
        this.contador2 = contador2;
    }

    /**
     * Este metodo modifica hilo por hilo cada imagen. El contador
     * debe contar hasta 300, ya que cada ciclo del while hace que
     * un solo hilo modifique la imágen. Lo que explica
     * 3 hilos * 100 imagenes = 300 ciclos.
     */
    @Override
    public void run() {
        while (contador2.getCount() < 300) {
            Image i = cont.getImagen();
            if (i!=null) {
                        if(!i.getMod(Thread.currentThread().getName())) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            i.setMH1(true, contador2, contadorModified);
                        }
                i.soltar();
            }
        }
        System.out.println("Termine proceso 2 " + Thread.currentThread().getName());
    }
}



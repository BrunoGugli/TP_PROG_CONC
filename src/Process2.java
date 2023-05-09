/**
 * Modela el proceso 2, también implementa la interfaz Runneable.
 */
public class Process2 implements Runnable {
    private ContImg cont;
    private Counter contador2;
    private Counter contadorModified;
    private int hilos;

    /**
     * Constructor con parámetros.
     *
     * @param cont El contenedor general de imagenes.
     * @param contador2 El contador propio del proceso.
     * @param contadorModified El contador que lleva el registro de imagenes
     *                         totalmente modificadas por todos los hilos del proceso 2.
     *
     */
    public Process2(ContImg cont, Counter contador2, Counter contadorModified,int hilos) {
        this.cont = cont;
        this.contadorModified = contadorModified;
        this.contador2 = contador2;
        this.hilos = hilos;
    }

    /**
     * Este metodo modifica hilo por hilo cada imagen. El contador
     * debe contar hasta (hilos*100), ya que cada ciclo del while hace que
     * un solo hilo modifique la imágen.
     */
    @Override
    public void run() {
        while (contador2.getCount() < (hilos*100)) {
            Image i = cont.getImagen();
            if (i!=null){
                if(!i.getModPorHilo(Thread.currentThread().getName())) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    i.setMH(contador2,contadorModified,hilos);
                }
                i.soltar();
            }
        }
        System.out.println("Termine proceso 2 " + Thread.currentThread().getName());
    }
}



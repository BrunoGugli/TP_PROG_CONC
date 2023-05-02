/**
 * Modela el proceso 3, implementa Runneable.
 */
public class Process3 implements Runnable {
    private ContImg cont;
    private Counter contador3;
    private int Hilos;

    /**
     * Constructor del proceso con parámetros.
     *
     * @param cont El contenedor general de imágenes.
     * @param contador3 El contador propio del proceso.
     */
    public Process3(ContImg cont, Counter contador3,int Hilos) {
        this.cont = cont;
        this.contador3 = contador3;
        this.Hilos = Hilos;
    }

    /**
     * Este metodo comprueba si se le han aplicado las
     * modificaciones del proceso 2 a una imagen
     * y si esto se cumple, le aplique el recorte.
     */
    @Override
    public void run() {
        while (contador3.getCount() < 100) {
            Image i = cont.getImagen();
            if (i!=null){
                if(i.getModified(Hilos) && !i.getREC()) {
                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    i.setREC(true, contador3);
                }
                i.soltar();
            }
        }
        System.out.println("Termine proceso 3 " + Thread.currentThread().getName());
    }
}


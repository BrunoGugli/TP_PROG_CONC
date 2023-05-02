/**
 * Modela el proceso 1, por lo que implementa la interfaz Runnable.
 */
public class Process1 implements Runnable{
    private Image img;
    private ContImg cont;
    private Counter contador1;




    /**
     * Constructor con parámetros.
     * Inicializa las variables de instancia.
     *
     * @param cont El contenedor general de imagenes.
     * @param contador1 El contador propio del proceso.
     */
    public Process1(ContImg cont, Counter contador1){
        this.cont = cont;
        this.contador1=contador1;
    }

    /**
     * Este metodo realiza las carga de las 100 imágenes.
     */
    @Override
    public void run() {
        int i = 1;
        while (contador1.getCount() < 100) {
            img = new Image("Imagen " + i);
            try {
                Thread.sleep(10);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
            cont.addImagen(img,contador1);
            i++;
        }
        System.out.println("Termine proceso 1 " + Thread.currentThread().getName());
    }

}


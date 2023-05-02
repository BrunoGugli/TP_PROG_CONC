/**
 * Modela el proceso 4, implementa la interfaz Runneable.
 */
public class Process4 implements Runnable {
    private ContImg cont;
    private boolean entro;
    private Counter contador4;

    /**
     * Constructor del proceso con parámetros.
     *
     * @param cont El contenedor general de imágenes.
     * @param contador4 El contador para llevar el número de
     *                  imágenes pasadas al nuevo contenedor.
     */
    public Process4(ContImg cont, Counter contador4) {
        this.cont = cont;
        this.entro=true;
        this.contador4 = contador4;
    }

    /**
     * Este metodo comprueba a cada imagen si se le aplicó
     * el recorte, si esto se cumple la remueve del contenedor
     * principal y la mueve al nuevo contenedor final.
     */
    @Override
    public void run() {
        while (entro || !cont.getArray().isEmpty()){
            if(!cont.getArray().isEmpty()) {
                this.entro = false;
                Image i = cont.getImagen();
                if (i != null) {
                    try{
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    cont.copiarImagen(i);
                    cont.removerImagen(i,contador4);  //Dividir en 2 cosas (remover y copiar)
                    i.soltar();
                }
            }
        }
        System.out.println("Termine proceso 4 " + Thread.currentThread().getName());
    }

}





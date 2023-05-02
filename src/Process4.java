/**
 * Modela el proceso 4, implementa la interfaz Runneable.
 */
public class Process4 implements Runnable {
    private ContImg cont;
    private ContImg listaCopia;
    private boolean entro;
    private Counter contador4;

    /**
     * Constructor del proceso con parámetros.
     *
     * @param cont El contenedor general de imágenes.
     * @param contador4 El contador para llevar el número de
     *                  imágenes pasadas al nuevo contenedor.
     */
    public Process4(ContImg cont, ContImg listaCopia, Counter contador4) {
        this.cont = cont;
        this.listaCopia = listaCopia;
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
                    if(i.getREC()) {
                        try {
                            Thread.sleep(15);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        listaCopia.addImagenCopia(i);
                        cont.removerImagen(i, contador4);
                    }
                    i.soltar();
                }
            }
        }
        System.out.println("Termine proceso 4 " + Thread.currentThread().getName());
    }

}






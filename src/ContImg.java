
import java.util.Random;
import java.util.ArrayList;

/**
 * Modela el contenedor principal de imagenes.
 */
public class ContImg {
    private ArrayList<Image> ListaImg;

    /**
     * Constructor con parámetros.
     * Inicializa un ArrayList para implementar el
     * contenedor y almacenar las imagenes.
     *
     *
     */
    public ContImg(){
        ListaImg=new ArrayList<>();
    }



    /**
     * Este método se encarga de que cada hilo del proceso 1 agregue una imagen
     * al contenedor mediante exclusión mutua. Hace nuevamente la comprobación del
     * contador, porque en el proceso 1, los dos hilos leen que el
     * contador es menor a 100 (por ej. cuando es 99) por lo que se cumple la
     * condición, ambos entran a este metodo y ambos incrementarían el contador.
     * Entonces haciendo la comprobación nuevamente se evita ese problema.
     *
     * Aumenta el contador 1.
     *
     * @param img la imagen a agregar.
     * @param contador el contador del proceso 1.
     *
     */
    public void addImagen(Image img, Counter contador) {
        synchronized (ListaImg) {
            if (contador.getCount() < 100) {
                ListaImg.add(img);
                contador.increment();
            }
        }
    }


    public void addImagenCopia(Image img) {
        synchronized (ListaImg) {
            ListaImg.add(img);
        }
    }



    /**
     * Este metodo devuelve una imagen aleatoria del contenedor teniendo
     * en cuenta si esta ya se encuentra en proceso (isTomada) o está libre.
     *
     * @return La imagen aleatoria o null en caso de no poder hacerlo.
     *
     */
    public Image getImagen() {
        try {
            synchronized (ListaImg){
                while (!ListaImg.isEmpty()) {
                    Random random = new Random();
                    int numeroAleatorio = random.nextInt(ListaImg.size() + 1);
                    if(!ListaImg.get(numeroAleatorio).isTomada()){
                        ListaImg.get(numeroAleatorio).tomar();
                        return ListaImg.get(numeroAleatorio);
                    }
                }
                return null;
            }
        }catch (IndexOutOfBoundsException e) {
            return null;
        }

    }


    /**
     * Este método devuelve el ArrayList que contiene las imágenes.
     *
     * @return La Lista (de la clase ArrayList) que contiene las imágenes.
     */
    public ArrayList<Image> getArray() {
        synchronized (ListaImg) {
            return this.ListaImg;
        }
    }

    /**
     * Este metodo remueve una imagen del contenedor según
     * el índice y la agrega al array final que contiene
     * las imágenes totalmente procesadas.
     *
     * Aumenta el contador 4.
     *
     * @param i El índice de la imagen a remover.
     * @param contador4 El contador del proceso 4.
     */
    public void removerImagen(Image i,Counter contador4){
        synchronized (ListaImg) {
            if (ListaImg.contains(i)) {
                this.ListaImg.remove(i);
                contador4.increment();
            }
        }
    }
}

import java.util.ArrayList;

/**
 * Modela las imagenes que deben ser procesadas.
 */
public class Image {
    private String nombre;
    private boolean REC;
    private boolean tomada;

    public ArrayList<String> hilosModificaron;


    /**
     * Constructor de la instancia, inicializa los checkers necesarios
     * para las distintas funciones que actuan sobre una imagen.
     *
     * @param nombre El nombre de la imagen.
     */
    public Image(String nombre){
        this.nombre = nombre;
        this.tomada=false;
        REC = false;
        hilosModificaron = new ArrayList<>();
    }

    /**
     * Este metodo devuelve el flag que indica si la imagen
     * fue modificada por un determindo hilo en el proceso 2.
     *
     * @param i El nombre del hilo a comprobar si modificó.
     *
     * @return True o false según esté o no modificada.
     */
    public boolean getModPorHilo(String i) {
        return hilosModificaron.contains(i);
    }


    /**
     * Este metodo devuelve el flag que indica si la imagen
     * fue modificada por todos los hilos del proceso 2.
     *
     * @param hilos La cantidad de hilos del proceso 2.
     *
     * @return True o false según esté o no modificada.
     */
    public boolean getModified(int hilos) {
        if(this.hilosModificaron.size() == hilos) {
            return true;
        }
        return false;
    }

    /**
     * Este metodo devuelve el flag que indica si la imagen
     * fue recortada en el proceso 3.
     *
     * @return True o false según esté o no recortada.
     */
    public boolean getREC() {
        return REC;
    }

    /**
     * Este metodo devuelve el flag que indica si la imagen
     * está tomada para realizarle algún proceso.
     *
     * @return True o false según esté o no tomada.
     */
    public boolean isTomada() {
        return tomada;
    }


    /**
     * Este método agrega el hilo que está modificando a la imagen, al
     * array de hilos que modificaron a la imagen.
     *
     * @param contador El contador2 del proceso 2.
     * @param contadorModified El contador que lleva el registro
     *                         de imagenes modificadas por todos los
     *                         hilos del proceso 2.
     */
    public void setMH(Counter contador, Counter contadorModified, int hilos) {
        this.hilosModificaron.add(Thread.currentThread().getName());
        contador.increment();
        if(this.getModified(hilos)){
            contadorModified.increment();
        }
    }



    /**
     * Este método setea el flag en true de que la
     * imagen fue recortada en el proceso 3.
     *
     * Incrementa en 1 el contador 3.
     *
     * @param REC El flag en true de que fue recortada.
     * @param contador El contador del proceso 3.
     */
    public void setREC(boolean REC, Counter contador){
        this.REC = REC;
        contador.increment();
    }

    /**
     * Este método toma la imagen para realizarle algún proceso
     * indicando a otros hilos de que está ocupada y no puede
     * ser tomada por estos últimos.
     * Setea el flag de tomada en true.
     */
    public void tomar() {
        if(!this.isTomada()){
            this.tomada=true;
        }
    }

    /**
     * Este método suelta una imagen tomada luego de realizarle
     * algún proceso indicando a otros hilos de que está libre y
     * puede ser tomada por alguno de estos últimos.
     *
     * Setea el flag de tomada en false.
     */
    public void soltar() {
        if(this.isTomada()) {
            this.tomada = false;
        }
    }
}

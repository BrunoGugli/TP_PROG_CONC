import java.util.ArrayList;

/**
 * Modela las imagenes que deben ser procesadas con sus
 * setters y getters correspondientes.
 */
public class Image {
    private String nombre;
    private boolean MH1;
    private boolean MH2;
    private boolean MH3;
    private boolean REC;
    private boolean tomada;

    private ArrayList<String> hilosModificaron;

    private Object controlTomada, controlREC, controlArray;

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
        controlArray=new Object();
        controlTomada=new Object();
        controlREC=new Object();
        hilosModificaron = new ArrayList<>();
    }

    /**
     * Este metodo devuelve el flag que indica si la imagen
     * fue modificada por el hilo 1 en el proceso 2.
     *
     * @return True o false según esté o no modificada.
     */
    public boolean getMod(String i) {
        synchronized (this){ //Sincronizar con distintas llaves
            return hilosModificaron.contains(i);
        }
    }


    /**
     * Este metodo devuelve el flag que indica si la imagen
     * fue modificada por los 3 hilos del proceso 2.
     *
     * @return True o false según esté o no modificada.
     */
    public boolean getModified(int hilos) {
        synchronized (controlArray){
            if(this.hilosModificaron.size() == hilos) {
                return true;
            }
            return false;
        }
    }

    /**
     * Este metodo devuelve el flag que indica si la imagen
     * fue recortada en el proceso 3.
     *
     * @return True o false según esté o no recortada.
     */
    public boolean getREC() {
        synchronized (controlREC){
            return REC;
        }
    }

    /**
     * Este metodo devuelve el flag que indica si la imagen
     * está tomada para realizarle algún proceso.
     *
     * @return True o false según esté o no tomada.
     */
    public boolean isTomada() {
        synchronized (controlTomada){
            return tomada;
        }
    }


    /**
     * Este método setea el flag en true de que la
     * imagen fue modificada por el hilo 1 en el proceso 2.
     *
     * @param MH1 El flag en true de que fue modificada.
     * @param contador El contador2 del proceso 2.
     * @param contadorModified El contador que lleva el registro
     *                         de imagenes modificadas por los 3 hilos.
     */
    public void setMH(boolean MH1, Counter contador, Counter contadorModified) {
        synchronized (this) {
            if(MH1 == true && !this.getMod(Thread.currentThread().getName())){
                this.hilosModificaron.add(Thread.currentThread().getName());
                contador.increment();
            }
        }
    }



    /**
     * Este método setea el flag en true de que la
     * imagen fue recortada en el proceso 3.
     *
     * @param REC El flag en true de que fue recortada.
     * @param contador El contador del proceso 3.
     */
    public void setREC(boolean REC, Counter contador){
        synchronized (controlREC){
            this.REC = REC;
            contador.increment();
        }
    }

    /**
     * Este método toma la imagen para realizarle algún proceso
     * indicando a otros hilos de que está ocupada y no puede
     * ser tomada por estos últimos.
     * Setea el flag de tomada en true.
     */
    public void tomar() {
        synchronized (controlTomada){
            if(!this.isTomada()){
                this.tomada=true;
            }
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
        synchronized (controlTomada){
            if(this.isTomada()) {
                this.tomada = false;
            }
        }
    }
}

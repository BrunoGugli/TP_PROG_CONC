/**
 * Modela un contador que inicia en 0 y puede
 * incrementarse indefinidamente.
 */
public class Counter {
    private int count;
    public Counter(){
        this.count=0;
    }

    /**
     * Este método incrementa en uno el valor del contador.
     */
    public synchronized void increment() {
        count++;
    }

    /**
     * Este método devuelve el valor actual del contador.
     *
     * @return El valor del contador.
     */
    public synchronized int getCount() {
        return count;
    }
}

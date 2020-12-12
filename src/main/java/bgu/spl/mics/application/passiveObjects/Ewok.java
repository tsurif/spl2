package bgu.spl.mics.application.passiveObjects;

/**
 * Passive data-object representing a forest creature summoned when HanSolo and C3PO receive AttackEvents.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You may add fields and methods to this class as you see fit (including public methods).
 */
public class Ewok {
	private int serialNumber;
	public boolean available;

    public Ewok (int serialNumber){
        available = true;
        this.serialNumber = serialNumber;
    }
    /**
     * Acquires an Ewok
     */
    public synchronized void acquire() {
        while (!available) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        available = false;
    }

    /**
     * release an Ewok
     */
    public synchronized void release() {//throws IllegalAccessException {
        //if(available) throw new IllegalAccessException("\"one shuold not release an Ewok - if free the ewok is\" m. Yoda");//TODO throws exeption is available = true?
        available = true;
        System.out.println("Ewok num " + serialNumber + " dismiss" );
        notifyAll();
    }
}

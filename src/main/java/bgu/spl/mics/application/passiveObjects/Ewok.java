package bgu.spl.mics.application.passiveObjects;

/**
 * Passive data-object representing a forest creature summoned when HanSolo and C3PO receive AttackEvents.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You may add fields and methods to this class as you see fit (including public methods).
 */
public class Ewok {
	int serialNumber;
	boolean available;
	public final Object lock;

    public Ewok (int serialNumber){
        available = true;
        this.serialNumber = serialNumber;
        lock=new Object();
    }
    /**
     * Acquires an Ewok
     */
    public void acquire() {
		available = false;
    }

    /**
     * release an Ewok
     */
    public void release() {
    	available = true;
    }
}

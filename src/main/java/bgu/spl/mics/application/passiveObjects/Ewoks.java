package bgu.spl.mics.application.passiveObjects;


/**
 * Passive object representing the resource manager.
 * <p>
 * This class must be implemented as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add ONLY private methods and fields to this class.
 */
public class Ewoks {
    Ewok[] ewoksArr;
    Object[] ewokLockers;
    private static class EwoksHolder{
        private static Ewoks instance = new Ewoks();
    }
    public static Ewoks getInstance(){
        return Ewoks.EwoksHolder.instance;
    }

    public void initEwoks(int size){
        ewoksArr = new Ewok[size];
        for (int i = 0; i < size; i = i + 1){
            ewoksArr[i] = new Ewok(i);
        }
        ewokLockers = new Object[size];
        for (int i = 0; i < size; i = i + 1){
            ewokLockers[i] = new Object();
        }
    }
    public void acquire(int[] ewoksToUse, int index){//assuming ewoksTouse is sorted
            synchronized (ewokLockers[ewoksToUse[index]]){
                ewoksArr[ewoksToUse[index]].acquire();
                if(index<ewoksToUse.length-1) acquire(ewoksToUse,index + 1);
            }
    }



}

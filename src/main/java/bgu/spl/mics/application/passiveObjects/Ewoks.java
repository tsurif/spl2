package bgu.spl.mics.application.passiveObjects;


import java.util.List;

/**
 * Passive object representing the resource manager.
 * <p>
 * This class must be implemented as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add ONLY private methods and fields to this class.
 */
public class Ewoks {
    private Ewok[] ewoksArr;
    private static class EwoksHolder{
        private static Ewoks instance = new Ewoks();
    }
    public static Ewoks getInstance(){
        return Ewoks.EwoksHolder.instance;
    }

    public void initEwoks(int size){
        size = size + 1;
        ewoksArr = new Ewok[size];
        for (int i = 0; i < size; i = i + 1){
            ewoksArr[i] = new Ewok(i);
        }
    }

    public void acquire(List<Integer> ewoksToUse, String name){//assuming ewoksTouse is sorted
        System.out.println("Ewoks resiving request By" + name);
        for (Integer i:ewoksToUse) {
            ewoksArr[i].acquire();
            System.out.println("Ewok num " + i + " calls for duty to " + name );
        }
    }

    public void release(List<Integer> ewoksToUse){
        for (Integer i: ewoksToUse){
            ewoksArr[i].release();
        }
    }
}

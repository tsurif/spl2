package bgu.spl.mics.application.passiveObjects;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Passive data-object representing a Diary - in which the flow of the battle is recorded.
 * We are going to compare your recordings with the expected recordings, and make sure that your output makes sense.
 * <p>
 * Do not add to this class nothing but a single constructor, getters and setters.
 */
public class Diary {
    private static class DiaryHolder{
        private static Diary instance = new Diary();
    }

    public static Diary getInstance(){
        return Diary.DiaryHolder.instance;
    }

    private AtomicInteger totalAttacks; //Can be updated by C3PO or HanSolo
    private long HanSoloFinish;
    private long C3POFinish;
    private long R2D2Deactivate;
    private long LeiaTerminate;
    private long HanSoloTerminate;
    private long C3POTerminate;
    private long R2D2Terminate;
    private long LandoTerminate;


    public Diary(){
        totalAttacks = new AtomicInteger();
        }

    public long getHanSoloFinish() {
        return HanSoloFinish;
    }

    public void setHanSoloFinish() {
        HanSoloFinish = System.currentTimeMillis();
    }

    public long getC3POFinish() {
        return C3POFinish;
    }

    public void setC3POFinish() {
        C3POFinish = System.currentTimeMillis();
    }

    public long getR2D2Deactivate() {
        return R2D2Deactivate;
    }

    public void setR2D2Deactivate() {
        R2D2Deactivate = System.currentTimeMillis();
    }

    public long getLeiaTerminate() {
        return LeiaTerminate;
    }

    public void setLeiaTerminate() {
        LeiaTerminate = System.currentTimeMillis();
    }

    public long getHanSoloTerminate() {
        return HanSoloTerminate;

    }

    public void setHanSoloTerminate() {
        HanSoloTerminate = System.currentTimeMillis();
    }

    public long getC3POTerminate() {
        return C3POTerminate;
    }

    public void setC3POTerminate() {
        C3POTerminate = System.currentTimeMillis();
    }

    public long getR2D2Terminate() {
        return R2D2Terminate;
    }

    public void setR2D2Terminate() {
        R2D2Terminate = System.currentTimeMillis();
    }

    public long getLandoTerminate() {
        return LandoTerminate;
    }

    public void setLandoTerminate() {
        LandoTerminate = System.currentTimeMillis();
    }

    public int getTotalAttacks() {
        return totalAttacks.get();
    }

    public void setTotalAttacks() {
        totalAttacks.getAndAdd(1);
    }
}

package bgu.spl.mics;

import bgu.spl.mics.application.passiveObjects.Diary;
/**
 * an output object to store the information to a json file
 * @totalAttacks number of attacks we counted
 * @HanSoloFinish the time Han Solo handled all his attack event he collected
 * @C3POFinish the time C3PO handled all his attack event he collected
 * @R2D2Deactivate the time R2D2 finish his Deactivation-Event
 * @LeiaTerminate the time LeiaThread Terminated
 * @HanSoloTerminate the time HanSoloThread Terminated
 * @C3POTerminate the time C3POThread Terminated
 * @R2D2Terminate the time R2D2Thread Terminated
 * @LandoTerminate the time LandoThread Terminated
 */
public class Output {
    private int totalAttacks;
    private long HanSoloFinish;
    private long C3POFinish;
    private long R2D2Deactivate;
    private long LeiaTerminate;
    private long HanSoloTerminate;
    private long C3POTerminate;
    private long R2D2Terminate;
    private long LandoTerminate;

    public Output(Diary diary){
        totalAttacks=diary.getTotalAttacks();
        HanSoloFinish=diary.getHanSoloFinish();
        C3POFinish= diary.getC3POFinish();
        R2D2Deactivate=diary.getR2D2Deactivate();
        LeiaTerminate=diary.getLeiaTerminate();
        HanSoloTerminate=diary.getHanSoloTerminate();
        C3POTerminate=diary.getC3POTerminate();
        R2D2Terminate=diary.getR2D2Terminate();
        LandoTerminate=diary.getLandoTerminate();
    }

}

package bgu.spl.mics;

import bgu.spl.mics.application.passiveObjects.Diary;

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

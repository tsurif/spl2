package bgu.spl.mics.application.services;

import bgu.spl.mics.Callback;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.BombDestroyerEvent;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.passiveObjects.Diary;

/**
 * LandoMicroservice
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class LandoMicroservice  extends MicroService {

    private final Callback<BombDestroyerEvent> bombCallBack= new Callback<BombDestroyerEvent>() {

        @Override
        public void call(BombDestroyerEvent c) {
            //TODO: complete this
        }
    };

    private final Callback<TerminateBroadcast> terminateCallback=new Callback<TerminateBroadcast>() {
        @Override
        public void call(TerminateBroadcast c) {
            Diary.getInstance().setLandoTerminate();
            terminate();
        }
    };

    public LandoMicroservice(long duration) {
        super("Lando");
    }

    @Override
    protected void initialize() {
       subscribeEvent(BombDestroyerEvent.class,bombCallBack);
       subscribeBroadcast(TerminateBroadcast.class,terminateCallback);
    }
}

package bgu.spl.mics.application.services;

import bgu.spl.mics.Callback;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.BombDestroyerEvent;
import bgu.spl.mics.application.messages.TerminateEvent;

/**
 * LandoMicroservice
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class LandoMicroservice  extends MicroService {

    private Callback<BombDestroyerEvent> bombCallBack= new Callback<BombDestroyerEvent>() {

        @Override
        public void call(BombDestroyerEvent c) {
            //TODO: complete this
        }
    };

    private Callback<TerminateEvent> terminateCallback=new Callback<TerminateEvent>() {
        @Override
        public void call(TerminateEvent c) {
            terminate();
        }
    };

    public LandoMicroservice(long duration) {
        super("Lando");
    }

    @Override
    protected void initialize() {
       subscribeEvent(BombDestroyerEvent.class,bombCallBack);
       subscribeEvent(TerminateEvent.class,terminateCallback);
    }
}

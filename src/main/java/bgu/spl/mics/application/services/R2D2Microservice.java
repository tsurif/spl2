package bgu.spl.mics.application.services;

import bgu.spl.mics.Callback;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.DeactivationEvent;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.passiveObjects.Diary;

/**
 * R2D2Microservices is in charge of the handling {@link DeactivationEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link DeactivationEvent}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class R2D2Microservice extends MicroService {
    private final Callback<DeactivationEvent> deactivateCallBack= new Callback<DeactivationEvent>() {

        @Override
        public void call(DeactivationEvent c) {
            //TODO: complete this
            try {
                Thread.sleep(2000);
                Diary.getInstance().setR2D2Deactivate();
            }catch(InterruptedException e){}
        }
    };

    private final Callback<TerminateBroadcast> terminateCallback=new Callback<TerminateBroadcast>() {
        @Override
        public void call(TerminateBroadcast c) {
            Diary.getInstance().setR2D2Terminate();
            terminate();
        }
    };
    public R2D2Microservice(long duration) {
        super("R2D2");
    }

    @Override
    protected void initialize() {
        subscribeEvent(DeactivationEvent.class,deactivateCallBack);
        subscribeBroadcast(TerminateBroadcast.class,terminateCallback);
    }

}

package bgu.spl.mics.application.services;

import bgu.spl.mics.Callback;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.BombDestroyerEvent;
import bgu.spl.mics.application.messages.DeactivationEvent;

/**
 * R2D2Microservices is in charge of the handling {@link DeactivationEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link DeactivationEvent}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class R2D2Microservice extends MicroService {
    private Callback<DeactivationEvent> deactivateCallBack= new Callback<DeactivationEvent>() {

        @Override
        public void call(DeactivationEvent c) {
            //TODO: complete this
        }
    };
    public R2D2Microservice(long duration) {
        super("R2D2");
    }

    @Override
    protected void initialize() {
        subscribeEvent(DeactivationEvent.class,deactivateCallBack);
    }
}

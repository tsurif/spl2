package bgu.spl.mics.application.services;

import bgu.spl.mics.Callback;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.TerminateEvent;


/**
 * C3POMicroservices is in charge of the handling {@link AttackEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvent}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class C3POMicroservice extends MicroService {

    private Callback<AttackEvent> attackCallBack= new Callback<AttackEvent>() {

        @Override
        public void call(AttackEvent c) {
            //TODO: complete this
        }
    };

    private Callback<TerminateEvent> terminateCallback=new Callback<TerminateEvent>() {
        @Override
        public void call(TerminateEvent c) {
            terminate();
        }
    };
    public C3POMicroservice() {
        super("C3PO");
    }

    @Override
    protected void initialize() {
        subscribeEvent(AttackEvent.class,attackCallBack);
        subscribeEvent(TerminateEvent.class,terminateCallback);
    }
}

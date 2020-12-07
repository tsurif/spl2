package bgu.spl.mics.application.services;


import bgu.spl.mics.Callback;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
//import bgu.spl.mics.application.messages.AccomplishBroadcast;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.passiveObjects.Ewoks;

import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.passiveObjects.Diary;

/**
 * HanSoloMicroservices is in charge of the handling {@link AttackEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvent}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class HanSoloMicroservice extends MicroService {


    private final Callback<AttackEvent> attackCallBack= new Callback<AttackEvent>() {

        @Override
        public void call(AttackEvent event) {
            //TODO: complete this
            System.out.println("Han working on Attack");
            Ewoks.getInstance().acquire(event.attack.getSerials(), name);
            try {
                Thread.sleep(event.attack.getDuration());
            } catch (InterruptedException e) {
            }
            MessageBusImpl.getInstance().complete(event,event.expectedResult);
            Ewoks.getInstance().release(event.attack.getSerials());

            Diary.getInstance().setTotalAttacks();
            Diary.getInstance().setHanSoloFinish();
            //sendBroadcast(new AccomplishBroadcast());
            //TODO add dairy shit


        }
    };


    private final Callback<TerminateBroadcast> terminateCallback=new Callback<TerminateBroadcast>() {
        @Override
        public void call(TerminateBroadcast c) {
            Diary.getInstance().setHanSoloTerminate();
            terminate();
        }
    };

    public HanSoloMicroservice() {
        super("Han");
    }


    @Override
    protected void initialize() {
        System.out.println("Han start initialize");
        subscribeEvent(AttackEvent.class,attackCallBack);
        subscribeBroadcast(TerminateBroadcast.class,terminateCallback);
        System.out.println("Han end initialize");
    }
}

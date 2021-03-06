package bgu.spl.mics.application.services;

import bgu.spl.mics.Callback;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.BombDestroyerEvent;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.passiveObjects.Diary;
import sun.awt.windows.ThemeReader;

/**
 * LandoMicroservice
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class LandoMicroservice  extends MicroService {
    private long sleepDuration;
    private final Callback<BombDestroyerEvent> bombCallBack= new Callback<BombDestroyerEvent>() {
        /**
         * Lando send a bomb destroyer(sleep) and then resolve the bomb destroyer event
         * @param event
         */
        @Override
        public void call(BombDestroyerEvent event) {
            try {
                Thread.sleep(sleepDuration);
                MessageBusImpl.getInstance().complete(event,event.expectedResult);
                sendBroadcast(new TerminateBroadcast());
            }catch (InterruptedException e){}
        }
    };

    private final Callback<TerminateBroadcast> terminateCallback=new Callback<TerminateBroadcast>() {
        /**
         * the microservice commit termination
         * @param c
         */
        @Override
        public void call(TerminateBroadcast c) {
            Diary.getInstance().setLandoTerminate();
            terminate();
        }
    };

    public LandoMicroservice(long duration) {
        super("Lando");
        sleepDuration = duration;
    }

    @Override
    protected void initialize() {
       //System.out.println("Lando start initialize");
       subscribeEvent(BombDestroyerEvent.class,bombCallBack);
       subscribeBroadcast(TerminateBroadcast.class,terminateCallback);
    }
}

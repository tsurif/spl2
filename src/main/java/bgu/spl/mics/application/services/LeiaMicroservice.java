package bgu.spl.mics.application.services;

import bgu.spl.mics.Callback;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AccomplishBroadcast;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.DeactivationEvent;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.application.passiveObjects.Diary;

/**
 * LeiaMicroservices Initialized with Attack objects, and sends them as  {@link AttackEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvent}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */

public class LeiaMicroservice extends MicroService {
	private Attack[] attacks;
	private int accomplishCount;

    private final Callback<TerminateBroadcast> terminateCallback=new Callback<TerminateBroadcast>() {
        @Override
        public void call(TerminateBroadcast c) {
            Diary.getInstance().setLeiaTerminate();
            terminate();
        }
    };

    private final Callback<AccomplishBroadcast> accomplishCallback=new Callback<AccomplishBroadcast>() {
        @Override
        public void call(AccomplishBroadcast c) {
            accomplishCount++;
            if(accomplishCount == attacks.length) sendEvent(new DeactivationEvent());
        }
    };
    public LeiaMicroservice(Attack[] attacks) {
        super("Leia");
		this.attacks = attacks;
        for (Attack a: attacks) {
            a.sort();//TODO change the logic to avoid this methods
        }
		accomplishCount = 0;


    }

    @Override
    protected void initialize() {
        System.out.println("Leia start initialize");
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){}
        subscribeBroadcast(TerminateBroadcast.class,terminateCallback);
        subscribeBroadcast(AccomplishBroadcast.class,accomplishCallback);
        for (Attack obj:attacks) {
            System.out.println("Leia send attack");
            sendAttackEvent(obj);
        }
    }

    public void sendAttackEvent(Attack attack){
        AttackEvent ae=new AttackEvent(attack);
        sendEvent(ae);
    }
}

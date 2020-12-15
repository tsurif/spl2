package bgu.spl.mics.application.services;

import bgu.spl.mics.Callback;

import bgu.spl.mics.Future;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.*;
import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.application.passiveObjects.Diary;

import java.util.LinkedList;
import java.util.List;

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
	private List<Future> attackFutures;
	private Future<Boolean> r2d2Future;

    private final Callback<TerminateBroadcast> terminateCallback =new Callback<TerminateBroadcast>() {
        @Override
        public void call(TerminateBroadcast c) {
            Diary.getInstance().setLeiaTerminate();
            terminate();
        }
    };

//    private final Callback<AccomplishBroadcast> accomplishCallback=new Callback<AccomplishBroadcast>() {
//        @Override
//        public void call(AccomplishBroadcast c) {
//            accomplishCount++;
//            if(accomplishCount == attacks.length) {
//                r2d2Future = sendEvent(new DeactivationEvent());
//                if (r2d2Future.get()) {
//                    sendEvent(new BombDestroyerEvent());
//                }
//            }
//
//        }
//    };
    public LeiaMicroservice(Attack[] attacks) {
        super("Leia");

		this.attacks = attacks;
        for (Attack a: attacks) {
            a.sort();
        }
        attackFutures = new LinkedList<>();
		accomplishCount = 0;


    }

    @Override
    protected void initialize() {
//        System.out.println("Leia start initialize");
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){}
        subscribeBroadcast(TerminateBroadcast.class,terminateCallback);
        //subscribeBroadcast(AccomplishBroadcast.class,accomplishCallback);
        for (Attack att:attacks) {
//            System.out.println(name + " send attack");
            sendAttackEvent(att);
        }

        while(!attackFutures.isEmpty()){
            attackFutures.remove(0).get();
//            System.out.println("-------------------------------------attack sucsses--------------------------------------");
        }
        r2d2Future = sendEvent(new DeactivationEvent());
        if (r2d2Future.get()) {
            sendEvent(new BombDestroyerEvent());
        }
    }

    public void sendAttackEvent(Attack attack){
        AttackEvent attackEvent=new AttackEvent(attack);
        Future f = sendEvent(attackEvent);
        if(f != null) {
            attackFutures.add(f);
        }

    }
}

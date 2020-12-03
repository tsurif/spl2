package bgu.spl.mics.application.messages;
import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.Attack;

import java.util.List;

public class AttackEvent implements Event<Boolean> {
	public boolean expectedResult;
	public List<Integer> ewoks;

	public AttackEvent(List<Integer> ewoks){
	    expectedResult=true;
	    this.ewoks=ewoks;

    }

}

package bgu.spl.mics.application.messages;
import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.Attack;


import java.util.List;

public class AttackEvent implements Event<Boolean> {
	public boolean expectedResult;
	public Attack attack;
	public AttackEvent(Attack attack){
	    expectedResult=true;
		this.attack=attack;
    }

}

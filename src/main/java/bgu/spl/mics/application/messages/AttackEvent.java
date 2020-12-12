package bgu.spl.mics.application.messages;
import bgu.spl.mics.Event;
import bgu.spl.mics.Future;
import bgu.spl.mics.application.passiveObjects.Attack;


import java.util.List;

/**
 * Data object representing an Attack-Event sent by Leia
 * @expectedResult the result we will resolve to
 * @attack the Attack passive-object we maintain
 */
public class AttackEvent implements Event<Boolean> {
	public boolean expectedResult;
	public Attack attack;
	/**
	 * Constructor
	 * @param attack
	 */
	public AttackEvent(Attack attack){
	    expectedResult=true;
		this.attack=attack;

    }

}

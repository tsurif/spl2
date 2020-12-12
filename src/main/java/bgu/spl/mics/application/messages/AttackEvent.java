package bgu.spl.mics.application.messages;
import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.Attack;


import java.util.List;

/**
 * Data object representing an attack which sent by Leia
 * @expectedResult the result which should be resolved to
 * @attack the passive-object we maintain
 */
public class AttackEvent implements Event<Boolean> {
	public final boolean expectedResult=true;
	public Attack attack;

	/**
	 * Constructor
	 * @param attack
	 */
	public AttackEvent(Attack attack){
		this.attack=attack;
    }

}

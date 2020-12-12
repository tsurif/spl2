package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.Future;

/**
 * Data object representing an Deactivation-Event sent by Leia
 * @expectedResult the result we will resolve to
 */
public class DeactivationEvent implements Event<Boolean> {
    public boolean expectedResult;
    public DeactivationEvent(){
        expectedResult=true;
    }

}

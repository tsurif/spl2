package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.Message;


/**
 * Implement Event, this Event is for R2D2 use only.
 * Once all the futures of the Attack-Events that leia sent have resolved,
 * leia send this event.
 * @expectedResult the result this Event will have once resolved
 */
public class BombDestroyerEvent implements Event<Boolean> {
    public boolean expectedResult;
    public BombDestroyerEvent(){
        expectedResult=true;
    }

}

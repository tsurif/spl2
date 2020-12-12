package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.Future;

public class BombDestroyerEvent implements Event<Boolean> {
    public boolean expectedResult;
    public BombDestroyerEvent(){
        expectedResult=true;
    }

}

package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;

public class BombDestroyerEvent implements Event<Boolean> {
    public boolean expectedResult;
    public BombDestroyerEvent(){
        expectedResult=true;
    }

}

package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
/**
 * Data object representing an BombDestroyer event which sent by Leia
 * @expectedResult the result which should be resolved to
 */
public class BombDestroyerEvent implements Event<Boolean> {
    public final boolean expectedResult=true;

}

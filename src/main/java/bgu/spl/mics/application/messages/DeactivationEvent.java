package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
/**
 * Data object representing an Deactivation order which sent by Leia
 * @expectedResult the result which should be resloved to
 */
public class DeactivationEvent implements Event<Boolean> {
    public final boolean expectedResult=true;
}

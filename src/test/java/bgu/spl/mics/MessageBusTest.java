package bgu.spl.mics;

import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.messages.MockEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageBusTest {

    private MessageBusImpl messageBus;

    private CallbackMock<BroadCastMock> callbackBroadCast;
    private CallbackMock<AttackEvent> callBackEvent;

    private MicroServiceMock ms1;
    private MicroServiceMock ms2;

    private AttackEvent attackEvent;
    private BroadCastMock broadCast;

    @BeforeEach
    void setUp(){
        messageBus=MessageBusImpl.getInstance();
        callbackBroadCast =new CallbackMock<BroadCastMock>();
        callBackEvent=new CallbackMock<AttackEvent>();

        ms1=new MicroServiceMock("Test1");
        ms2=new MicroServiceMock("Test2");

        attackEvent = new AttackEvent(new Attack(null,50));
        broadCast = new BroadCastMock();
    }

    @AfterEach
    void TearDown(){
        messageBus.unregister(ms1);
        messageBus.unregister(ms2);
        callbackBroadCast.isCalled=false;
        callBackEvent.isCalled=false;
    }
    @Test
    void subscribeEvent() { //check if the sendEvent dont return null after we add 1 subscriber
        ms1.subscribeEvent(AttackEvent.class,callBackEvent);
        assertNotNull(messageBus.sendEvent(attackEvent));
    }

    @Test
    void subscribeEvent_Different_Type_Null(){ //check if we call a different event we get null from the sendEvent because the microService subscribed to another event
        MockEvent mockEvent=new MockEvent();

        ms1.subscribeEvent(AttackEvent.class,callBackEvent);
        assertNull(messageBus.sendEvent(mockEvent));
    }

    @Test
    void subscribeBroadcast() { //check if the callback was called by the microService after it was registered
        ms1.subscribeBroadcast(BroadCastMock.class, callbackBroadCast);
        messageBus.sendBroadcast(broadCast);
        try{
            Message msg=messageBus.awaitMessage(ms1);
            assertNotNull(msg);
        }catch (InterruptedException ex){

        }
    }

    @Test
    void sendBroadcast_2_Subscribers() { //send broadcast to more then 1 microservice subscribed to, check if all got the message
        CallbackMock callbackBroadCast2 =new CallbackMock<BroadCastMock>();

        ms1.subscribeBroadcast(BroadCastMock.class, callbackBroadCast);
        ms2.subscribeBroadcast(BroadCastMock.class, callbackBroadCast2);

        messageBus.sendBroadcast(broadCast);
        try{
            Message msg1=messageBus.awaitMessage(ms1);
            Message msg2=messageBus.awaitMessage(ms2);
            assertNotNull(msg1);
            assertNotNull(msg2);
        }catch (InterruptedException ex){

        }
    }

    @Test
    void SendBroadcast_2_Subscribers_Not_Called_The_Wrong_One(){ //subscribe 2 differents microservices to different broadcast and check if the one shouldn't get it did not get it
        CallbackMock<BroadCastMock2> cb=new CallbackMock<>();

        ms1.subscribeBroadcast(BroadCastMock.class, callbackBroadCast);
        ms2.subscribeBroadcast(BroadCastMock2.class, cb);

        messageBus.sendBroadcast(broadCast);
        assertFalse(cb.isCalled);
    }

    @Test
    void sendEvent_Null() { //check that the sendEvent return null if there are no subscribers
        assertNull(messageBus.sendEvent(attackEvent));
    }

    @Test
    void sendEvent() { //check if the callBack was called after the sendEvent
        ms1.subscribeEvent(AttackEvent.class,callBackEvent);
        messageBus.sendEvent(attackEvent);
        try{
            Message msg=messageBus.awaitMessage(ms1);
            assertNotNull(msg);
        }catch (InterruptedException ex){}
    }

    @Test
    void sendEvent_Different_Type(){ //check if we call a different event which the microService not subscribed, we dont run its callback
        MockEvent mockEvent=new MockEvent();

        ms1.subscribeEvent(AttackEvent.class,callBackEvent);
        messageBus.sendEvent(mockEvent);

        assertFalse(callBackEvent.isCalled);
    }

    @Test
    void complete() { //TODO not sure 100% its the correct logic
        ms1.subscribeEvent(AttackEvent.class,callBackEvent);
        Future<Boolean> future=messageBus.sendEvent(attackEvent);
        ms1.complete(attackEvent,true);

        assertTrue(future.isDone());
    }

    @Test
    void unregister_From_Event() { //check if we unregister microservice from event
        ms1.subscribeEvent(AttackEvent.class,callBackEvent);
        messageBus.unregister(ms1);
        messageBus.sendEvent(attackEvent);

        assertFalse(callBackEvent.isCalled);
    }
    @Test
    void unregister_From_Event_Null() { //check if we unregister microservice from event, his callback does not activate
        ms1.subscribeEvent(AttackEvent.class,callBackEvent);
        messageBus.unregister(ms1);

        assertNull(messageBus.sendEvent(attackEvent));
    }
    @Test
    void unregister_2_subscribers_NotNull() { //check if we subscribed 2 microservices, and  unregister 1 the queue is not empty
        CallbackMock<AttackEvent> cb =new CallbackMock<>();

        ms1.subscribeEvent(AttackEvent.class,callBackEvent);
        ms2.subscribeEvent(AttackEvent.class,cb);

        messageBus.unregister(ms1);

        assertNotNull(messageBus.sendEvent(attackEvent));
    }
    @Test
    void unregister_From_BroadCast() { //check if we unregister microservice from broadcast, his callback does not activate
        ms1.subscribeBroadcast(BroadCastMock.class, callbackBroadCast);
        messageBus.unregister(ms1);
        try {
            messageBus.sendBroadcast(broadCast);
        }catch(NullPointerException ex){
            assertTrue(true);
        }

    }
    @Test
    void awaitMessage(){
        ms1.subscribeEvent(AttackEvent.class,callBackEvent);
        messageBus.sendEvent(attackEvent);

        try {
            Message msg=messageBus.awaitMessage(ms1);
            assertEquals(attackEvent,msg);
        }
        catch (InterruptedException e) {
            assertTrue(true);
        }

    }
}
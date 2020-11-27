package bgu.spl.mics;

import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.messages.MockEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageBusTest {

    private MessageBusImpl messageBus;

    private MicroServiceMock microServiceMock;
    private CallbackMock<BroadCastMock> callbackBroadCast;
    private CallbackMock<AttackEvent> callBackEvent;

    @BeforeEach
    void setUp(){
        messageBus=new MessageBusImpl();
        callbackBroadCast =new CallbackMock<BroadCastMock>();
        callBackEvent=new CallbackMock<AttackEvent>();
    }

    @Test
    void subscribeEvent() { //check if the sendEvent not null after we add 1 subscriber
        AttackEvent attackEvent=new AttackEvent();
        MicroService ms=new MicroServiceMock("Test");
        messageBus.register(ms);
        ms.subscribeEvent(AttackEvent.class,callBackEvent);
        assertNotNull(messageBus.sendEvent(attackEvent));
    }

    @Test
    void subscribeEvent_Different_Type_Null(){ //check if we call a different event we get return null from the sendEvent because the microService subscribed to another event
        AttackEvent attackEvent=new AttackEvent();
        MicroService ms=new MicroServiceMock("Test");
        messageBus.register(ms);
        ms.subscribeEvent(AttackEvent.class,callBackEvent);
        MockEvent mockEvent=new MockEvent();
        assertNull(messageBus.sendEvent(mockEvent));
    }

    @Test
    void subscribeBroadcast() { //check if the callback was called by the microService after it was registerd
        BroadCastMock broadCast = new BroadCastMock();
        MicroService ms=new MicroServiceMock("Test");
        ms.subscribeBroadcast(BroadCastMock.class, callbackBroadCast);
        messageBus.sendBroadcast(broadCast);
        assertTrue(callbackBroadCast.isCalled);
    }

    @Test
    void sendBroadcast_2_Subscribers() { //send broadcast to more then 1 microservice subscribed to, check if all got the message
        BroadCastMock broadCast = new BroadCastMock();
        CallbackMock callbackBroadCast2 =new CallbackMock<BroadCastMock>();
        MicroService ms1=new MicroServiceMock("Test1");
        MicroService ms2=new MicroServiceMock("Test2");
        ms1.subscribeBroadcast(BroadCastMock.class, callbackBroadCast);
        ms2.subscribeBroadcast(BroadCastMock.class, callbackBroadCast2);
        messageBus.sendBroadcast(broadCast);
        assertTrue(callbackBroadCast.isCalled && callbackBroadCast2.isCalled);
    }

    @Test
    void SendBroadcast_2_Subscribers_Not_Called_The_Wrong_One(){ //subscribe 2 differents microservices to different broadcast and check if the one shouldn't get it did not get it
        BroadCastMock broadCast = new BroadCastMock();
        CallbackMock<BroadCastMock2> cb=new CallbackMock<>();
        MicroService ms1=new MicroServiceMock("Test1");
        MicroService ms2=new MicroServiceMock("Test2");
        ms1.subscribeBroadcast(BroadCastMock.class, callbackBroadCast);
        ms2.subscribeBroadcast(BroadCastMock2.class, cb);
        messageBus.sendBroadcast(broadCast);
        assertFalse(cb.isCalled);
    }

    @Test
    void sendEvent_Null() { //check that the sendEvent return null if there are no subscribers
        AttackEvent attackEvent=new AttackEvent();
        assertNull(messageBus.sendEvent(attackEvent));
    }

    @Test
    void sendEvent() { //check if the callBack was called after the sendEvent
        AttackEvent attackEvent=new AttackEvent();
        MicroService ms=new MicroServiceMock("Test");
        messageBus.register(ms);
        ms.subscribeEvent(AttackEvent.class,callBackEvent);
        messageBus.sendEvent(attackEvent);
        assertTrue(callBackEvent.isCalled);
    }

    @Test
    void sendEvent_Different_Type(){ //check if we call a different event which the microService not subscribed, we dont run its callback
        AttackEvent attackEvent=new AttackEvent();
        MicroService ms=new MicroServiceMock("Test");
        messageBus.register(ms);
        ms.subscribeEvent(AttackEvent.class,callBackEvent);
        MockEvent mockEvent=new MockEvent();
        messageBus.sendEvent(mockEvent);
        assertFalse(callBackEvent.isCalled);
    }

    @Test
    void complete() { //TODO not sure 100% its the correct logic
        AttackEvent attackEvent=new AttackEvent();
        MicroService ms=new MicroServiceMock("Test");
        ms.subscribeEvent(AttackEvent.class,callBackEvent);
        Future<Boolean> future=messageBus.sendEvent(attackEvent);
        ms.complete(attackEvent,true);
        assertTrue(future.isDone());
    }

    @Test
    void unregister_From_Event() { //check if we unregister microservice from event
        AttackEvent attackEvent=new AttackEvent();
        MicroService ms=new MicroServiceMock("Test");
        ms.subscribeEvent(AttackEvent.class,callBackEvent);
        messageBus.unregister(ms);
        messageBus.sendEvent(attackEvent);
        assertFalse(callBackEvent.isCalled);
    }
    @Test
    void unregister_From_Event_Null() { //check if we unregister microservice from event, his callback does not activate
        AttackEvent attackEvent=new AttackEvent();
        MicroService ms=new MicroServiceMock("Test");
        ms.subscribeEvent(AttackEvent.class,callBackEvent);
        messageBus.unregister(ms);
        assertNull(messageBus.sendEvent(attackEvent));
    }
    @Test
    void unregister_2_subscribers_NotNull() { //check if we subscribed 2 microservices, and  unregister 1 the queue is not empty
        AttackEvent attackEvent=new AttackEvent();
        CallbackMock cb =new CallbackMock<AttackEvent>();
        MicroService ms1=new MicroServiceMock("Test1");
        MicroService ms2=new MicroServiceMock("Test2");
        ms1.subscribeEvent(AttackEvent.class,callBackEvent);
        ms2.subscribeEvent(AttackEvent.class,cb);
        messageBus.unregister(ms1);
        assertNotNull(messageBus.sendEvent(attackEvent));
    }
    @Test
    void unregister_From_BroadCast() { //check if we unregister microservice from broadcast, his callback does not activate
        BroadCastMock broadCast = new BroadCastMock();
        MicroService ms=new MicroServiceMock("Test");
        ms.subscribeBroadcast(BroadCastMock.class, callbackBroadCast);
        messageBus.unregister(ms);
        messageBus.sendBroadcast(broadCast);
        assertFalse(callbackBroadCast.isCalled);
    }
}
package bgu.spl.mics;

import bgu.spl.mics.application.messages.AttackEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class MessageBusTest {

    private MessageBusImpl messageBus;

    private MicroServiceMock microServiceMock;
    private Callback<String> callback;

    @BeforeEach
    void setUp(){
        messageBus=new MessageBusImpl();

        callback=new Callback<String>() {
            @Override
            public void call(String c) {
                System.out.print("I am your father!");
            }
        };

    }

    @Test
    void subscribeEvent() {
        AttackEvent attackEvent=new AttackEvent();
        final MicroService ms=new MicroServiceMock("Test");
        messageBus.subscribeEvent(AttackEvent.class,ms);
        messageBus.sendEvent(attackEvent);


    }

    @Test
    void subscribeBroadcast() {
        MessageBroadCastMock broadCast = new MessageBroadCastMock();
        final MicroService ms=new MicroServiceMock("Test");
        messageBus.subscribeBroadcast(MessageBroadCastMock.class, ms);
        messageBus.sendBroadcast(broadCast);
    }

    @Test
    void complete() {
        AttackEvent attackEvent=new AttackEvent();
        messageBus.complete(attackEvent,true);


    }

    @Test
    void sendBroadcast() {

    }

    @Test
    void sendEventNull() {
        MessageEventMock<String> mem=new MessageEventMock<>();
        assertEquals(null, messageBus.sendEvent(mem));
    }
    @Test
    void sendEvent() { //TODO
        AttackEvent attackEvent=new AttackEvent();
        final MicroService ms=new MicroServiceMock("Test");
        messageBus.subscribeEvent(AttackEvent.class,ms);
        MessageEventMock<String> mem=new MessageEventMock<>();
        assertEquals(null, messageBus.sendEvent(mem));
    }
    @Test
    void register() {

    }

    @Test
    void unregister() {

    }

    @Test
    void awaitMessage() {

    }
}
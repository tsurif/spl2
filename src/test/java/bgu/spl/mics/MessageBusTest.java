package bgu.spl.mics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageBusTest {

    private MessageBusImpl messageBus;
    private MessageEventMock<String> messageEventMock;
    private MessageBroadCastMock messageBroadCastMock;


    @BeforeEach
    void setUp(){
        messageBus=new MessageBusImpl();
        messageEventMock=new MessageEventMock<>();
        messageBroadCastMock=new MessageBroadCastMock();

    }

    @Test
    void subscribeEvent() {

    }

    @Test
    void subscribeBroadcast() {

    }

    @Test
    void complete() {

    }

    @Test
    void sendBroadcast() {

    }

    @Test
    void sendEvent() {

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
package bgu.spl.mics;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

/**
 * this is an Object that helps to manege the MessageBusImpl.
 * the MessageBusImpl use this obj to manage the message passing between the Bus users.
 * for every message type MessageBusImpl will use an postOffice instance.
 * this is synchronized obj so every call for specific type of message will sync the messageBusImpl only for that type.
 *
 */
public class PostOffice {
    private Queue<BlockingQueue<Message>> mailBoxes;

    public PostOffice(){
        mailBoxes = new LinkedList<>();
    }

    /**
     * @return the number of MicroService register for the message-type this PostOffice store.
     */
    public synchronized int size(){
        return mailBoxes.size();
    }

    /**
     *
     * @return true if and only if no one is registered for the message-type this PostOffice store.
     */
    public synchronized boolean isEmpty(){
        return mailBoxes.isEmpty();
    }


    /**
     * remove the queue of a microService.
     * use when this microService unregistered
     * @param subscriber a queue of a MicroService to remove
     */
    public synchronized void remove(BlockingQueue<Message> subscriber){
       mailBoxes.remove(subscriber);
    }

    /**
     * add the queue of a microService.
     * use when this microService registered
     * @param subscriber a queue of a MicroService to add
     */
    public synchronized void add(BlockingQueue<Message> subscriber){
        mailBoxes.add(subscriber);
    }

    /**
     * send a Broadcast to all the MicroService that registered to this PostOffice.
     * @param b Broadcast to send.
     */

    public synchronized void sendBroadcast(Broadcast b){
        for (BlockingQueue<Message> mailBox:mailBoxes) {
            mailBox.add(b);
        }
    }


    /**
     * send an event to the first microService in the queue,
     * the send it to the back
     * supporting the round-robbin manner.
     * @param e an event to pass to a microService.
     */
    public synchronized void sendEvent(Event e){

        BlockingQueue<Message> msQueue = mailBoxes.remove();
        mailBoxes.add(msQueue);
        msQueue.add(e);
    }
}

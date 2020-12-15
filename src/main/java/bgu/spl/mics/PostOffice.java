package bgu.spl.mics;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 *
 */
public class PostOffice {

    private Queue<BlockingQueue<Message>> mailBoxes;

    /**
     *
     */
    public PostOffice(){
        mailBoxes = new LinkedList<>();
    }

    public synchronized int size(){
        return mailBoxes.size();
    }

    /**
     *
     * @return
     */
    public synchronized boolean isEmpty(){
        return mailBoxes.isEmpty();
    }

    /**
     *
     * @param q
     */
    public synchronized void remove(BlockingQueue<Message> q){
        mailBoxes.remove(q);
    }

    /**
     *
     * @param subscriber
     */
    public synchronized void add(BlockingQueue<Message> subscriber){
        mailBoxes.add(subscriber);

    }

    /**
     *
     * @param b
     */
    public synchronized void sendBroadcast(Broadcast b){
        for (BlockingQueue<Message> mailBox:mailBoxes) {
            mailBox.add(b);
        }
    }

    /**
     *
     * @param e
     */
    public synchronized void sendEvent(Event e){
        BlockingQueue<Message> msQueue = mailBoxes.remove();
        mailBoxes.add(msQueue);
        msQueue.add(e);
    }
}

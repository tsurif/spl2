package bgu.spl.mics;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class PostOffice {
    //private Queue<BlockingQueue<Message>> mailBoxes;
    private LinkedList<BlockingQueue<Message>> mailBoxes;
    private AtomicInteger counter;
    private AtomicInteger size;
    public PostOffice(){
        mailBoxes = new LinkedList<>();
        counter=new AtomicInteger();
        size=new AtomicInteger();
        counter.set(0);
        size.set(0);
    }

    public synchronized int size(){
        return mailBoxes.size();
    }
    public synchronized boolean isEmpty(){
        return mailBoxes.isEmpty();
    }

    public synchronized void remove(BlockingQueue<Message> q){
        if (mailBoxes.contains(q)) {
            mailBoxes.remove(q);
            size.getAndDecrement();
        }
    }

    public synchronized void add(BlockingQueue<Message> subscriber){
        mailBoxes.add(subscriber);
        size.getAndIncrement();

    }
    public synchronized void sendBrodcast(Broadcast b){
        for (BlockingQueue<Message> mailBox:mailBoxes) {
            mailBox.add(b);
        }
    }

    public synchronized void sendEvant(Event e){
        counter.compareAndSet(size.get(),0);
        int i=counter.getAndSet((counter.get()+1)% size.get());
        BlockingQueue<Message>msQueue=mailBoxes.get(i);
        msQueue.add(e);
//        BlockingQueue<Message> msQueue = mailBoxes.remove();
//        mailBoxes.add(msQueue);
//        msQueue.add(e);
    }
}

package bgu.spl.mics;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

public class PostOffice {
    private Queue<BlockingQueue<Message>> mailBoxes;

    public PostOffice(){
        mailBoxes = new LinkedList<>();
    }

    public synchronized int size(){
        return mailBoxes.size();
    }
    public synchronized boolean isEmpty(){
        return mailBoxes.isEmpty();
    }

    public synchronized void remove(BlockingQueue<Message> q){
        if (mailBoxes.contains(q)) mailBoxes.remove(q);
    }

    public synchronized void add(BlockingQueue<Message> subscriber){
        mailBoxes.add(subscriber);
    }
    public synchronized void sendBrodcast(Broadcast b){
        for (BlockingQueue<Message> mailBox:mailBoxes) {
            mailBox.add(b);
        }
    }

    public synchronized void sendEvant(Event e){
        BlockingQueue<Message> msQueue = mailBoxes.remove();
        mailBoxes.add(msQueue);
        msQueue.add(e);
    }
}

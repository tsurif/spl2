package bgu.spl.mics;



import bgu.spl.mics.application.messages.AttackEvent;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {

//TODO implement the round robin demand
	private HashMap<Class<? extends Message>, Queue<BlockingQueue<Message>>> messageTypeHash;
	private HashMap<MicroService, BlockingQueue<Message>> registeredHash;
	private HashMap<Event,Future> futureHashMap;

	private final Object messageTypeHashLocker;

	private static class MessageBusHolder{
		private static MessageBusImpl instance = new MessageBusImpl();
	}
	private MessageBusImpl(){
		messageTypeHash=new HashMap<>();
		registeredHash=new HashMap<>();
		futureHashMap=new HashMap<>();

		messageTypeHashLocker = new Object();
	}
	public static MessageBusImpl getInstance(){
		return MessageBusHolder.instance;
	}


	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {//TODO allow to work on this together IF the thread not working on the same type
		synchronized (messageTypeHashLocker) {
			if (!messageTypeHash.containsKey(type)) {
				messageTypeHash.put(type, new LinkedList<>());
			}
		}
			messageTypeHash.get(type).add(registeredHash.get(m));

	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		synchronized (messageTypeHashLocker) {
			if (!messageTypeHash.containsKey(type)) {
				messageTypeHash.put(type, new LinkedList<>());
			}
			//messageTypeHash.get(type).add(registeredHash.get(m));
		}
			messageTypeHash.get(type).add(registeredHash.get(m));
	}


	@Override
	public <T> void complete(Event<T> e, T result) {
		futureHashMap.get(e).resolve(result);
	}

	@Override
	public void sendBroadcast(Broadcast b) {
		if(!messageTypeHash.containsKey(b.getClass())){
			throw new IllegalArgumentException("No one subscribed to this event"); //throw error - no one subscribe to this broadcast
		}
		Queue<BlockingQueue<Message>> subscribersQueue=messageTypeHash.get(b.getClass());
		for (BlockingQueue<Message> elem:subscribersQueue) {
			elem.add(b);
		}
	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		if(!messageTypeHash.containsKey(e.getClass())|| messageTypeHash.get(e.getClass()).size()==0)
        return null;
		Queue<BlockingQueue<Message>> subscribersQueue = messageTypeHash.get(e.getClass());
		BlockingQueue<Message> msQueue = subscribersQueue.remove();
		subscribersQueue.add(msQueue);
		msQueue.add(e);
		Future<T> future = new Future<>();
		futureHashMap.put(e,future);
		return future;
		//TODO who using this future for the love of god
	}

	@Override
	public void register(MicroService m) {
		System.out.println(m.name + " is register");
		registeredHash.put(m,new LinkedBlockingQueue<>());//TODO ??change that to something thread - safety???
		System.out.println(m.name + " done register");
	}

	@Override
	public void unregister(MicroService m) {
		if(registeredHash.containsKey(m)) {
			BlockingQueue<Message> mQueueRemoved = registeredHash.remove(m);
			messageTypeHash.forEach((type_Key, QOfBlockingQ) -> QOfBlockingQ.removeIf(q->q.equals(mQueueRemoved)));
		}

	}


	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
		BlockingQueue<Message> msQueue = registeredHash.get(m);
		return msQueue.take();
	}

//	public void restart(){
//		messageTypeHash.clear();
//		registeredHash.clear();
//		futureHashMap.clear();
//
//	}
}

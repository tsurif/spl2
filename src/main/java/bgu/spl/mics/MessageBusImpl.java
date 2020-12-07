package bgu.spl.mics;



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
	private final Object registeredHashLocker;
	private final Object futureHashLocker;

	private static class MessageBusHolder{
		private static MessageBusImpl instance = new MessageBusImpl();
	}
	private MessageBusImpl(){
		messageTypeHash=new HashMap<>();
		registeredHash=new HashMap<>();
		futureHashMap=new HashMap<>();

		messageTypeHashLocker = new Object();
		registeredHashLocker = new Object();
		futureHashLocker = new Object();
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
			messageTypeHash.get(type).add(registeredHash.get(m));
		}

	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		synchronized (messageTypeHashLocker) {
			if (!messageTypeHash.containsKey(type)) {
				messageTypeHash.put(type, new LinkedList<>());
			}
			messageTypeHash.get(type).add(registeredHash.get(m));
		}
	}


	@Override
	public <T> void complete(Event<T> e, T result) {
		futureHashMap.get(e).resolve(result);
	}

	@Override
	public void sendBroadcast(Broadcast b) {
		synchronized (messageTypeHashLocker){
		if(!messageTypeHash.containsKey(b.getClass())){
			throw new IllegalArgumentException("No one subscribed to this event"); //throw error - no one subscribe to this broadcast
		}
		Queue<BlockingQueue<Message>> subscribersQueue=messageTypeHash.get(b.getClass());
		for (BlockingQueue<Message> elem:subscribersQueue) {
			elem.add(b);
		}
		}
	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		synchronized (messageTypeHashLocker) {
			if (!messageTypeHash.containsKey(e.getClass()) ||
					messageTypeHash.get(e.getClass()).size() == 0)
				return null;
			Future<T> future = new Future<>();
			Queue<BlockingQueue<Message>> subscribersQueue = messageTypeHash.get(e.getClass());
			BlockingQueue<Message> msQueue = subscribersQueue.remove();
			subscribersQueue.add(msQueue);
			msQueue.add(e);
			synchronized (futureHashLocker) {
				futureHashMap.put(e, future);
				return future;
			}
		}
		//TODO who using this future for the love of god
	}

	@Override
	public void register(MicroService m) {
		synchronized (registeredHashLocker) {
			registeredHash.put(m, new LinkedBlockingQueue<>());
		}
	}

	@Override
	public void unregister(MicroService m) {
		synchronized (registeredHashLocker) {
			if (registeredHash.containsKey(m)) {
				BlockingQueue<Message> mQueueRemoved = registeredHash.remove(m);

				synchronized (messageTypeHashLocker) {
					messageTypeHash.forEach((k, v) -> {
						v.removeIf(q -> q == mQueueRemoved);
					});
				}
			}
		}
	}


	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
		BlockingQueue<Message> msQueue = registeredHash.get(m);
		return msQueue.take();
	}
}

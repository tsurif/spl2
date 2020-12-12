package bgu.spl.mics;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;



/**
 * The {@link bgu.spl.mics.MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {

	private ConcurrentHashMap<Class<? extends Message>, PostOffice> messageTypeHash;
	private ConcurrentHashMap<MicroService, BlockingQueue<Message>> registeredHash;
	private ConcurrentHashMap<Event,Future> futureHashMap;

	private final Object messageTypeHashLocker;
	private final Object registeredHashLocker;
	private final Object futureHashLocker;

	private static class MessageBusHolder{
		private static bgu.spl.mics.MessageBusImpl instance = new bgu.spl.mics.MessageBusImpl();
	}
	private MessageBusImpl(){
		messageTypeHash=new ConcurrentHashMap<>();
		registeredHash=new ConcurrentHashMap<>();
		futureHashMap=new ConcurrentHashMap<>();

		messageTypeHashLocker = new Object();
		registeredHashLocker = new Object();
		futureHashLocker = new Object();

	}
	public static bgu.spl.mics.MessageBusImpl getInstance(){
		return bgu.spl.mics.MessageBusImpl.MessageBusHolder.instance;
	}

	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {//TODO allow to work on this together IF the thread not working on the same type
		synchronized (messageTypeHashLocker) {
			if (!messageTypeHash.containsKey(type)) {
				messageTypeHash.put(type, new PostOffice());
			}
			messageTypeHash.get(type).add(registeredHash.get(m));
		}
	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		synchronized (messageTypeHashLocker) {
			if (!messageTypeHash.containsKey(type)) {
				messageTypeHash.put(type, new PostOffice());
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
		PostOffice postOffice = messageTypeHash.get(b.getClass());
		if (postOffice == null) throw new IllegalArgumentException("No one subscribed to this event");
		postOffice.sendBrodcast(b);
	}

	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		if (!messageTypeHash.containsKey(e.getClass()) ||
				messageTypeHash.get(e.getClass()).size() == 0) {
			return null;
		}
		Future<T> future;
//		synchronized (futureHashLocker) {
			future = new Future<>();
			futureHashMap.put(e, future);
//		}
		messageTypeHash.get(e.getClass()).sendEvant(e);
		return future;
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
				LinkedList<Class<? extends Message>> toRemove = new LinkedList<>();
				messageTypeHash.forEach((k, v) -> {
					v.remove(mQueueRemoved);
					if(messageTypeHash.get(k).isEmpty()){
						toRemove.add(k);
					}
				});
				synchronized (messageTypeHashLocker) {
					for (Class<? extends Message> type:toRemove) {
						messageTypeHash.remove(type);
					}
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




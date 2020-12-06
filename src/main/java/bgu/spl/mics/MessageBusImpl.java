package bgu.spl.mics;



import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;


/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {

//TODO implement the round robin demand
	private HashMap<Class<? extends Message>, Queue<Queue<Message>>> messageTypeHash;
	private HashMap<MicroService, Queue<Message>> registeredHash;
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
		synchronized (messageTypeHashLocker) {//this is lock all the messageTypeHash maybe we can lock only the hash for the tyoe we use now
			System.out.println(m.name +" in sub event");
			if (!messageTypeHash.containsKey(type)) {
				messageTypeHash.put(type, new LinkedList<>());
			}

			if (!messageTypeHash.get(type).contains(registeredHash.get(m))) {
				messageTypeHash.get(type).add(registeredHash.get(m));
			}
			System.out.println(type +" queue:");
			for (Queue<Message> q:
					messageTypeHash.get(type)) {
				System.out.println(q);
			}
		}

	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		if(!messageTypeHash.containsKey(type))
			messageTypeHash.put(type,new LinkedList<>());

		if(!messageTypeHash.get(type).contains(registeredHash.get(m)))
			messageTypeHash.get(type).add(registeredHash.get(m));

	}

	@Override
	public <T> void complete(Event<T> e, T result) {
		futureHashMap.get(e).resolve(result);
	}

	@Override
	public void sendBroadcast(Broadcast b) {
		if(!messageTypeHash.containsKey(b.getClass())){
			//throw error - no one subscribe to this broadcast
		}
		Queue<Queue<Message>> subscribersQueue=messageTypeHash.get(b.getClass());
		for (Queue<Message> elem:subscribersQueue) {
			elem.add(b);
		}
	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		if(!messageTypeHash.containsKey(e.getClass()))
        return null;

		Queue<Queue<Message>> subscribersQueue = messageTypeHash.get(e.getClass());
//		System.out.println("-------------------------------------------------------------------");
//		System.out.println("pre");
//		for (Queue<Message> q:
//			 subscribersQueue) {
//			System.out.println(q);
//		}

		Queue<Message> msQueue = subscribersQueue.remove();
		msQueue.add(e);
		subscribersQueue.add(msQueue);
//		System.out.println("post");
//		for (Queue<Message> q:
//				subscribersQueue) {
//			System.out.println(q);
//		}


		Future<T> future = new Future<>();
		futureHashMap.put(e,future);

		return future;
	}

	@Override
	public void register(MicroService m) {
		registeredHash.put(m,new LinkedList<Message>());//TODO ??change that to something thread - safety???
	}

	@Override
	public void unregister(MicroService m) {
		if(registeredHash.containsValue(m)) {
			Queue<Message> mQueueRemoved = registeredHash.remove(m);
			messageTypeHash.forEach((k, v) -> v.removeIf(q -> q.equals(mQueueRemoved)));
		}

	}


	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
		//blocking??
		Queue<Message> msQueue = registeredHash.get(m);

		while(msQueue.isEmpty()){
			try {
				Thread.sleep(10);
			}catch (InterruptedException e){}
		} //wait();//TODO: how do we make thread to wait
		//TODO once the thread starting to take message from the queue blook the other threads from accesing it
		return msQueue.remove();
	}

//	public void restart(){
//		messageTypeHash.clear();
//		registeredHash.clear();
//		futureHashMap.clear();
//
//	}
}

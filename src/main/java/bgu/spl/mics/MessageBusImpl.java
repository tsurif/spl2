package bgu.spl.mics;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {


	private HashMap<Class<? extends Message>, Queue<Queue<Message>>> messageTypeHash;
	private HashMap<MicroService, Queue<Message>> registeredHash;

	private static class MessageBusHolder{
		private static MessageBusImpl instance = new MessageBusImpl();
	}
	private MessageBusImpl(){

	}
	public static MessageBusImpl getInstance(){
		return MessageBusHolder.instance;
	}


	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
		if(!messageTypeHash.containsKey(type))
			messageTypeHash.put(type,new LinkedList<>());

		if(!messageTypeHash.get(type).contains(registeredHash.get(m)))
			messageTypeHash.get(type).add(registeredHash.get(m));
		
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
		
	}

	@Override
	public void sendBroadcast(Broadcast b) {
		
	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		if(!messageTypeHash.containsKey(e.getClass()))
        return null;

		Queue<Queue<Message>> subscribersQueue = messageTypeHash.get(e.getClass());
		Queue<Message> msQueue = subscribersQueue.remove();
		subscribersQueue.add(msQueue);

		msQueue.add(e);

		return null;
	}

	@Override
	public void register(MicroService m) {
		registeredHash.put(m,new LinkedList<Message>());//TODO ??change that to something thread - safety???
	}

	@Override
	public void unregister(MicroService m) {
		
	}

	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
		
		return null;
	}
}


import java.util.LinkedList;
	import java.util.Queue;

public class ShareQueue {
	
	    private final Queue<String> queue = new LinkedList<>();
	    private final int capacity;
	    private boolean isWriterActive = true;

	    public ShareQueue(int capacity) {
	        this.capacity = capacity;
	    }

	    public synchronized void put(String message) throws InterruptedException {
	        while (queue.size() >= capacity || !isWriterActive) {
	            // Wait for space in the queue or until the writer is active
	            wait();
	        }
	        queue.add(message);
	        notifyAll(); // Notify waiting consumers
	    }

	    public synchronized String get() throws InterruptedException {
	        while (queue.isEmpty() && isWriterActive) {
	            // Wait for a message or until the writer is active
	            wait();
	        }
	        if (!queue.isEmpty()) {
	            String message = queue.poll();
	            notifyAll(); // Notify waiting producer and other consumers
	            return message;
	        }
	        return null; // No message to consume
	    }

	    public synchronized void stopWriter() {
	        isWriterActive = false;
	        notifyAll(); // Notify waiting consumers
	    }
	}


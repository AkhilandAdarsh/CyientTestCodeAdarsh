
public class SharedQueueDemo {

	public static void main(String[] args) {
		ShareQueue sharedQueue = new ShareQueue(10); // Queue capacity of 10

		// Writer thread
		Thread writerThread = new Thread(() -> {
			for (int i = 1; i <= 25; i++) {
				String message = "Message " + i;
				try {
					System.out.println(" send "+message);
					sharedQueue.put(message);
					Thread.sleep(200); // Simulate adding 5 messages per second
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
			sharedQueue.stopWriter();
		});

		// Reader threads (5 consumers)
		Thread[] readerThreads = new Thread[5];
		for (int i = 0; i < 5; i++) {
			readerThreads[i] = new Thread(() -> {
				while (true) {
					try {
						String message = sharedQueue.get();
						if (message != null) {
							System.out.println("Received: " + message);
						} else {
							break; // No more messages, exit the loop
						}
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			});
			readerThreads[i].start();
		}

		// Start the writer and reader threads
		writerThread.start();

		// Wait for all reader threads to finish
		for (Thread readerThread : readerThreads) {
			try {
				System.out.println("hello");
				readerThread.join();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

		System.out.println("All readers have finished.");
	}

}

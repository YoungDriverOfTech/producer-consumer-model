package lockCondition;

import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

public class Consumer extends Thread{

    Container container;
    ReentrantLock lock;

    public Consumer(Container container, ReentrantLock lock) {
        this.container = container;
        this.lock = lock;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                lock.lock();
                while (container.getValue().isEmpty()) {
                    try {
                        container.getNotProducedYet().await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Integer value = container.getValue().get();
                System.out.println("consuming " + value);
                container.setValue(Optional.empty());
                container.getNotConsumedYet().signal();
            } finally {
                lock.unlock();
            }
        }
    }
}

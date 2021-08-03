package lockCondition;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class Producer extends Thread{

    Container container;
    ReentrantLock lock;

    public Producer(Container container, ReentrantLock lock) {
        this.container = container;
        this.lock = lock;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {

            try {
                lock.lock();

                while (container.getValue().isPresent()) {
                    try {
                        container.getNotConsumedYet().await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                int r = new Random().nextInt();
                System.out.println("producing " + r);
                container.setValue(Optional.of(r));
                container.getNotProducedYet().signal();

            } finally {
                lock.unlock();
            }


        }
    }
}

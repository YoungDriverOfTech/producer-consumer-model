package mutipleThread;

import java.util.Optional;

public class Consumer extends Thread{

    Container container;
    Object lock;

    public Consumer(Container container, Object lock) {
        this.container = container;
        this.lock = lock;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            synchronized (lock) {
                while (container.getValue().isEmpty()) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Integer value = container.getValue().get();
                System.out.println("consuming " + value);
                container.setValue(Optional.empty());
                lock.notify();
            }
        }
    }
}

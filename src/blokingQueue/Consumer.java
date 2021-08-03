package blokingQueue;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class Consumer extends Thread{

    // 这是一个并发的容器，它的方法可以解决并发访问的问题
    BlockingQueue<Integer> queue;

    // 这个queue是用来控制生产者和消费者，完成各自的动作之后，不要直接进行下一次循环，而要先进行等待
    BlockingQueue<Integer> signalQueue;

    public Consumer(BlockingQueue<Integer> queue, BlockingQueue<Integer> signalQueue) {
        this.queue = queue;
        this.signalQueue = signalQueue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            /**
             * Retrieves and removes the head of this queue, waiting if necessary
             * until an element becomes available.
             *
             * 只要这个队列里面没有元素，就会一直等着，直到有元素了，那么就从这个队列里面拿出来第一个元素
             */
            try {
                System.out.println("consuming " + queue.take());
                signalQueue.put(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

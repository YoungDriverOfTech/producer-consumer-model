package blokingQueue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Producer extends Thread{

    // 这是一个并发的容器，它的方法可以解决并发访问的问题
    BlockingQueue<Integer> queue;

    // 这个queue是用来控制生产者和消费者，完成各自的动作之后，不要直接进行下一次循环，而要先进行等待
    BlockingQueue<Integer> signalQueue;

    public Producer(BlockingQueue<Integer> queue, BlockingQueue<Integer> signalQueue) {
        this.queue = queue;
        this.signalQueue = signalQueue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            int r = new Random().nextInt();
            System.out.println("producing " +r);

            /**
             * Inserts the specified element into this queue, waiting if necessary
             * for space to become available.
             *
             * 只有队列里面有了可用空间，才会put元素，否则就等待
             */
            try {
                queue.put(r);
                signalQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}

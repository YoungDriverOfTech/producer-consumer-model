package blokingQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Boss {

    /*
    * 随机产生10个数组，使用wait/notify/notifyAll完成生产者消费者模型
    * */
    public static void main(String[] args) throws InterruptedException {

        // capacity 是容量，这个队列至多只能存放一个元素
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(1);
        BlockingQueue<Integer> signalQueue = new LinkedBlockingQueue<>(1);

        Producer producer = new Producer(queue, signalQueue);
        Consumer consumer = new Consumer(queue, signalQueue);

        producer.start();
        consumer.start();
    }
}

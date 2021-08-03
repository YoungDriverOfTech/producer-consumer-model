package lockCondition;

import java.util.concurrent.locks.ReentrantLock;

public class Boss {

    /*
    * 随机产生10个数组，使用wait/notify/notifyAll完成生产者消费者模型
    * */
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Container container = new Container(lock);

        Producer producer = new Producer(container, lock);
        Consumer consumer = new Consumer(container, lock);

        producer.start();
        consumer.start();

//        producer.join();
//        consumer.join();
    }
}

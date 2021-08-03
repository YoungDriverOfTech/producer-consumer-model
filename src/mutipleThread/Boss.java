package mutipleThread;

import java.util.Optional;

public class Boss {

    /*
    * 随机产生10个数组，使用wait/notify/notifyAll完成生产者消费者模型
    * */
    public static void main(String[] args) throws InterruptedException {
        Container container = new Container();
        Object lock = new Object();

        Producer producer = new Producer(container, lock);
        Consumer consumer = new Consumer(container, lock);

        producer.start();
        consumer.start();

//        producer.join();
//        consumer.join();
    }
}

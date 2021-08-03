package lockCondition;

import java.util.Optional;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Container {

    private Condition notConsumedYet; // 还没有被消费掉
    private Condition notProducedYet; // 还没有被生产

    public Container(ReentrantLock lock) {
        this.notConsumedYet = lock.newCondition();
        this.notProducedYet = lock.newCondition();
    }

    public Condition getNotConsumedYet() {
        return notConsumedYet;
    }

    public Condition getNotProducedYet() {
        return notProducedYet;
    }

    private Optional<Integer> value = Optional.empty();

    public Optional<Integer> getValue() {
        return value;
    }

    public void setValue(Optional<Integer> value) {
        this.value = value;
    }
}

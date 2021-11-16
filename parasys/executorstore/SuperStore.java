package parasys.executorstore;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class SuperStore {

    private final String _name;
    private final ReverseVendingMachine[] _reverseVendingMachines;
    private final ReentrantLock _queueLock = new ReentrantLock();
    private final Condition _queueLockCondition = _queueLock.newCondition();
    private ExecutorService _customerPool = Executors.newFixedThreadPool(50);

    public SuperStore(String name) {
        _name = name;

        final int NUMBER_OF_MACHINES = 4;
        _reverseVendingMachines = IntStream.range(1, NUMBER_OF_MACHINES + 1).mapToObj(ReverseVendingMachine::new)
                .toArray(ReverseVendingMachine[]::new);
    }

    public void OpenTheDoors() {
        Logger.log("Opening", this);

        for (var customerNumber = 1; customerNumber <= 50; customerNumber++) {
            _customerPool.execute(new Customer(customerNumber, this));

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.out.println("The customer is in hurry and can't wait 3 seconds.");
            }
        }

        _customerPool.shutdown();
    }

    public void DepositBottles(List<Basket> _baskets) {

        ReverseVendingMachine availableMachine;
        try {
            _queueLock.lock();
            if (Arrays.stream(_reverseVendingMachines).allMatch(m -> m.GetInUse())) {
                try {
                    _queueLockCondition.await();
                } catch (InterruptedException e) {
                    Logger.log("A customer died");
                }
            }
            availableMachine = Arrays.stream(_reverseVendingMachines).filter(m -> !m.GetInUse()).findFirst().get();
            availableMachine.SetInUse(true);
        } finally {
            _queueLock.unlock();
        }

        availableMachine.DepositBottles(_baskets);
        try {
            _queueLock.lock();
            availableMachine.SetInUse(false);
            _queueLockCondition.signal();
        } finally {
            _queueLock.unlock();
        }
    }

    @Override
    public String toString() {
        return _name;
    }
}
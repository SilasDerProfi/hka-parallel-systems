package parasys.executorstore;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Customer implements Runnable {

    private final int _customerNumber;
    private final List<Basket> _baskets;
    private SuperStore _currentStore;

    public Customer(int customerNumber, SuperStore currentStore) {
        _customerNumber = customerNumber;

        var numberOfBaskets = (int) (Math.random() * 3 + 1);
        _baskets = IntStream.range(1, numberOfBaskets + 1).mapToObj(i -> new Basket(this, i))
                .collect(Collectors.toList());

        _currentStore = currentStore;
    }

    @Override
    public void run() {
        Logger.log("Entering", this);
        _currentStore.DepositBottles(_baskets);
        Logger.log("Leaving", this);
    }

    @Override
    public String toString() {
        return "Customer #" + _customerNumber;
    }
}

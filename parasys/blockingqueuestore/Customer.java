package parasys.blockingqueuestore;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Customer implements Comparable<Customer> {

    private final int _customerNumber;
    private final List<Basket> _baskets;
    private final boolean _isGoldCustomer;

    public Customer(int customerNumber, boolean isGoldCustomer) {
        _customerNumber = customerNumber;
        _isGoldCustomer = isGoldCustomer;
        var numberOfBaskets = (int) (Math.random() * 3 + 1);
        _baskets = IntStream.range(1, numberOfBaskets + 1).mapToObj(i -> new Basket(this, i))
                .collect(Collectors.toList());
    }

    public List<Basket> GetBaskets() {
        return _baskets;
    }

    @Override
    public String toString() {
        return "Customer #" + _customerNumber + (_isGoldCustomer ? " (Gold)" : "");
    }

    @Override
    public int compareTo(Customer o) {
        if (_isGoldCustomer == o._isGoldCustomer)
            return Integer.compare(_customerNumber, o._customerNumber);
        else if (_isGoldCustomer)
            return -1;
        else
            return 1;

    }
}

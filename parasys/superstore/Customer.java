package parasys.superstore;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Customer {

    private int _customerNumber;
    private List<Basket> _baskets;

    public Customer(int customerNumber) {
        _customerNumber = customerNumber;

        var numberOfBaskets = (int) (Math.random() * 3 + 1);
        _baskets = Stream.generate(() -> new Basket()).limit(numberOfBaskets).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Customer #" + _customerNumber;
    }
}

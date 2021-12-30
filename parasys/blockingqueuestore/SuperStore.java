package parasys.blockingqueuestore;

import java.util.Arrays;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.stream.IntStream;

public class SuperStore {

    private final String _name;
    private final ReverseVendingMachine[] _reverseVendingMachines;
    private PriorityBlockingQueue<Customer> _customers;

    public SuperStore(String name) {
        _name = name;
        _customers = new PriorityBlockingQueue<>();

        final int NUMBER_OF_MACHINES = 4;
        _reverseVendingMachines = IntStream.range(1, NUMBER_OF_MACHINES + 1)
                .mapToObj(i -> new ReverseVendingMachine(i, _customers)).toArray(ReverseVendingMachine[]::new);
    }

    public void OpenTheDoors() {
        Arrays.stream(_reverseVendingMachines).forEach(ReverseVendingMachine::start);
        Logger.log("Opening", this);

        for (var customerNumber = 1; customerNumber <= 50; customerNumber++) {

            var newCustomer = new Customer(customerNumber, Math.random() < 0.3);
            _customers.add(newCustomer);
            Logger.log("Entering", newCustomer);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.out.println("exception...");
            }
        }
    }

    @Override
    public String toString() {
        return _name;
    }
}
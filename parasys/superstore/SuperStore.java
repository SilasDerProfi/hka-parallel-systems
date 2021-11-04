package parasys.superstore;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

public class SuperStore {

    private String name;
    private ReverseVendingMachine[] _reverseVendingMachines;
    private LinkedBlockingQueue<Customer> _customers;

    public SuperStore(String name) {
        final int NUMBER_OF_MACHINES = 4;

        _reverseVendingMachines = Stream.generate(() -> new ReverseVendingMachine()).limit(NUMBER_OF_MACHINES)
                .toArray(ReverseVendingMachine[]::new);
    }

    public void OpenTheDoors() {
        new Thread(() -> {
            _customers = new LinkedBlockingQueue<>();

            for (var customerNumber = 1; customerNumber <= 50; customerNumber++) {

                var newCustomer = new Customer(customerNumber);
                _customers.add(newCustomer);
                System.out.println(newCustomer + " enters the Store");

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    System.out.println("");
                }
            }
        }).start();
    }

    public String GetName() {
        return name;
    }

}
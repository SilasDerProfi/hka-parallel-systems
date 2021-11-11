package parasys.executorstore;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.IntStream;

public class SuperStore {

    private final String _name;
    private final ReverseVendingMachine[] _reverseVendingMachines;
    private LinkedBlockingQueue<Customer> _customers;

    public SuperStore(String name) {
        _name = name;

        final int NUMBER_OF_MACHINES = 4;
        _reverseVendingMachines = IntStream.range(1, NUMBER_OF_MACHINES + 1).mapToObj(ReverseVendingMachine::new)
                .toArray(ReverseVendingMachine[]::new);
    }

    public void OpenTheDoors() {
        Logger.log("Opening", this);

        _customers = new LinkedBlockingQueue<>();

        for (var customerNumber = 1; customerNumber <= 50; customerNumber++) {

            var newCustomer = new Customer(customerNumber);
            newCustomer.EnterStore(this);
            _customers.add(newCustomer);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.out.println("exception...");
            }
        }
    }

    public void DepositBottles(List<Basket> _baskets) {

        ReverseVendingMachine availableMachine;
        synchronized (this) {
            if (Arrays.stream(_reverseVendingMachines).allMatch(m -> m.GetInUse())) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Logger.log("A customer died");
                }
            }

            availableMachine = Arrays.stream(_reverseVendingMachines).filter(m -> !m.GetInUse()).findFirst().get();
            availableMachine.SetInUse(true);
        }

        availableMachine.DepositBottles(_baskets);

        synchronized (this) {
            availableMachine.SetInUse(false);
            notify();
        }
    }

    @Override
    public String toString() {
        return _name;
    }
}
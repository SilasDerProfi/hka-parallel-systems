package parasys.blockingqueuestore;

import java.util.concurrent.PriorityBlockingQueue;

public class ReverseVendingMachine extends Thread {

    private final int _machineNumber;
    private final PriorityBlockingQueue<Customer> _customers;

    public ReverseVendingMachine(int machineNumber, PriorityBlockingQueue<Customer> customers) {
        _machineNumber = machineNumber;
        _customers = customers;
    }

    @Override
    public void run() {
        Customer customer;
        while (true) {
            try {
                customer = _customers.take();
            } catch (InterruptedException e) {
                System.out.println("exception...");
                continue;
            }

            Logger.log("Start Deposit", customer, this);
            customer.GetBaskets().forEach(b -> b.Deposit(this));
            Logger.log("End Deposit", customer, this);
            Logger.log("Leaving", customer);
        }
    }

    @Override
    public String toString() {
        return "Machine #" + _machineNumber;
    }

}

package parasys.superstore;

import java.util.concurrent.LinkedBlockingQueue;

public class SuperStore {

    private ReverseVendingMachine[] _reverseVendingMachines = new ReverseVendingMachine[4];
    private LinkedBlockingQueue<Customer> _customers = new LinkedBlockingQueue<>(50);
        
}
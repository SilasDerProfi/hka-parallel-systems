package parasys.blockingqueuestore;

public class Basket {

    private final double _dischargeDuration;
    private final Customer _owner;
    private final int _basketNumber;

    public Basket(Customer owner, int basketNumber) {
        _dischargeDuration = Math.random() * 4 + 2;
        _owner = owner;
        _basketNumber = basketNumber;
    }

    public void Deposit(ReverseVendingMachine reverseVendingMachine) {
        final int MILLISECOND_CONVERSION_FACTOR = 1000;
        try {
            Thread.sleep((int) (_dischargeDuration * MILLISECOND_CONVERSION_FACTOR));
        } catch (InterruptedException e) {
        }

        Logger.log("Deposited", _owner, this, reverseVendingMachine);
    }

    @Override
    public String toString() {
        return "Basket #" + _basketNumber;
    }
}

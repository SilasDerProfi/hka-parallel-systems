package parasys.superstore;

import java.util.List;

public class ReverseVendingMachine {

    private final int _machineNumber;
    private boolean _isInUse;

    public ReverseVendingMachine(int machineNumber) {
        _machineNumber = machineNumber;
    }

    public void DepositBottles(List<Basket> _baskets) {
        Logger.log("Start Deposit", _baskets.get(0).GetOwner(), this);
        _baskets.forEach(b -> b.Deposit(this));
        Logger.log("End Deposit", _baskets.get(0).GetOwner(), this);
    }

    public boolean GetInUse() {
        return _isInUse;
    }

    public void SetInUse(boolean isInUse) {
        _isInUse = isInUse;
    }

    @Override
    public String toString() {
        return "Machine #" + _machineNumber;
    }

}

package models;

public class OtherParameter {
    private double minDepositAmount;
    private double minInitDeposit;
    private boolean controlClosingSaving;

    public OtherParameter(double minDepositAmount, double minInitDeposit, boolean controlClosingSaving) {
        this.minDepositAmount = minDepositAmount;
        this.minInitDeposit = minInitDeposit;
        this.controlClosingSaving = controlClosingSaving;
    }

    public void setMinDepositAmount(double minDepositAmount) {
        this.minDepositAmount = minDepositAmount;
    }

    public void setMinInitDeposit(double minInitDeposit) {
        this.minInitDeposit = minInitDeposit;
    }

    public void setControlClosingSaving(boolean controlClosingSaving) {
        this.controlClosingSaving = controlClosingSaving;
    }

    public double getMinDepositAmount() {
        return minDepositAmount;
    }

    public double getMinInitDeposit() {
        return minInitDeposit;
    }

    public boolean isControlClosingSaving() {
        return controlClosingSaving;
    }
}

package models.builder;

import models.OtherParameter;

public class OtherParameterBuilder {
    private double minDepositAmount;
    private double minInitDeposit;
    private boolean controlClosingSaving;

    public OtherParameterBuilder setMinDepositAmount(double deposit) {
        this.minDepositAmount = deposit;
        return this;
    }

    public OtherParameterBuilder setMinInitDeposit(double deposit) {
        this.minInitDeposit = deposit;
        return this;
    }

    public OtherParameterBuilder setControlClosingSaving(boolean controlClosingSaving) {
        this.controlClosingSaving = controlClosingSaving;
        return this;
    }

    public OtherParameter getResult() {
        return new OtherParameter(minDepositAmount, minInitDeposit, controlClosingSaving);
    }
}

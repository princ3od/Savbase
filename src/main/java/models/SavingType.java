package models;

import java.util.Date;

public class SavingType {
    private  int _id;
    private  String _nameSavingType;
    private int _tenor;
    private double _interestRate;
    private Date _activeDate;
    private int _minSendingDate;
    private  String _rule;


    public  SavingType(int _id,String _nameSavingType, int _tenor, double _interestRate,Date _activeDate,int _minSendingDate,String _rule){
        this._id = _id;
        this._nameSavingType= _nameSavingType;
        this._tenor=_tenor;
        this._interestRate=_interestRate;
        this._activeDate =_activeDate;
        this._minSendingDate = _minSendingDate;
        this._rule=_rule;
    };
    public String getRule() {
        return _rule;
    }

    public void setRule(String _rule) {
        this._rule = _rule;
    }

    public int getMinSendingDate() {
        return _minSendingDate;
    }

    public void setMinSendingDate(int _minSendingDate) {
        this._minSendingDate = _minSendingDate;
    }

    public Date getActiveDate() {
        return _activeDate;
    }

    public void setActiveDate(Date _activeDate) {
        this._activeDate = _activeDate;
    }

    public double getInterestRate() {
        return _interestRate;
    }

    public void setInterestRate(double _interestRate) {
        this._interestRate = _interestRate;
    }

    public int getTenor() {
        return _tenor;
    }

    public void setTenor(int _tenor) {
        this._tenor = _tenor;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getNameSavingType() {
        return _nameSavingType;
    }

    public void setNameSavingType(String _nameSavingType) {
        this._nameSavingType = _nameSavingType;
    }
}

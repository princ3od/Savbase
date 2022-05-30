package models.builder;

import models.SavingType;

import java.util.Date;

public class SavingBuilder {
    private  int _id;
    private  String _nameSavingType;
    private int _tenor;
    private double _interestRate;
    private Date _activeDate;
    private int _minSendingDate;
    private  String _rule;

    public SavingBuilder setSavingId(int _id){
        this._id = _id;
        return  this;
    }
    public SavingBuilder setNameSavingType(String _nameSavingType){
        this._nameSavingType = _nameSavingType;
        return  this;
    }
    public SavingBuilder setTenor(int _tenor){
        this._tenor = _tenor;
        return  this;
    }
    public SavingBuilder setInterestRate(double _interestRate){
        this._interestRate = _interestRate;
        return  this;
    }
    public SavingBuilder setActiveDate(Date _date){
        this._activeDate = _date;
        return  this;
    }
    public SavingBuilder setMinSendingDate(int _minSendingDate){
        this._minSendingDate = _minSendingDate;
        return  this;
    }
    public SavingBuilder setRule(String _rule){
        this._rule = _rule;
        return  this;
    }

    public SavingType build(){
        return new SavingType(_id,_nameSavingType,_tenor,_interestRate,_activeDate,_minSendingDate,_rule);
    }

}

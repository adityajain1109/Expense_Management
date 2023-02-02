package com.example.expensemanager;

public class TransactionalModel
{
    private String id, note, amount, type,date;

    public TransactionalModel()
    {

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public TransactionalModel(String id, String note, String amount, String type, String Date)
    {
        this.id=id;
        this.amount=amount;
        this.note=note;
        this.type=type;
        this.date=date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}

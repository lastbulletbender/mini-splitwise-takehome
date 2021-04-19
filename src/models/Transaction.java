package models;

import java.util.HashMap;
import java.util.List;

public abstract class Transaction {

    protected String transactionName;

    protected User paidBy;

    protected HashMap<User, Float> amountMapping;

    protected Float amount;

    public Transaction(String transactionName, User paidBy, Float amount) {
        this.transactionName = transactionName;
        this.paidBy = paidBy;
        this.amount = amount;
        this.amountMapping = new HashMap<User, Float>();
    }

    public String getTransactionName() {
        return transactionName;
    }

    public User getPaidBy() {
        return paidBy;
    }

    public HashMap<User, Float> getAmountMapping() {
        return amountMapping;
    }

    public Float getAmount() {
        return amount;
    }
}

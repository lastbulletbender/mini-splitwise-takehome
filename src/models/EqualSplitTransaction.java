package models;

import java.util.HashMap;
import java.util.List;

public class EqualSplitTransaction extends Transaction {

    public EqualSplitTransaction(String transactionName, User paidBy, List<User> users, Float amount) {
        super(transactionName, paidBy, amount);
        split(users);
    }
    protected void split(List<User> users) {
        float splitAmount = this.amount / (users.size());
        for (User u : users) {
            this.amountMapping.put(u, splitAmount);
        }
    }
}

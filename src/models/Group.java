package models;

import java.util.*;

public class Group {

    private String name;

    private User admin;

    private HashSet<User> users = new HashSet<>();

    private List<Transaction> transactions = new ArrayList<>();

    private Map<User, Map<User, Float>> transactionMap = new HashMap<>();

    public Group(String name, User admin) {
        this.name = name;
        this.admin = admin;
        users.add(admin);
    }

    public String getName() {
        return name;
    }

    public HashSet<User> getUsers() {
        return users;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public User getAdmin() {
        return admin;
    }

    public void updateTransactionMap(Transaction transaction) {

        Map<User, Float> owedMap = new HashMap<>();
        transaction.getAmountMapping().forEach((key, value) -> {
            if (!key.equals(transaction.getPaidBy())) {
                owedMap.put(key, value);
            }
        });

        if (transactionMap.containsKey(transaction.getPaidBy())) {
            transactionMap.get(transaction.getPaidBy()).forEach(((user, balance) -> {
                if (owedMap.containsKey(user)) {
                    owedMap.put(user, owedMap.get(user) + balance);
                }
            }));
        } else {
            transaction.getAmountMapping().forEach((k,v) -> {
                if (transactionMap.containsKey(k)) {
                    if (transactionMap.get(k).containsKey(transaction.getPaidBy())) {
                        if (transactionMap.get(k).get(transaction.getPaidBy()) > v) {
                            transactionMap.get(k).put(transaction.getPaidBy(), transactionMap.get(k).get(transaction.getPaidBy()) - v);
                        } else {
                            owedMap.put(k, v - transactionMap.get(k).get(transaction.getPaidBy()));
                            transactionMap.get(k).remove(transaction.getPaidBy());

                        }
                    }
                }
            });
        }
        transactionMap.put(transaction.getPaidBy(), owedMap);
    }

    public Map<User, Map<User, Float>> getTransactionMap() {
        return transactionMap;
    }

    public void setTransactionMap(Map<User, Map<User, Float>> transactionMap) {
        this.transactionMap = transactionMap;
    }
}

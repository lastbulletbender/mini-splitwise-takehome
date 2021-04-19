package service;

import models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MiniSplitwise {

    public void createGroup(User adminUser, String groupName) {
        Group group = new Group(groupName, adminUser);
        adminUser.getGroups().put(groupName, group);
    }

    public void addMembersToGroup(User adminUser, String groupName, List<User> users) throws Exception {
        Group group = adminUser.getGroups().get(groupName);

        if (null == group) {
            throw new Exception("User does not have such group");
        }

        for (User u : users) {
            if (!group.getUsers().add(u)) {
                System.out.println("WARN : " + "User " + u.getName() + " already exists in the group");
                continue;
            }
            u.getGroups().put(groupName, group);
        }
    }

    public void removeMembersFromGroup(User adminUser, String groupName, User userToRemove) throws Exception {
        Group group = adminUser.getGroups().get(groupName);
        if (null == group) {
            throw new Exception("User does not have such group");
        }
        if (group.getAdmin() != adminUser) {
            throw new Exception("User is not admin of the group");
        }

        if (adminUser == userToRemove) {
            throw new Exception("Admin cannot be removed from a group");
        }

        if (group.getTransactionMap().containsKey(userToRemove)) {
            for (Map.Entry<User, Float> entry : group.getTransactionMap().get(userToRemove).entrySet()) {
                User k = entry.getKey();
                Float v = entry.getValue();
                if (v!= 0F) {
                    throw new Exception("User owes/is owed money");
                }
            }
        }
        for (Map.Entry<User, Map<User, Float>> e : group.getTransactionMap().entrySet()) {
            User k = e.getKey();
            Map<User, Float> v = e.getValue();
            for (Map.Entry<User, Float> entry : group.getTransactionMap().get(k).entrySet()) {
                User k1 = entry.getKey();
                Float v1 = entry.getValue();
                if (k1.equals(userToRemove) && v1 != 0) {
                    throw new Exception("User owes/is owed money");
                }
            }
        }

        group.getUsers().remove(userToRemove);
        userToRemove.getGroups().remove(group.getName());
    }

    public void addTransactionToGroup(User user, Transaction transaction, String groupName) throws Exception {

        Group group = user.getGroups().get(groupName);

        if (group == null) {
            throw new Exception("User does not have such group");
        }
        if (!validateTransaction(group, transaction)) {
            throw new Exception("A user does not exist in the group");
        }
        group.updateTransactionMap(transaction);
        group.getTransactions().add(transaction);
    }

    private boolean validateTransaction(Group group, Transaction transaction) {
        // Check if all users part of group
        for (User u: transaction.getAmountMapping().keySet()) {
            if (!group.getUsers().contains(u)) {
                return false;
            }
        }
        return true;
    }

    public Transaction createEqualSplitTransaction(User paidBy, String transactionName, Float amount, List<User> users)
            throws Exception {
        return new EqualSplitTransaction(transactionName, paidBy, users, amount);
    }

    public Transaction createFixedTransaction(User paidBy, String transactionName, Float amount, User[] userArray, Float[] splitArray) throws Exception {
        List<FixedValuesTransactionDTO> fixedValuesTransactionDTOS = generateFixedTransactionDTO(userArray, splitArray);
        return new FixedValuesTransaction(transactionName, paidBy, fixedValuesTransactionDTOS, amount);
    }

    public Transaction createPercentageBasedSplitTransaction(User paidBy, String transactionName, float amount, User[] users, Float[] percentages) throws Exception {
        List<PercentageBasedSplitTransactionDTO> percentageBasedSplitTransactionDTOS = generatePercentageBasedSplitTransactionDTOS(users, percentages);
        return new PercentageBasedSplitTransaction(transactionName, paidBy, percentageBasedSplitTransactionDTOS, amount);
    }

    private List<PercentageBasedSplitTransactionDTO> generatePercentageBasedSplitTransactionDTOS(User[] users, Float[] percentages) throws Exception {
        if (users.length != percentages.length) {
            throw new Exception("Arrays are not of equal length");
        }
        List<PercentageBasedSplitTransactionDTO> percentageBasedSplitTransactionDTOS = new ArrayList<>();
        for (int i = 0; i < users.length; i++) {
            percentageBasedSplitTransactionDTOS.add(new PercentageBasedSplitTransactionDTO(users[i], percentages[i]));
        }
        return percentageBasedSplitTransactionDTOS;
    }

    private List<FixedValuesTransactionDTO> generateFixedTransactionDTO(User[] userArray, Float[] splitArray) throws Exception {
        if (userArray.length != splitArray.length) {
            throw new Exception("Arrays are not of equal length");
        }
        List<FixedValuesTransactionDTO> fixedValuesTransactionDTOS = new ArrayList<>();
        for (int i = 0; i < userArray.length; i++) {
            fixedValuesTransactionDTOS.add(new FixedValuesTransactionDTO(userArray[i], splitArray[i]));
        }
        return fixedValuesTransactionDTOS;
    }


    public String getSummary(User user) {

        StringBuilder summaryBuilder = new StringBuilder();
        summaryBuilder.append("Summary for ").append(user.getName()).append(" : \n\n");
        for (Group g : user.getGroups().values()) {
            summaryBuilder.append("Group name : ").append(g.getName()).append("\n");
            if (g.getTransactionMap().get(user) != null) {
                g.getTransactionMap().get(user).forEach((k,v) -> {
                    summaryBuilder.append(k.getName()).append(" owes ").append(user.getName()).append(" ").append(v).append("\n");
                });
            }

            g.getTransactionMap().forEach((k,v) -> {
                v.forEach((key, value) -> {
                    if (key.equals(user)) {
                        summaryBuilder.append(key.getName()).append(" owes ").append(k.getName()).append(" ").append(value).append("\n");
                    }
                });
            });
            summaryBuilder.append("\n");
        }

        return summaryBuilder.toString();
    }

}

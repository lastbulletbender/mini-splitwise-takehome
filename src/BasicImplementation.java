import models.Transaction;
import models.User;
import service.MiniSplitwise;

import java.util.ArrayList;
import java.util.List;

public class BasicImplementation {

    public static void main(String[] args) {
        MiniSplitwise miniSplitwise = new MiniSplitwise();

        User user = new User(1L, "John");
        User user2 = new User(2L, "Jane");
        User user3 = new User(3L, "Tom");


        List<User> transactionUsers1 = new ArrayList<>(); // To store all users being part of a transaction
        transactionUsers1.add(user);
        transactionUsers1.add(user2);
        transactionUsers1.add(user3);

        List<User> transactionUsers2 = new ArrayList<>(); // To store only John being part of transaction
        transactionUsers2.add(user);

        miniSplitwise.createGroup(user, "Wonderla");
        Transaction t = null;

        try {
            miniSplitwise.addMembersToGroup(user, "Wonderla", transactionUsers1);

            t = miniSplitwise.createEqualSplitTransaction(user, "Tickets", 150F, transactionUsers1);
            miniSplitwise.addTransactionToGroup(user, t, "Wonderla");

            t = miniSplitwise.createEqualSplitTransaction(user2, "Snacks", 50F, transactionUsers2);
            miniSplitwise.addTransactionToGroup(user, t, "Wonderla");

            t= miniSplitwise.createFixedTransaction(user, "Food", 300F, new User[] {user, user2, user3}, new Float[] { 50F, 200F, 50F });
            miniSplitwise.addTransactionToGroup(user, t, "Wonderla");

            t= miniSplitwise.createPercentageBasedSplitTransaction(user, "Souvenir", 100F, new User[] {user, user2}, new Float[] { 50F, 50F });
            miniSplitwise.addTransactionToGroup(user, t, "Wonderla");


            System.out.println(miniSplitwise.getSummary(user));
            System.out.println("******************************");
            System.out.println(miniSplitwise.getSummary(user2));
            System.out.println("******************************");
            System.out.println(miniSplitwise.getSummary(user3));


            // After deleting user
            miniSplitwise.removeMembersFromGroup(user, "Wonderla", user3);
            System.out.println("******************************");
            System.out.println(miniSplitwise.getSummary(user3));


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

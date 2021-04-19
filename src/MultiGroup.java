import models.Transaction;
import models.User;
import service.MiniSplitwise;

import java.util.ArrayList;
import java.util.List;

public class MultiGroup {

    public static void main(String[] args) {
        MiniSplitwise miniSplitwise = new MiniSplitwise();

        User user = new User(1L, "Missy"); // My pets :)
        User user2 = new User(2L, "Fiesty");
        User user3 = new User(3L, "Zesty");
        User user4 = new User(4L, "Cera");


        List<User> transactionUsers1 = new ArrayList<>(); // To store all users being part of a transaction
        transactionUsers1.add(user);
        transactionUsers1.add(user2);
        transactionUsers1.add(user3);
        transactionUsers1.add(user4);

        List<User> transactionUsers2 = new ArrayList<>(); // To store only John being part of transaction
        transactionUsers2.add(user);

        miniSplitwise.createGroup(user, "Pets");
        miniSplitwise.createGroup(user, "Pets2");
        Transaction t = null;

        try {
            miniSplitwise.addMembersToGroup(user, "Pets", transactionUsers1);

            t = miniSplitwise.createEqualSplitTransaction(user, "Item1", 200F, transactionUsers1);
            miniSplitwise.addTransactionToGroup(user, t, "Pets");

            t= miniSplitwise.createFixedTransaction(user2, "Item2", 2000F, new User[] {user4, user, user3}, new Float[] { 1000F, 500F, 500F });
            miniSplitwise.addTransactionToGroup(user, t, "Pets");


            miniSplitwise.addMembersToGroup(user, "Pets2", transactionUsers1);

            t= miniSplitwise.createPercentageBasedSplitTransaction(user3, "Item1", 750F, new User[] {user3, user2, user}, new Float[] { 30F, 40F, 30F });
            miniSplitwise.addTransactionToGroup(user, t, "Pets2");

            System.out.println();
            System.out.println(miniSplitwise.getSummary(user));
            System.out.println("******************************");
            System.out.println(miniSplitwise.getSummary(user2));
            System.out.println("******************************");
            System.out.println(miniSplitwise.getSummary(user3));
            System.out.println("******************************");
            System.out.println(miniSplitwise.getSummary(user4));


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

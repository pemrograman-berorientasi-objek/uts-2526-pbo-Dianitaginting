package fintech.driver;

import fintech.model.*;
import java.util.*;

/**
 * @author 12S24044 Dianita Lorensia Br Ginting
 */

public class Driver2 {

    public static void main(String[] _args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<Account> accounts = new ArrayList<>();
        ArrayList<Transaction> transactions = new ArrayList<>();

        int transactionId = 0;

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("---")) {
                break;
            }

            String[] parts = input.split("#");
            String command = parts[0];

            if (command.equals("create-account")) {
                String name = parts[1];
                String username = parts[2];

                Account acc = new Account(name, username);
                accounts.add(acc);
            }

            else if (command.equals("deposit")) {
                String username = parts[1];
                double amount = Double.parseDouble(parts[2]);
                String timestamp = parts[3];
                String description = parts[4];

                for (Account acc : accounts) {
                    if (acc.getUsername().equals(username)) {
                        acc.deposit(amount);

                        transactionId++;
                        Transaction t = new DepositTransaction(
                            transactionId, username, amount, timestamp, description
                        );
                        transactions.add(t);
                        break;
                    }
                }
            }

            else if (command.equals("withdraw")) {
                String username = parts[1];
                double amount = Double.parseDouble(parts[2]);
                String timestamp = parts[3];
                String description = parts[4];

                for (Account acc : accounts) {
                    if (acc.getUsername().equals(username)) {

                        boolean success = acc.withdraw(amount);

                        if (success) {
                            transactionId++;
                            Transaction t = new WithdrawTransaction(
                                transactionId, username, amount, timestamp, description
                            );
                            transactions.add(t);
                        }

                        break;
                    }
                }
            }
        }

       
        for (Account acc : accounts) {
            System.out.println(
                acc.getUsername() + "|" +acc.getName() + "|" + acc.getBalance()
            );
        }

        scanner.close();
    }
}
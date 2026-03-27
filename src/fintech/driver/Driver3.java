package fintech.driver;

import fintech.model.*;
import java.util.*;

/**
 * @author 12S24044 Dianita Lorensia Br Ginting
 */

public class Driver3 {

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

                accounts.add(new Account(name, username));
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
                        transactions.add(new DepositTransaction(
                            transactionId, username, amount, timestamp, description
                        ));
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

                        if (acc.withdraw(amount)) {
                            transactionId++;
                            transactions.add(new WithdrawTransaction(
                                transactionId, username, amount, timestamp, description
                            ));
                        }

                        break;
                    }
                }
            }

            else if (command.equals("transfer")) {
                String sender = parts[1];
                String receiver = parts[2];
                double amount = Double.parseDouble(parts[3]);
                String timestamp = parts[4];
                String description = parts[5];

                Account senderAcc = null;
                Account receiverAcc = null;

                
                for (Account acc : accounts) {
                    if (acc.getUsername().equals(sender)) {
                        senderAcc = acc;
                    }
                    if (acc.getUsername().equals(receiver)) {
                        receiverAcc = acc;
                    }
                }

                
                if (senderAcc != null && receiverAcc != null) {
                    if (senderAcc.withdraw(amount)) {

                        receiverAcc.deposit(amount);

                        transactionId++;
                        transactions.add(new TransferTransaction(
                            transactionId, sender, receiver, amount, timestamp, description
                        ));
                    }
                }
            }
        }

    
        for (Account acc : accounts) {
            System.out.println(
                acc.getUsername() + "|" +
                acc.getName() + "|" +
                acc.getBalance()
            );
        }

        scanner.close();
    }
}

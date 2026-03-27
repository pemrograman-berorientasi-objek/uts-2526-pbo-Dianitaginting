package fintech.driver;

import fintech.model.*;
import java.util.*;

/**
 * @author 12S24044 Dianita Lorensia Br Ginting
 */

class TxRecord {
    int id;
    String username;
    String type;
    double amount;
    String timestamp;
    String description;

    public TxRecord(int id, String username, String type,
                    double amount, String timestamp, String description) {
        this.id = id;
        this.username = username;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
        this.description = description;
    }
}

public class Driver4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<Account> accounts = new ArrayList<>();
        ArrayList<Transaction> transactions = new ArrayList<>(); 
        ArrayList<TxRecord> records = new ArrayList<>(); 

        int transactionId = 0;

        while (true) {
            String input = scanner.nextLine();
            
            if (input.equals("---")) {
                break;
            }

            String[] parts = input.split("#");
            String command = parts[0];

            try {
                if (command.equals("create-account")) {
                    accounts.add(new Account(parts[1], parts[2]));
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

                            
                            records.add(new TxRecord(
                                transactionId, username, "deposit",
                                amount, timestamp, description
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

                            if (!acc.withdraw(amount)) {
                                throw new NegativeBalanceException("Saldo tidak cukup");
                            }

                            transactionId++;

                            transactions.add(new WithdrawTransaction(
                                transactionId, username, amount, timestamp, description
                            ));

                            records.add(new TxRecord(
                                transactionId, username, "withdraw",
                                -amount, timestamp, description
                            ));
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
                        if (acc.getUsername().equals(sender)) senderAcc = acc;
                        if (acc.getUsername().equals(receiver)) receiverAcc = acc;
                    }

                    if (senderAcc != null && receiverAcc != null) {

                        if (!senderAcc.withdraw(amount)) {
                            throw new NegativeBalanceException("Saldo tidak cukup");
                        }

                        receiverAcc.deposit(amount);

                        transactionId++;

                        transactions.add(new TransferTransaction(
                            transactionId, sender, receiver, amount, timestamp, description
                        ));

                        records.add(new TxRecord(
                            transactionId, sender, "transfer",
                            -amount, timestamp, description
                        ));
                    }
                }

                
                else if (command.equals("show-account")) {
                    String username = parts[1];

                    Account target = null;
                    for (Account acc : accounts) {
                        if (acc.getUsername().equals(username)) {
                            target = acc;
                            break;
                        }
                    }

                    if (target != null) {
                       
                        System.out.println(
                            target.getUsername() + "|" +
                            target.getName() + "|" +
                            target.getBalance()
                        );

                        
                        ArrayList<TxRecord> userTx = new ArrayList<>();
                        for (TxRecord t : records) {
                            if (t.username.equals(username)) {
                                userTx.add(t);
                            }
                        }

                      
                        Collections.sort(userTx, new Comparator<TxRecord>() {
                            public int compare(TxRecord a, TxRecord b) {
                                return a.timestamp.compareTo(b.timestamp);
                            }
                        });
                    

                        
                        for (TxRecord t : userTx) {
                            System.out.println(
                                t.id + "|" +
                                t.type + "|" +
                                t.amount + "|" +
                                t.timestamp + "|" +
                                t.description
                            );
                        }
                    }
                }

            } catch (NegativeBalanceException e) {

            }
        }
      
      scanner.close();
    }

}


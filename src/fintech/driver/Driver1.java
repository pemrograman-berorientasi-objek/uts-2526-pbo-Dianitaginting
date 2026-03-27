package fintech.driver;

import fintech.model.Account;
import java.util.*;

/**
 * @author 12S24044 Dianita Lorensia Br Ginting
 */

public class Driver1 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Account> accounts = new ArrayList<>();

        while (true) {
            String input = sc.nextLine();
            if (input.equals("---")) break;

            String[] p = input.split("#");

            if (p[0].equals("create-account")) {
                accounts.add(new Account(p[1], p[2]));
            }
        }

        for (Account acc : accounts) {
            System.out.println(acc.getUsername() + "|" + acc.getName() + "|" + acc.getBalance());
        }
    }
}




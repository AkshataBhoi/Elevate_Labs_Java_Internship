
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

class Account {

    String accNo;
    String accHolderName;
    double balance;
    ArrayList<Transaction> transactions = new ArrayList<Transaction>();

    public Account(String accountNo, String accHolder, double bal) {
        this.accNo = accountNo;
        this.accHolderName = accHolder;
        this.balance = bal;
    }

    void deposit(double amount) {
        System.out.println("Amount before Deposit: " + balance);
        if (amount > 0) {
            balance += amount;
            transactions.add(new Transaction("Deposit", amount));
            System.out.println("Deposited Amount: " + amount);
            System.out.println("Amount after Deposit: " + balance);
        } else {
            System.out.println("Amount must be Positive");
        }
    }

    void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactions.add(new Transaction("Withdrawal", amount));
            System.out.println("Amount after Withdrawal: " + balance);
        } else {
            System.out.println("Amount is more than balance");
        }
    }

    public void printTransactionHistory() {
        System.out.println("Transaction History:");
        for (Transaction t : transactions) {
            System.out.println(t);
        }

    }

    public double getBalance() {
        return balance;
    }
}

class Transaction {

    String type;
    double amount;
    Date date;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
        this.date = new Date();
    }

    @Override
    public String toString() {
        return "Transaction{"
                + "type='" + type + '\''
                + ", amount=" + amount
                + ", date=" + date
                + '}';
    }

}

class SavingAccount extends Account {

    double withdrawalLimit;

    public SavingAccount(String accountNo, String accHolder, double bal, double withdrawalLimit) {
        super(accountNo, accHolder, bal); // Call the constructor of BankAccount
        this.withdrawalLimit = withdrawalLimit;
    }

    @Override
    public void withdraw(double amount) {
        double maxWithdrawableAmount = getBalance() * withdrawalLimit;
        if (amount > 0 && amount <= maxWithdrawableAmount) {
            super.withdraw(amount); // Call the withdraw method of BankAccount
        } else {
            System.out.println("Withdrawal amount exceeds the limit of " + (withdrawalLimit * 100) + "% of the current balance.");
        }
    }
}

public class BankAccount {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Account No: ");
        String accNo = sc.nextLine();

        System.out.print("Enter Account Holder Name: ");
        String accHolder = sc.nextLine();

        System.out.print("Enter Total Balance: ");
        Double balance = sc.nextDouble();

        System.out.print("Enter WithDraw Limit: ");
        Double withdrawLimit = sc.nextDouble();

        SavingAccount sa = new SavingAccount(accNo, accHolder, balance, withdrawLimit);
        while (true) {
            System.out.println("************ Bank Account Operations **********");
            System.out.println("\n1. Deposit from Account \t 2.Withdraw From Account \t 3. Print Transaction History \t 4. Exit\n");
            System.out.print("Enter What u want to perform?");
            
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Enter Amount to Deposit: ");
                    Double amount = sc.nextDouble();
                    sa.deposit(amount);
                    break;
                case 2:
                    System.out.print("Enter Amount to WithDraw: ");
                    Double amt = sc.nextDouble();
                    sa.withdraw(amt);
                    break;
                case 3:
                    sa.printTransactionHistory();
                    break;
                case 4:
                System.out.println("Exiting....");
                    System.exit(0);
                default:
                    System.out.println("Invalid!!!!");
            }
        }
    }
}

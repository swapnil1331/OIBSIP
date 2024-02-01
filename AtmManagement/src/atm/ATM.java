package atm;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Account {
    private String userId;
    private String pin;
    private double balance;
    private StringBuilder transactionHistory;

    public Account(String userId, String pin) {
        this.userId = userId;
        this.pin = pin;
        this.balance = 0.0;
        this.transactionHistory = new StringBuilder("Transaction History:\n");
    }

    public String getUserId() {
        return userId;
    }

    public boolean checkPin(String enteredPin) {
        return pin.equals(enteredPin);
    }

    public double getBalance() {
        return balance;
    }

    public String getTransactionHistory() {
        return transactionHistory.toString();
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.append("Deposit: +").append(amount).append("\n");
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactionHistory.append("Withdrawal: -").append(amount).append("\n");
            return true;
        } else {
            System.out.println("Insufficient funds.");
            return false;
        }
    }

    public boolean transfer(Account recipient, double amount) {
        if (withdraw(amount)) {
            recipient.deposit(amount);
            transactionHistory.append("Transfer to ").append(recipient.getUserId()).append(": -").append(amount).append("\n");
            return true;
        } else {
            System.out.println("Transfer failed. Insufficient funds.");
            return false;
        }
    }
}

class Bank {
    private Map<String, Account> accounts;

    public Bank() {
        this.accounts = new HashMap<>();
    }

    public void addAccount(Account account) {
        accounts.put(account.getUserId(), account);
    }

    public Account getAccount(String userId) {
        return accounts.get(userId);
    }

    public boolean isValidUser(String userId, String pin) {
        Account account = accounts.get(userId);
        return account != null && account.checkPin(pin);
    }
}

public class ATM {
    private static Bank bank;

    public static void main(String[] args) {
        initializeBank();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter user id: ");
            String userId = scanner.nextLine();

            if (bank.isValidUser(userId, getPinFromUser())) {
                performTransactions(userId, scanner);
            } else {
                System.out.println("Invalid user id or pin. Try again.");
            }
        }
    }

    private static void initializeBank() {
        bank = new Bank();
        bank.addAccount(new Account("user1", "1234"));
        bank.addAccount(new Account("user2", "5678"));
    }

    private static String getPinFromUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter pin: ");
        return scanner.nextLine();
    }

    private static void performTransactions(String userId, Scanner scanner) {
        Account currentUser = bank.getAccount(userId);

        while (true) {
            displayMenu();
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    checkBalance(currentUser);
                    break;
                case 2:
                    deposit(currentUser, scanner);
                    break;
                case 3:
                    withdraw(currentUser, scanner);
                    break;
                case 4:
                    transfer(currentUser, scanner);
                    break;
                case 5:
                    viewTransactionHistory(currentUser);
                    break;
                case 6:
                    System.out.println("Exiting ATM. Have a nice day!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\nATM Menu:");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer");
        System.out.println("5. Transaction History");
        System.out.println("6. Quit");
        System.out.println("Enter your choice: ");
    }

    private static void checkBalance(Account account) {
        System.out.println("Balance: $" + account.getBalance());
    }

    private static void deposit(Account account, Scanner scanner) {
        System.out.println("Enter deposit amount: ");
        double depositAmount = scanner.nextDouble();
        account.deposit(depositAmount);
        System.out.println("Deposit successful. New balance: $" + account.getBalance());
    }

    private static void withdraw(Account account, Scanner scanner) {
        System.out.println("Enter withdrawal amount: ");
        double withdrawalAmount = scanner.nextDouble();
        if (account.withdraw(withdrawalAmount)) {
            System.out.println("Withdrawal successful. New balance: $" + account.getBalance());
        }
    }

    private static void transfer(Account sourceAccount, Scanner scanner) {
        System.out.println("Enter recipient user id: ");
        String recipientUserId = scanner.next();
        Account recipientAccount = bank.getAccount(recipientUserId);

        if (recipientAccount != null) {
            System.out.println("Enter transfer amount: ");
            double transferAmount = scanner.nextDouble();

            if (sourceAccount.transfer(recipientAccount, transferAmount)) {
                System.out.println("Transfer successful. New balance: $" + sourceAccount.getBalance());
            }
        } else {
            System.out.println("Recipient user not found.");
        }
    }

    private static void viewTransactionHistory(Account account) {
        System.out.println(account.getTransactionHistory());
    }
}


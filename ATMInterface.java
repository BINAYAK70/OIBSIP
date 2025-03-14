import java.util.Scanner;

class User {
    String UserID;
    String PIN;
    float balance;
    String[] TransactionHistory;
    int NoOfTransactions;

    public User(String UserID, String PIN, float balance) {
        this.UserID = UserID;
        this.PIN = PIN;
        this.balance = balance;
        this.TransactionHistory = new String[5];
        this.NoOfTransactions = 0;
    }

    boolean CheckPIN(String EnteredPIN) {
        return this.PIN.equals(EnteredPIN);
    }

    double GetBalance() {
        return balance;
    }

    void UpdateBalance(double amount) {
        this.balance += amount;
    }

    void Transaction(String TransactionDetails) {
        if (NoOfTransactions < 5) {
            TransactionHistory[NoOfTransactions++] = TransactionDetails;
        } else {
            for (int i = 1; i < 5; i++) {
                TransactionHistory[i - 1] = TransactionHistory[i];
            }
            TransactionHistory[4] = TransactionDetails;
        }
    }

    public void DisplayTransactionHistory() {
        System.out.println("Your last Transaction History:");
        for (String t : TransactionHistory) {
            if (t != null) {
                System.out.println(t);
            }
        }
    }

    public String getUserID() {
        return UserID;
    }
}

class ATM {
    User[] user;

    public ATM() {
        user = new User[5];
        user[0] = new User("ASISHCHOWBEY", "2002", 5632);
        user[1] = new User("BANKIMRAY", "1983", 1200);
        user[2] = new User("CHETANPANDEY", "2164", 4500);
        user[3] = new User("DEEPAKAHUJA", "4579", 3000);
        user[4] = new User("ENJAMANHAQUE", "7845", 9000);
    }

    public User Authentication(String UserID, String PIN) {
        for (User data : user) {
            if (data != null && data.getUserID().equals(UserID) && data.CheckPIN(PIN)) {
                return data;
            }
        }
        return null;
    }

    public User FindUser(String UserID) {
        for (User data : user) {
            if (data != null && data.getUserID().equals(UserID)) {
                return data;
            }
        }
        return null;
    }
}

class Transactions {
    public static void Withdraw(User user, double amount) {
        if (amount > 0 && amount <= user.GetBalance()) {
            user.UpdateBalance(-amount);
            user.Transaction("Withdrawn: ₹ " + amount);
            System.out.println("WITHDRAWAL SUCCESSFUL!!");
        } else {
            System.out.println("CANNOT BE WITHDRAWN DUE TO INSUFFICIENT BALANCE LEFT!!");
        }
    }

    public static void Deposit(User user, double amount) {
        if (amount > 0) {
            user.UpdateBalance(amount);
            user.Transaction("Deposited: ₹ " + amount);
            System.out.println("DEPOSIT SUCCESSFUL!!");
        } else {
            System.out.println("INVALID AMOUNT ENTERED!!");
        }
    }

    public static void Transfer(User sender, User receiver, double amount) {
        if (receiver == null) {
            System.out.println("Receiver NOT FOUND!!");
            return;
        }
        if (amount > 0 && amount <= sender.GetBalance()) {
            sender.UpdateBalance(-amount);
            receiver.UpdateBalance(amount);
            sender.Transaction("Transferred: ₹ " + amount + " to " + receiver.getUserID());
            receiver.Transaction("Received: ₹ " + amount + " from " + sender.getUserID());
            System.out.println("TRANSFER SUCCESSFUL!!");
        } else {
            System.out.println("CANNOT BE TRANSFERRED DUE TO INSUFFICIENT BALANCE!!");
        }
    }
}

class Operations {
    User CurrentUser;
    ATM atm;

    public Operations(ATM atm) {
        this.atm = atm;
    }

    public void BeginOperations() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your USER ID: ");
        String UserID = sc.next();
        System.out.print("Enter your PIN: ");
        String PIN = sc.next();
        CurrentUser = atm.Authentication(UserID, PIN);
        if (CurrentUser == null) {
            System.out.println("OOPS! INVALID CREDENTIALS! PLEASE TRY AGAIN!");
            return;
        }
        System.out.println("HURRAY! LOGIN SUCCESSFUL! WELCOME " + CurrentUser.getUserID() + " !");
        while (true) {
            System.out.println("\nPlease select the operation you want to perform!");
            System.out.println("1. DISPLAY TRANSACTION HISTORY");
            System.out.println("2. WITHDRAW CASH");
            System.out.println("3. DEPOSIT CASH");
            System.out.println("4. TRANSFER CASH");
            System.out.println("5. EXIT");
            System.out.print("Enter the serial no of the operation you want to perform: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    CurrentUser.DisplayTransactionHistory();
                    break;
                case 2:
                    System.out.print("Enter amount you want to WITHDRAW: ₹ ");
                    double withdrawnAmount = sc.nextDouble();
                    Transactions.Withdraw(CurrentUser, withdrawnAmount);
                    break;
                case 3:
                    System.out.print("Enter amount to DEPOSIT: ₹ ");
                    double depositedAmount = sc.nextDouble();
                    Transactions.Deposit(CurrentUser, depositedAmount);
                    break;
                case 4:
                    System.out.print("Enter receiver USER ID: ");
                    String ReceiverID = sc.next();
                    User receiver = atm.FindUser(ReceiverID);
                    if (receiver == null) {
                        System.out.println("RECEIVER NOT FOUND!!");
                        break;
                    }
                    System.out.print("Enter amount to TRANSFER: ₹ ");
                    double transferredAmount = sc.nextDouble();
                    Transactions.Transfer(CurrentUser, receiver, transferredAmount);
                    break;
                case 5:
                    System.out.println("THANK YOU! PLEASE VISIT AGAIN!");
                    return;
                default:
                    System.out.println("INVALID CHOICE! PLEASE TRY AGAIN!");
            }
        }
    }
}

public class ATMInterface {
    public static void main(String[] args) {
        ATM atm = new ATM();
        Operations operations = new Operations(atm);
        operations.BeginOperations();
    }
}

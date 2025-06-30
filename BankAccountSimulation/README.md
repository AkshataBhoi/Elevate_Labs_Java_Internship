# Bank Account Simulation

This project is a **Java-based Bank Account Simulation**.
It demonstrates the use of core object-oriented programming concepts including **classes, inheritance, method overriding, and encapsulation**.

---

## 🚀 Features

✅ Create a **Savings Account** with:
- Account Number
- Account Holder Name
- Initial Balance
- Withdrawal Limit Percentage

✅ **Deposit Funds**
- Adds to the account balance
- Records a transaction with date and type

✅ **Withdraw Funds**
- Checks withdrawal limits
- Updates balance
- Records a transaction with date and time

✅ **View Transaction History**
- Displays all deposits and withdrawals with timestamps

✅ **Interactive Console Menu**
- Allows the user to perform operations repeatedly

---

## 🛠️ Technologies Used

- Java
- Java Collections (`ArrayList`)
- Java Date API (`java.util.Date`)
- Command-line interaction (`Scanner`)

---

## 💡 OOP Concepts Demonstrated

- **Classes and Objects:** Modeling Accounts and Transactions
- **Inheritance:** `SavingAccount` extends `Account`
- **Method Overriding:** Custom withdrawal rules for savings account
- **Encapsulation:** Data hidden behind methods
- **Polymorphism:** Overridden `withdraw()` behavior

---

## 🧩 How It Works

1. **Account Creation**
   - The user enters account details (number, name, balance, withdrawal limit).

2. **Menu Options**
   - Deposit: Enter an amount to add.
   - Withdraw: Enter an amount to withdraw within the allowed  limit.
   - Print Transaction History: Lists all past deposits and withdrawals.
   - Exit: Closes the simulation.

3. **Transaction Recording**
   - Each deposit or withdrawal creates a `Transaction` object stored in an `ArrayList`.
   
---




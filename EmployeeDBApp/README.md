# Employee Database App – Java JDBC

This project is a simple Java console application demonstrating how to connect Java with MySQL using JDBC and perform CRUD (Create, Read, Update, Delete) 
operations on an `employee` table.

---

## 📂 Project Structure

```
EmployeeDBApp/
├── DBConnection.java
├── DBOperation.java
├── lib/
│   └── mysql-connector-j-9.3.0.jar
```

---

## 🛠 Requirements

- **Java Development Kit (JDK)** installed (Java 8+ recommended)
- **MySQL Server** installed and running
- MySQL JDBC Driver (`mysql-connector-j-9.3.0.jar`)
- VS Code terminal

---

## 🗄 Database Setup

1. **Create the database:**
   ```sql
   CREATE DATABASE jdbcpractice;
   ```

2. **Create the employee table:**
   ```sql
   CREATE TABLE employee (
       Id INT PRIMARY KEY,
       Name VARCHAR(50),
       Position VARCHAR(50),
       Salary DOUBLE
   );
   ```

---


## 🖥️ Compilation & Execution

### 1️⃣ Compile

Open your terminal **in the folder containing `.java` files**, and run:

**Windows:**
```
javac -cp ".;lib/mysql-connector-j-9.3.0.jar" *.java
```
---

### 2️⃣ Run the Application

**Windows:**
```
java -cp ".;lib/mysql-connector-j-9.3.0.jar" DBOperation
```

---

## 🧑‍💻 How to Use

When you run the program, you will see a menu:

```
Employee Database App
1. Add     2. View     3. Update     4. Delete     5. Exit
Enter your Choice:
```

- **Add:** Insert a new employee record.
- **View:** List all employee records.
- **Update:** Update existing employee details.
- **Delete:** Remove an employee record by ID.
- **Exit:** Closes the database connection and exits.

---

## 🔒 How Connections and Statements Are Managed

- `DBConnection` manages the JDBC connection.
- `PreparedStatement` is used to prevent SQL injection.
- All statements and result sets are closed automatically using **try-with-resources**.
- The connection is closed cleanly when you exit.

---


## 📌 Notes

- Make sure your MySQL server is running before you start the app.
- If you see `ClassNotFoundException` or `No suitable driver`, verify your JDBC JAR path and classpath.

---

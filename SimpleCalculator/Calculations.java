
import java.util.Scanner;

public class Calculations {

    public static int addition(int a, int b) {
        return (a + b);
    }

    public static int subtraction(int a, int b) {
        return (a - b);
    }

    public static int multiply(int a, int b) {
        return (a * b);
    }

    public static double divide(int a, int b) {
        double res = 0;
        try {
            res = a / b;
        } catch (ArithmeticException e) {
            System.out.println("Divide By Zero Error!!!");
        }
        return res;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int number1, number2;
        String operator, choice = null;

        do {
            System.out.println("\n****************Simple Calculator*******************\n");
            System.out.print("Enter First Number: ");
            number1 = sc.nextInt();

            System.out.print("Enter Second Number: ");
            number2 = sc.nextInt();

            System.out.print("Enter Opertor [+,-,*,/]: ");
            operator = sc.next();
            double result = 0;
            switch (operator) {
                case "+":
                    result = addition(number1, number2);
                    break;
                case "-":
                    result = subtraction(number1, number2);
                    break;
                case "*":
                    result = multiply(number1, number2);
                    break;
                case "/":
                    result = divide(number1, number2);
                    break;
                default:
                    System.out.println("Invalid Entry!!!");
                    continue;
            }
            System.out.println("Result is: " + result);

            System.out.println("Do you wnat to continue ? [yes/no OR y/n]: ");
            choice = sc.next().toLowerCase();
        } while (choice.equals("yes") || choice.equals("y"));
        System.out.println("Thank You...");
    }
}

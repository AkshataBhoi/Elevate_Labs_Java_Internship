
import java.util.ArrayList;
import java.util.Scanner;

class Student {

    int id;
    String name;
    int marks;

    public Student(int sid, String sname, int smarks) {
        this.id = sid;
        this.name = sname;
        this.marks = smarks;
    }

    void diplayDetails() {
        System.out.println("ID: " + this.id + "\t" + "Name: " + this.name + "\t" + "Marks: " + this.marks);
    }

}

public class StudentMenu {

    static ArrayList<Student> students = new ArrayList<Student>();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("************Student Record System**********");
            System.out.println("Enter Your Choice for CRUD operations:");
            System.out.println("1. Add Values \t 2. View Records \t 3. Update the Record \t 4. Delete the Record \t5. Exit");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Adding Values!!!\n");
                    addValues(sc);
                    break;
                case 2:
                    System.out.println("Displaying Values!!!\n");
                    displayValues();
                    break;
                case 3:
                    System.out.println("Updating Values!!!\n");
                    updateValues(sc);
                    break;
                case 4:
                    System.out.println("Deleting Values!!!\n");
                    deleteValues(sc);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid Entry!!!!!");
            }
            // sc.close();
        }
    }

    static void addValues(Scanner sc) {

        System.out.print("Enter Student Id:");
        int studId = sc.nextInt();

        System.out.print("Enter Student Name:");
        String studName = sc.next();

        System.out.print("Enter Student Marks:");
        int studMarks = sc.nextInt();
        students.add(new Student(studId, studName, studMarks));
    }

    static void displayValues() {
        if (students.isEmpty()) {
            System.out.println("No Records Found!!!");
        } else {
            for (Student student : students) {
                student.diplayDetails();;
            }
        }
    }

    static void updateValues(Scanner sc) {
        System.out.print("Enter Student Id:");
        int studId = sc.nextInt();
        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            if (s.id == studId) {
                System.out.print("Enter Student New Name:");
                String studNewName = sc.next();

                System.out.print("Enter Student New Marks:");
                int studNewMarks = sc.nextInt();
                students.set(i, new Student(studId, studNewName, studNewMarks));
            } else {
                System.out.println("No Records Found!!!");
            }
        }

    }

    static void deleteValues(Scanner sc) {
        System.out.print("Enter Student Id:");
        int studId = sc.nextInt();
        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            if (s.id == studId) {
                System.out.print("Deleting Elements!\n");
                students.remove(i);
            } else {
                System.out.println("No Records Found!!!");
            }
        }

    }
}

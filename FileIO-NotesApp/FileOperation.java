
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileOperation {

    static File obj;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("************ File Oprations ***********");
            System.out.println("1. Create File \t 2. Write to File \t 3. Read From File \t 4. Delete the file \t 5. Exit \n");
            System.out.print("Enter Your Choice of File operations: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("Enter File name u want to create:");
                    String fileName = sc.nextLine();
                    try {
                        obj = new File(fileName);
                        if (obj.createNewFile()) {
                            System.out.println("File Create:" + obj.getName());
                        } else {
                            System.out.println("File already Exists!");
                        }
                    } catch (Exception e) {
                        System.out.println("Error Creating File!!!");
                    }

                    break;
                case 2:
                    System.out.println("Enter Contents of file:");
                    String contents = sc.nextLine();
                    try {
                        FileWriter fileW = new FileWriter(obj, true);
                        if (obj.exists()) {
                            fileW.write(contents + "\n");
                            fileW.close();
                            System.out.println("Written in file succesfully");
                        } else {
                            obj.createNewFile();
                            System.out.println("New File created: " + obj.getName());
                        }
                    } catch (Exception e) {
                        System.out.println("Error! Writing to File");
                    }
                    break;
                case 3:
                    System.out.println("Reading From file!");
                    if (obj.length() == 0) {
                        System.out.println("File is empty.");
                    } else {
                        try (BufferedReader br = new BufferedReader(new FileReader(obj))) {
                            String line;
                            System.out.println("File Contents:");
                            while ((line = br.readLine()) != null) {
                                System.out.println(line);
                            }
                        } catch (IOException e) {
                            System.out.println("Error Reading File: " + e.getMessage());
                        }
                    }
                    break;
                case 4:
                    try {
                        if (obj.exists()) {
                            System.out.println("File Exists You can deltet file");
                            obj.delete();
                            System.out.println("Deleted file: " + obj.getName());
                        } else {
                            System.out.println("File Does Not Exist!");
                        }
                    } catch (Exception e) {
                        System.out.println("Error! Deleting File");
                    }
                    break;
                case 5:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Something went Wrong!!!!");
            }

        }
    }
}

package examples;

import mdb.ModelHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scan = new Scanner(System.in);
        int op = 0;

        while (true) {
            System.out.println();
            System.out.println("1. add course");
            System.out.println("2. list courses");
            System.out.println("3. search course");
            System.out.println("4. rename course");
            System.out.println("5. add teacher");
            System.out.println("6. list teachers");
            System.out.println("7. search teacher");
            System.out.println("8. add subject");
            System.out.println("9. list subjects");
            System.out.println("10. search subjects");
            System.out.println("11. exit");

            op = scan.nextInt();
            scan.nextLine();

            if (op == 1) {
                Course c = new Course();

                System.out.print("Course name: ");

                c.set("name", scan.nextLine());
                c.save();

                System.out.println("Done! ID: " + c.get("id"));
            }
            else if (op == 2) {
                ArrayList<Course> courses = ModelHelper.getAll(Course.class);

                for (Course c : courses) {
                    System.out.println("#" + c.get("id") + " " + c.get("name"));
                }
            }
            else if (op == 3) {
                System.out.print("Search query: ");

                ArrayList<Course> courses = ModelHelper.search(Course.class, "name", scan.nextLine());

                for (Course c : courses) {
                    System.out.println("#" + c.get("id") + " " + c.get("name"));
                }
            }
            else if (op == 4) {
                System.out.print("Course ID: ");

                Course c = new Course(scan.nextInt());
                scan.nextLine();

                System.out.print("Rename " + c.get("name") + " to: ");
                c.set("name", scan.nextLine());
                c.save();
                System.out.println("Done!");
            }
            else if (op == 5) {
                Teacher t = new Teacher();

                System.out.print("Teacher name: ");
                t.set("name", scan.nextLine());

                System.out.print("Teacher age: ");
                t.set("age", scan.nextInt());
                scan.nextLine();

                t.save();

                System.out.println("Done! ID: " + t.get("id"));
            }
            else if (op == 6) {
                ArrayList<Teacher> teachers = ModelHelper.getAll(Teacher.class);

                for (Teacher t : teachers) {
                    System.out.println("#" + t.get("id") + " " + t.get("name") + " " + t.get("age"));
                }
            }
            else if (op == 7) {
                System.out.print("Search query: ");

                ArrayList<Teacher> teachers = ModelHelper.search(Teacher.class, "name", scan.nextLine());

                for (Teacher t : teachers) {
                    System.out.println("#" + t.get("id") + " " + t.get("name") + " " + t.get("age"));
                }
            }
            else if (op == 8) {
                Subject s = new Subject();

                System.out.print("Subject name: ");
                s.set("name", scan.nextLine());

                System.out.print("Teacher ID: ");
                s.set("teacher_id", scan.nextInt());
                scan.nextLine();

                System.out.print("Course ID: ");
                s.set("course_id", scan.nextInt());
                scan.nextLine();

                s.save();

                System.out.println("Done! ID: " + s.get("id"));
            }
            else if (op == 9) {
                ArrayList<Subject> subjects = ModelHelper.getAll(Subject.class);

                for (Subject s : subjects) {
                    System.out.println();
                    System.out.println("#" + s.get("id") + " " + s.get("name"));
                    System.out.println("Course: " + s.getCourse().get("name"));
                    System.out.println("Teacher: " + s.getTeacher().get("name"));
                }
            }
            else if (op == 10) {
                System.out.print("Search query: ");

                ArrayList<Subject> subjects = ModelHelper.search(Subject.class, "name", scan.nextLine());

                for (Subject s : subjects) {
                    System.out.println();
                    System.out.println("#" + s.get("id") + " " + s.get("name"));
                    System.out.println("Course: " + s.getCourse().get("name"));
                    System.out.println("Teacher: " + s.getTeacher().get("name"));
                }
            }
            else {
                break;
            }

            System.out.print("\npress ENTER to continue");
            scan.nextLine();
        }
    }
}

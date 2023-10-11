import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class University {
    static class Semester {
        private static int[] semester;
        static {
            semester = new int[8];
        }
        public static void incrementSemesterCount(int sem) {
            semester[sem - 1]++;
        }

        public static void printSemesterData() {
            System.out.println("Students per Semester");
            for (int i = 0; i < 8;i++) {
                System.out.println("Semester "+(i+1)+" : "+semester[i]);
            }
        }
    }

    class Course {
        String CourseID;
        String CourseName;
        int NumEnrolled;
    }
}

class Student {
    private int id;
    private String name;
    private int semester;
    private String dob;
    private List<String> courses;

    public Student(int id, String name, int semester, String dob, List<String> courses) {
        this.id = id;
        this.name = name;
        this.semester = semester;
        this.dob = dob;
        this.courses = courses;
        University.Semester.incrementSemesterCount(semester);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSemester() {
        return semester;
    }

    public String getDOB() {
        return dob;
    }

    public List<String> getCourses() {
        return courses;
    }
}

class ReadData {
    List<Student> students = new ArrayList<>();

    void ReadStudentData() {
        try {
            Scanner sc = new Scanner(new FileReader("StudentData.csv"));
            if (sc.hasNextLine())
                sc.nextLine();
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parts = line.split(",");

                if (parts.length == 5) {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    int semester = Integer.parseInt(parts[2].trim());
                    String dob = parts[3].trim();
                    String coursesStr = parts[4].trim();

                    // Remove quotes from the Courses field and split by comma
                    String[] coursesArray = coursesStr.replaceAll("\"", "").split("-");
                    List<String> courses = new ArrayList<>();
                    for (String course : coursesArray) {
                        courses.add(course.trim());
                    }

                    // Create a Student object and add it to the list
                    Student student = new Student(id, name, semester, dob, courses);
                    students.add(student);
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    void PrintStudentsData() {
        for (Student student : students) {
            System.out.println("ID: " + student.getId());
            System.out.println("Name: " + student.getName());
            System.out.println("Semester: " + student.getSemester());
            System.out.println("DOB: " + student.getDOB());
            System.out.println("Courses: " + student.getCourses());
            System.out.println();
        }
    }
}

class StudentArrangement {
    public static void main(String[] args) {
        ReadData readData = new ReadData();
        readData.ReadStudentData();
        readData.PrintStudentsData();
        University.Semester.printSemesterData();
    }
}

package default_package;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.util.Scanner;

public class RegistrationTest {

    private Registration reg;

    @BeforeEach
    public void setUp() throws IOException {
        reg = new Registration();

        // Create temporary test files
        createFile("StudentRegistration/models/Subjects.txt", 
            "CS101/Programming Basics/3.0\n" +
            "CS102/Data Structures/3.5\n" +
            "CS103/Algorithms/4.0"
        );

        createFile("StudentRegistration/models/Professions.txt", 
            "SW/Software_Engineering\n" +
            "IT/Information_Technology\n" +
            "CS/Computer_Science"
        );

        createFile("StudentRegistration/models/Exams.txt", 
            "SW01/CS101/85\n" +
            "SW01/CS102/75\n" +
            "SW01/CS103/55\n" +
            "IT02/CS101/90\n" +
            "IT02/CS102/60\n" +
            "CS03/CS101/70\n" +
            "CS03/CS103/50"
        );

        // Load data
        reg.loadSubjects();
        reg.loadMajors();
        reg.loadExams();
    }

    @AfterEach
    public void tearDown() {
        new File("StudentRegistration/models/Subjects.txt").delete();
        new File("StudentRegistration/models/Professions.txt").delete();
        new File("StudentRegistration/models/Exams.txt").delete();
    }

    private void createFile(String path, String content) throws IOException {
        File file = new File(path);
        file.getParentFile().mkdirs();
        try (PrintWriter pw = new PrintWriter(file)) {
            pw.print(content);
        }
    }

    @Test
    public void testSubjectsLoaded() {
        assertEquals(3, reg.subjectList.size());
        Subject s = (Subject) reg.subjectList.get(0);
        assertEquals("CS101", s.code);
        assertEquals("Programming Basics", s.name);
        assertEquals(3.0f, s.credit);
    }

    @Test
    public void testMajorsLoaded() {
        assertEquals(3, reg.majorList.size());
        Major m = (Major) reg.majorList.get(0);
        assertEquals("SW", m.code);
        assertEquals("Software_Engineering", m.name);
    }

    @Test
    public void testStudentsLoadedAndGPA() {
        assertEquals(3, reg.studentList.size());
        Student sw = (Student) reg.studentList.get(0);
        assertEquals("SW01", sw.code);
        assertTrue(sw.GPA > 0);
    }

    @Test
    public void testFailingStudents() {
        // No student has 3 or more Fs in this small test data
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        reg.displayFailingStudents();
        assertTrue(out.toString().contains("=== Students with F"));
    }

    @Test
    public void testAddExamRecord() {
        Scanner sc = new Scanner("SW01\nCS103\n95\n");
        reg.addExamRecord(sc);

        Student s = (Student) reg.findStudent("SW01");
        Lessons l = (Lessons) s.lessons.get(s.lessons.size() - 1);
        assertEquals("CS103", l.learned.code);
        assertEquals(95, l.score);
    }

    @Test
    public void testDeleteExamRecord() {
        Scanner sc = new Scanner("SW01\nCS101\n");
        reg.deleteExamRecord(sc);

        Student s = (Student) reg.findStudent("SW01");

        boolean exists = false;
        for (int i = 0; i < s.lessons.size(); i++) {
            Lessons l = (Lessons) s.lessons.get(i);  // cast here
            if (l.learned.code.equals("CS101")) {
                exists = true;
                break;
            }
        }

        assertFalse(exists);
    }


    @Test
    public void testGradesBySubject() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        reg.displayGradesBySubject();
        assertTrue(out.toString().contains("CS101"));
    }

    @Test
    public void testGradesByMajor() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        reg.displayGradesByMajor();
        assertTrue(out.toString().contains("Major: SW"));
    }
}
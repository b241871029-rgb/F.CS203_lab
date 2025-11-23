package default_package;

import java.io.*;
import java.util.Scanner;
import dataStructures.ArrayLinearList;

public class Registration {

	public ArrayLinearList studentList;
	public ArrayLinearList subjectList;
	public ArrayLinearList majorList;

	public Registration() {
		studentList = new ArrayLinearList();
		subjectList = new ArrayLinearList();
		majorList = new ArrayLinearList();
	}

	public void loadSubjects() {
		try {
			BufferedReader input = new BufferedReader(new FileReader("StudentRegistration/models/Subjects.txt"));
			String line;
			while ((line = input.readLine()) != null) {
				String[] values = line.split("/");
				Subject s = new Subject(values[0], values[1], Float.parseFloat(values[2]));
				subjectList.add(subjectList.size(), s);
			}
			input.close();
		} catch (Exception e) {
			System.out.println("Error loading subjects: " + e.getMessage());
		}
	}

	public void loadMajors() {
		try {
			BufferedReader input = new BufferedReader(new FileReader("StudentRegistration/models/Professions.txt"));
			String line;
			while ((line = input.readLine()) != null) {
				String[] values = line.split("/");
				Major m = new Major(values[0], values[1]);
				majorList.add(majorList.size(), m);
			}
			input.close();
		} catch (Exception e) {
			System.out.println("Error loading majors: " + e.getMessage());
		}
	}

	public void loadExams() {
		try {
			BufferedReader input = new BufferedReader(new FileReader("StudentRegistration/models/Exams.txt"));
			String line;
			while ((line = input.readLine()) != null) {
				String[] values = line.split("/");
				String studentCode = values[0];
				String lessonCode = values[1];
				int score = Integer.parseInt(values[2]);

				Student student = findStudent(studentCode);
				if (student == null) {
					student = new Student(studentCode);
					studentList.add(studentList.size(), student);
				}

				Subject subject = findSubject(lessonCode);
				if (subject != null) {
					student.lessons.add(student.lessons.size(), new Lessons(subject, score));
					student.calculateGPA();
				}
			}
			input.close();
		} catch (Exception e) {
			System.out.println("Error loading exams: " + e.getMessage());
		}
	}

	// === FIND HELPERS ===
	public Student findStudent(String code) {
	    for (int i = 0; i < studentList.size(); i++) {
	        Student s = (Student) studentList.get(i);
	        if (s.code.equals(code))
	            return s;
	    }
	    return null;
	}


	private Subject findSubject(String code) {
		for (int i = 0; i < subjectList.size(); i++) {
			Subject s = (Subject) subjectList.get(i);
			if (s.code.equals(code))
				return s;
		}
		return null;
	}

	// === DISPLAY FUNCTIONS ===
	public void displaySubjects() {
		System.out.println("=== Subjects ===");
		for (int i = 0; i < subjectList.size(); i++) {
			System.out.println(subjectList.get(i));
		}
	}

	public void displayMajors() {
		System.out.println("=== Majors ===");
		for (int i = 0; i < majorList.size(); i++) {
			System.out.println(majorList.get(i));
		}
	}

	public void displayStudentsGPA() {
		System.out.println("=== Students GPA ===");
		for (int i = 0; i < studentList.size(); i++) {
			System.out.println(studentList.get(i));
		}
	}

	public void displayFailingStudents() {
		System.out.println("=== Students with F in 3 or more courses ===");
		for (int i = 0; i < studentList.size(); i++) {
			Student s = (Student) studentList.get(i);
			int failCount = 0;
			for (int j = 0; j < s.lessons.size(); j++) {
				Lessons l = (Lessons) s.lessons.get(j);
				if (l.getGrade().equals("F"))
					failCount++;
			}
			if (failCount >= 3)
				System.out.println(s.code);
		}
	}

	public void displayGradesBySubject() {
		System.out.println("=== Grades by Subject ===");
		for (int i = 0; i < subjectList.size(); i++) {
			Subject sub = (Subject) subjectList.get(i);
			System.out.println("Subject: " + sub.code);
			for (int j = 0; j < studentList.size(); j++) {
				Student s = (Student) studentList.get(j);
				for (int k = 0; k < s.lessons.size(); k++) {
					Lessons l = (Lessons) s.lessons.get(k);
					if (l.learned.code.equals(sub.code))
						System.out.println("  " + s.code + " - " + l.getGrade());
				}
			}
		}
	}

	public void displayGradesByMajor() {
		System.out.println("=== Grades by Major ===");
		for (int i = 0; i < majorList.size(); i++) {
			Major m = (Major) majorList.get(i);
			System.out.println("Major: " + m.code);
			for (int j = 0; j < studentList.size(); j++) {
				Student s = (Student) studentList.get(j);
				if (s.code.startsWith(m.code)) {
					System.out.print(s.code + " - ");
					for (int k = 0; k < s.lessons.size(); k++) {
						Lessons l = (Lessons) s.lessons.get(k);
						System.out.print(l.learned.code + ":" + l.getGrade() + " ");
					}
					System.out.println();
				}
			}
		}
	}

	// === ADD / DELETE EXAM RECORDS ===
	public void addExamRecord(Scanner sc) {
		try {
			System.out.print("Enter Student Code: ");
			String studentCode = sc.nextLine();
			System.out.print("Enter Subject Code: ");
			String subjectCode = sc.nextLine();
			System.out.print("Enter Score (0–100): ");
			int score = Integer.parseInt(sc.nextLine());

			Student student = findStudent(studentCode);
			if (student == null) {
				student = new Student(studentCode);
				studentList.add(studentList.size(), student);
			}

			Subject subject = findSubject(subjectCode);
			if (subject == null) {
				System.out.println("❌ Subject not found!");
				return;
			}

			student.lessons.add(student.lessons.size(), new Lessons(subject, score));
			student.calculateGPA();

		} catch (Exception e) {
			System.out.println("Error adding exam record: " + e.getMessage());
		}
	}

	public void deleteExamRecord(Scanner sc) {
		try {
			System.out.print("Enter Student Code to delete exam: ");
			String studentCode = sc.nextLine();
			Student student = findStudent(studentCode);
			if (student == null) {
				System.out.println("Student not found!");
				return;
			}

			System.out.print("Enter Subject Code to delete: ");
			String subjectCode = sc.nextLine();

			for (int i = 0; i < student.lessons.size(); i++) {
				Lessons l = (Lessons) student.lessons.get(i);
				if (l.learned.code.equals(subjectCode)) {
					student.lessons.remove(i);
					System.out.println("Exam record deleted.");
					student.calculateGPA();
					return;
				}
			}
			System.out.println("Exam record not found.");
		} catch (Exception e) {
			System.out.println("Error deleting exam record: " + e.getMessage());
		}
	}
}

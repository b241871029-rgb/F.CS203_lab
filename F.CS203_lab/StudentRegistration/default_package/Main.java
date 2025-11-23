package default_package;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Registration reg = new Registration();
		reg.loadSubjects();
		reg.loadMajors();
		reg.loadExams();

		Scanner sc = new Scanner(System.in);
		int choice;

		do {
			System.out.println("\n===== OYUTNII BURTGELIIN MENU =====");
			System.out.println("1. Buh hicheeluudiig haruulah");
			System.out.println("2. Buh mergejluudiig haruulah");
			System.out.println("3. Buh oyutnuudiin golch dung haruulah");
			System.out.println("4. 3 ba tuunees deesh F avsan oyutnuudiig haruulah");
			System.out.println("5. Hicheel bureer dun haruulah");
			System.out.println("6. Mergejl bureer dun haruulah");
			System.out.println("7. Shine shalgaltiin onoo nemeh");
			System.out.println("8. Shalgaltiin onoo ustgah");
			System.out.println("0. Garah");
			System.out.print("Songoltoo oruulna uu: ");
			choice = sc.nextInt();
			sc.nextLine();

			switch (choice) {
			case 1 -> reg.displaySubjects();
			case 2 -> reg.displayMajors();
			case 3 -> reg.displayStudentsGPA();
			case 4 -> reg.displayFailingStudents();
			case 5 -> reg.displayGradesBySubject();
			case 6 -> reg.displayGradesByMajor();
			case 7 -> reg.addExamRecord(sc);
			case 8 -> reg.deleteExamRecord(sc);
			case 0 -> System.out.println("Garch baina...");
			default -> System.out.println("Buruu songolt. Dahin oroldono uu!");
			}
		} while (choice != 0);
		sc.close();
	}
}

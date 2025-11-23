package default_package;

import dataStructures.Chain;

public class Student {
	public String code;
	public float GPA;
	public Chain lessons;

	public Student(String code) {
		this.code = code;
		this.GPA = 0;
		this.lessons = new Chain();
	}

	public void calculateGPA() {
		float totalPoints = 0;
		float totalCredits = 0;
		for (int i = 0; i < lessons.size(); i++) {
			Lessons l = (Lessons) lessons.get(i);
			totalPoints += l.getGPA() * l.learned.credit;
			totalCredits += l.learned.credit;
		}
		if (totalCredits != 0) {
			this.GPA = totalPoints / totalCredits;
		} else {
			this.GPA = 0;
		}
	}

	public String toString() {
		return code + " - GPA: " + GPA;
	}
}
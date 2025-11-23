package default_package;

public class Exam {
	private String studentCode;
	private String subjectCode;
	private float score;

	public Exam(String studentCode, String subjectCode, float score) {
		this.studentCode = studentCode;
		this.subjectCode = subjectCode;
		this.score = score;
	}

	public String getStudentCode() {
		return studentCode;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public float getScore() {
		return score;
	}
}

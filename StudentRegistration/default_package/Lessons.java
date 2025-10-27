package default_package;

public class Lessons {
    public Subject learned;
    public int score;

    public Lessons(Subject learned, int score) {
        this.learned = learned;
        this.score = score;
    }

    public float getGPA() {
        if (score >= 90) return 4.0f;
        else if (score >= 80) return 3.0f;
        else if (score >= 70) return 2.0f;
        else if (score >= 60) return 1.0f;
        else return 0.0f;
    }

    public String getGrade() {
        if (score >= 90) return "A";
        else if (score >= 80) return "B";
        else if (score >= 70) return "C";
        else if (score >= 60) return "D";
        else return "F";
    }
}

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class StudentRecordProcessor {
    // Поля для хранения данных
    private final List<Student> students = new ArrayList<>();

    // _____реализуйте класс Student ниже в этом же файле______

    private double averageScore;
    private Student highestStudent;

    private final String inputFile = "input/students.txt";
    private final String outputFile = "output/report.txt";
    /**
     * Task 1 + Task 2 + Task 5 + Task 6
     */
    public void readFile() {
        // TODO: реализуйте чтение файла здесь

            try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {

                String line;

                while ((line = br.readLine()) != null) {
                    try {
                        String[] parts = line.split(",");

                        if (parts.length != 2) {
                            System.out.println("Invalid format: " + line);
                            continue;
                        }
                        String name = parts[0];
                        int score = Integer.parseInt(parts[1]);

                        // Custom exception
                        if (score < 0 || score > 100) {
                            throw new InvalidScoreException("Invalid score: " + line);
                        }

                        students.add(new Student(name, score));
                        System.out.println("Valid: " + line);

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid data: " + line);
                    } catch (InvalidScoreException e) {
                        System.out.println(e.getMessage());
                    }
                }

            } catch (FileNotFoundException e) {
                System.out.println("File not found: " + inputFile);
            } catch (IOException e) {
                System.out.println("Error reading file.");
            }

    }

    /**
     * Task 3 + Task 8
     */
    public void processData() {
        // TODO: обработка данных и сортировка здесь
                if (students.isEmpty()) return;

                // сортировка по убыванию
                students.sort((a, b) -> b.getScore() - a.getScore());

                int sum = 0;
                highestStudent = students.get(0);

                for (Student s : students) {
                    sum += s.getScore();
                }

                averageScore = (double) sum / students.size();
            }


    /**
     * Task 4 + Task 5 + Task 8
     */
    public void writeFile() {
        // TODO: запись результата в файл здесь
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {

            bw.write("Average: " + averageScore);
            bw.newLine();

            bw.write("Highest: " + highestStudent.getName() + " - " + highestStudent.getScore());
            bw.newLine();
            bw.newLine();

            bw.write("Sorted Students (Descending):");
            bw.newLine();

            for (Student s : students) {
                bw.write(s.getName() + " - " + s.getScore());
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error writing file.");
        }

    }

    public static void main(String[] args) {
        StudentRecordProcessor processor = new StudentRecordProcessor();

        try {
            processor.readFile();
            processor.processData();
            processor.writeFile();
            System.out.println("Processing completed. Check output/report.txt");
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
}
// class InvalidScoreException реализуйте меня
// class Student (name, score)
class InvalidScoreException extends Exception {
    public InvalidScoreException(String message) {
        super(message);
    }
}
/**
 * Класс Student
 */
class Student {
    private String name;
    private int score;

    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}
package Checker;
import java.sql.Time;

public class Checker8 {
    public static void main(String[] args) {
        // String representing a time in HH:mm:ss format
        String timeString = "12:30:45";

        // Using Time.valueOf to convert the string to Time
        Time timeValue = Time.valueOf(timeString);
        System.out.println("Time using Time.valueOf: " + timeValue);
    }
}



import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SessionData {
    private int sessionID;
    private String date;
    private String time;
    private int countPerSession;
    private ArrayList<Integer> countPerLaps;

    public SessionData(ArrayList<Integer> countPerLaps, int countPerSession) {
        this.date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        this.sessionID = hasher(this.date + this.time);
        this.countPerSession = countPerSession;
        this.countPerLaps = countPerLaps;
    }

    public int getSessionID() {
        return sessionID;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getCountPerSession() {
        return countPerSession;
    }

    public ArrayList<Integer> getCountPerLaps() {
        return countPerLaps;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setCountPerSession(int countPerSession) {
        this.countPerSession = countPerSession;
    }

    public void setCountPerLaps(ArrayList<Integer> countPerLaps) {
        this.countPerLaps = countPerLaps;
    }

    public int hasher(String input) {
        int hashkey = 0;
        for (int i = 0; i < input.length(); i++) {
            hashkey = (hashkey * 31) + input.charAt(i);
        }
        return hashkey;
    }
}

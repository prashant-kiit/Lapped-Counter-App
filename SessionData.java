import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SessionData {
    private int sessionID;
    private String sessionName;
    private String date;
    private String time;
    private int countPerSession;
    private ArrayList<Integer> countPerLaps;

    // Serizalize it to a file
    public SessionData(String sessionName, ArrayList<Integer> countPerLaps, int countPerSession) {
        this.sessionName = sessionName;
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

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public int hasher(String input) {
        int hashkey = 0;
        for (int i = 0; i < input.length(); i++) {
            hashkey = (hashkey * 31) + input.charAt(i);
        }
        return hashkey;
    }
}

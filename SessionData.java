
import java.util.ArrayList;

public class SessionData {
    private Integer sessionID;
    private String sessionName;
    private String date;
    private String time;
    private Integer countPerSession;
    private ArrayList<Integer> countPerLaps;

    public SessionData() {
        
    }
    // Serizalize it to a file
    public SessionData(String sessionName, String date, String time, ArrayList<Integer> countPerLaps, int countPerSession) {
        this.sessionName = sessionName;
        this.date = date;
        this.time = time;
        this.countPerSession = countPerSession;
        this.sessionID = hasher(this.date + this.time);
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

    public void setSessionID(String date, String time) {
        this.sessionID = hasher(this.date + this.time);
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

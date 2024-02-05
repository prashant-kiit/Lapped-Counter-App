
import java.io.Serializable;
import java.util.ArrayList;

public class SessionData implements Serializable{
    private Integer sessionID;
    private String sessionName;
    private String date;
    private String time;
    private Integer countPerSession;
    private ArrayList<Integer> countPerLaps;

    public SessionData() {
        
    }

    public synchronized int getSessionID() {

        return sessionID;
    }

    public synchronized String getDate() {
        return date;
    }

    public synchronized String getTime() {
        return time;
    }

    public synchronized int getCountPerSession() {
        return countPerSession;
    }

    public synchronized ArrayList<Integer> getCountPerLaps() {
        return countPerLaps;
    }

    public synchronized void setSessionID(String date, String time) {
        this.sessionID = hasher(this.date + this.time);
    }

    public synchronized void setDate(String date) {
        this.date = date;
    }

    public synchronized void setTime(String time) {
        this.time = time;
    }

    public synchronized void setCountPerSession(int countPerSession) {
        this.countPerSession = countPerSession;
    }

    public synchronized void setCountPerLaps(ArrayList<Integer> countPerLaps) {
        this.countPerLaps = countPerLaps;
    }

    public synchronized String getSessionName() {
        return sessionName;
    }

    public synchronized void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public synchronized int hasher(String input) {
        int hashkey = 0;
        for (int i = 0; i < input.length(); i++) {
            hashkey = (hashkey * 31) + input.charAt(i);
        }
        return hashkey;
    }

    @Override
    public synchronized String toString() {
        return "SessionData [sessionID=" + sessionID + ", sessionName=" + sessionName + ", date=" + date + ", time="
                + time + ", countPerSession=" + countPerSession + ", countPerLaps=" + countPerLaps + "]";
    }

}

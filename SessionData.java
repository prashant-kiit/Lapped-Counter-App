
import java.io.Serializable;
import java.util.ArrayList;

public class SessionData implements Serializable {
    private Integer sessionID = null;
    private String sessionName = null;
    private String date = null;
    private String time = null;
    private Integer countPerSession = null;
    private ArrayList<Integer> countPerLaps = null;

    private int getHash(String input) {
        int hashkey = 0;
        for (int i = 0; i < input.length(); i++) {
            hashkey = (hashkey * 31) + input.charAt(i);
        }
        return hashkey >= 0 ? hashkey : ~hashkey + 1;
    }

    public SessionData() {

    }

    public int getSessionID() {
        return sessionID;
    }

    public String getSessionName() {
        return sessionName;
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

    public void setSessionID(String date, String time, String sessionName) {
        this.sessionID = getHash(this.date + this.time + sessionName);
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
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

    @Override
    public String toString() {
        return "SessionData [sessionID=" + sessionID + ", sessionName=" + sessionName + ", date=" + date + ", time="
                + time + ", countPerSession=" + countPerSession + ", countPerLaps=" + countPerLaps + "]";
    }

}

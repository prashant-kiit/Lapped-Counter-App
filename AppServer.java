import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AppServer {
    private ArrayList<SessionData> sessionDatas = null;
    private SessionData currentSessionData = null;

    public AppServer(Database database) {
        this.sessionDatas = database.getDatabase();
    }

    public void addCurrentSessionData(String sessionName) {
        this.currentSessionData = new SessionData();
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        this.currentSessionData.setSessionID(date, time, this.currentSessionData.getSessionName());
        this.currentSessionData.setSessionName(sessionName);
        this.currentSessionData.setDate(date);
        this.currentSessionData.setTime(time);
        this.currentSessionData.setCountPerSession(0);
        this.currentSessionData.setCountPerLaps(new ArrayList<>());
        this.addCurrentLap();
        this.sessionDatas.add(currentSessionData);
    }

    public void addCurrentLap() {
        this.currentSessionData.getCountPerLaps().add(0);
    }

    public void incrementLastLap() {
        ArrayList<Integer> temp = this.currentSessionData.getCountPerLaps();
        temp.set(temp.size() - 1, temp.get(temp.size() - 1) + 1);
        this.currentSessionData.setCountPerSession(this.currentSessionData.getCountPerSession() + 1);
    }

    public void decrementLastLap() {
        ArrayList<Integer> temp = this.currentSessionData.getCountPerLaps();
        if (temp.get(temp.size() - 1) > 0) {
            temp.set(temp.size() - 1, temp.get(temp.size() - 1) - 1);
            this.currentSessionData.setCountPerSession(this.currentSessionData.getCountPerSession() - 1);
        }
    }

    public void resetCurrentLapToZero() {
        ArrayList<Integer> temp = this.currentSessionData.getCountPerLaps();
        temp.set(temp.size() - 1, 0);
    }

    public Map<Integer,String> getSessionNameListFromDatabase() {
        Map<Integer,String> temp = new HashMap<>();
        for(SessionData sessionData : this.sessionDatas) {
            temp.put(sessionData.getSessionID(), sessionData.getSessionName());
        }
        return temp; 
    }

    public void editSessionName(Integer sessionId, String newSessionName) {
        for(SessionData sessionData : this.sessionDatas) {
            if(sessionId == sessionData.getSessionID()) {
                sessionData.setSessionName(newSessionName);
            }
        }
    }
}

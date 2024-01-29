
import java.util.ArrayList;

public class Database {
    private ArrayList<SessionData> sessionDatas = new ArrayList<SessionData>();

    public ArrayList<SessionData> getDatabase() {
        return sessionDatas;
    }

    public void setDatabase(ArrayList<SessionData> sessionDatas) {
        this.sessionDatas = sessionDatas;
    }

    public void save(SessionData sessionData) {
        sessionDatas.add(sessionData);
    }
}

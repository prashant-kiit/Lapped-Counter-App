
import java.io.Serializable;
import java.util.ArrayList;

public class Database implements Serializable{
    private ArrayList<SessionData> sessionDatas = new ArrayList<SessionData>();
    private static Database database = null;

    private Database() {
    }

    public synchronized ArrayList<SessionData> getDatabase() {
        return sessionDatas;
    }

    public synchronized void setDatabase(ArrayList<SessionData> sessionDatas) {
        this.sessionDatas = sessionDatas;
    }

    public synchronized void save(SessionData sessionData) {
        if (this.sessionDatas.contains(sessionData)) {
            this.sessionDatas.remove(sessionData);
        }
        sessionDatas.add(sessionData);
    }

    public synchronized static Database getInstance() {
        if (database == null) {
            database = new Database();
        }
        return database;
    }

    @Override
    public synchronized String toString() {
        return "Database [sessionDatas=" + sessionDatas + "]";
    }
}


import java.util.ArrayList;

public class Database {
    private ArrayList<SessionData> sessionDatas = new ArrayList<SessionData>();
    private static Database database = null;
    private Database() {}
    
    public ArrayList<SessionData> getDatabase() {
        return sessionDatas;
    }

    public void setDatabase(ArrayList<SessionData> sessionDatas) {
        this.sessionDatas = sessionDatas;
    }

    public void save(SessionData sessionData) {
        sessionDatas.add(sessionData);
    }

    public static Database getInstance() {
        if(database == null) {
            database = new Database();
        }
        return database;
    }
}

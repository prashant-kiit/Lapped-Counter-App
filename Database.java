
import java.io.Serializable;
import java.util.ArrayList;

public class Database implements Serializable{
    private static ArrayList<SessionData> sessionDatas = null;
    private static Database database = null;
    private Database() {}

    public synchronized static Database getInstance() {
        if (database == null) {
            database = new Database();
            sessionDatas = new ArrayList<SessionData>();
        }
        return database;
    }

    public synchronized ArrayList<SessionData> getDatabase() {
        return sessionDatas;
    }

    @Override
    public synchronized String toString() {
        return "Database [sessionDatas=" + sessionDatas + "]";
    }
}

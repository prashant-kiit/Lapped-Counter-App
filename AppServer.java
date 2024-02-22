import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



@FunctionalInterface
interface AppServerEndpoint {
    Message operate(String... parameters);
}

public class AppServer {
    private Database database = null;;
    private MessageQueue messageQueue = null;
    private DataWarehouse dataWarehouse = null;
    private Integer currentSessionDataIndex = null;

    public AppServer(Database database, MessageQueue messageQueue, DataWarehouse dataWarehouse) {
        this.database = database;
        this.messageQueue = messageQueue;
        this.dataWarehouse = dataWarehouse;
    }

    public AppServerEndpoint addCurrentSessionData = (String... parameters) -> {
        this.database.getDatabase().add(new SessionData());
        this.currentSessionDataIndex = this.database.getDatabase().size() - 1;
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        this.database.getDatabase().get(this.currentSessionDataIndex).setSessionID(date, time, parameters[0]);
        this.database.getDatabase().get(this.currentSessionDataIndex).setSessionName(parameters[0]);
        this.database.getDatabase().get(this.currentSessionDataIndex).setDate(date);
        this.database.getDatabase().get(this.currentSessionDataIndex).setTime(time);
        this.database.getDatabase().get(this.currentSessionDataIndex).setCountPerSession(0);
        this.database.getDatabase().get(this.currentSessionDataIndex).setCountPerLaps(new ArrayList<>());
        this.database.getDatabase().get(this.currentSessionDataIndex).getCountPerLaps().add(0);
        return new Message(this.database, this.currentSessionDataIndex, this.dataWarehouse.setSessionData);
    };

    public AppServerEndpoint addCurrentLap = (String... parameters) -> {
        this.database.getDatabase().get(this.currentSessionDataIndex).getCountPerLaps().add(0);
        return new Message(this.database, this.currentSessionDataIndex, this.dataWarehouse.setCountPerSession, this.dataWarehouse.setCountPerLaps);
    };

    public AppServerEndpoint incrementLastLap = (String... parameters) -> {
        ArrayList<Integer> temp = this.database.getDatabase().get(this.currentSessionDataIndex).getCountPerLaps();
        temp.set(temp.size() - 1, temp.get(temp.size() - 1) + 1);
        this.database.getDatabase().get(this.currentSessionDataIndex).setCountPerSession(this.database.getDatabase().get(this.currentSessionDataIndex).getCountPerSession() + 1);
        return new Message(this.database, this.currentSessionDataIndex, this.dataWarehouse.setCountPerSession, this.dataWarehouse.setCountPerLaps);
    };

    public AppServerEndpoint decrementLastLap = (String... parameters) -> {
        ArrayList<Integer> temp = this.database.getDatabase().get(this.currentSessionDataIndex).getCountPerLaps();
        if (temp.get(temp.size() - 1) > 0) {
            temp.set(temp.size() - 1, temp.get(temp.size() - 1) - 1);
            this.database.getDatabase().get(this.currentSessionDataIndex).setCountPerSession(this.database.getDatabase().get(this.currentSessionDataIndex).getCountPerSession() - 1);
        }
        return new Message(this.database, this.currentSessionDataIndex, this.dataWarehouse.setCountPerSession, this.dataWarehouse.setCountPerLaps);
    };

    public AppServerEndpoint resetCurrentLapToZero = (String... parameters) -> {
        ArrayList<Integer> temp = this.database.getDatabase().get(this.currentSessionDataIndex).getCountPerLaps();
        temp.set(temp.size() - 1, 0);
        return new Message(this.database, this.currentSessionDataIndex, this.dataWarehouse.setCountPerSession, this.dataWarehouse.setCountPerLaps);
    };

    public AppServerEndpoint editSessionName = (String... parameters) -> {
        Integer sessionId = Integer.parseInt(parameters[0]);
        for (SessionData sessionData : this.database.getDatabase()) {
            if (sessionId == sessionData.getSessionID()) {
                sessionData.setSessionName(parameters[1]);
            }
        }
        return new Message(this.database, this.currentSessionDataIndex, this.dataWarehouse.setSessionName);
    };

    public Map<Integer, String> getSessionNameListFromDatabase(String... parameters) {
        Map<Integer, String> sessionNameListFromDatabase = new HashMap<>();
        for (SessionData sessionData : this.database.getDatabase()) {
            sessionNameListFromDatabase.put(sessionData.getSessionID(), sessionData.getSessionName());
        }
        return sessionNameListFromDatabase;
    }

    public void execute(AppServerEndpoint appServerEndpoint, String... parameters) {
        Message message = appServerEndpoint.operate(parameters);
        this.messageQueue.load(message);
    }
}

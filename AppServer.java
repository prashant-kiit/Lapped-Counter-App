import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Message {
    public SessionData currentSessionData = null;
    public DataWarehouseAttributeLambda[] dataWarehouseAttributeLambdas = null;

    Message(SessionData currentSessionData, DataWarehouseAttributeLambda... dataWarehouseAttributeLambdas) {
        this.currentSessionData = currentSessionData;
        this.dataWarehouseAttributeLambdas = dataWarehouseAttributeLambdas;
    }

    @Override
    public String toString() {
        return "[ " + this.currentSessionData + " : " + Arrays.toString( this.dataWarehouseAttributeLambdas) + " ]";
    }
}

@FunctionalInterface
interface AppServerEndpoint {
    Message operate(String... parameters);
}

public class AppServer {
    private DataWarehouse dataWarehouse = null;
    private MessageQueue messageQueue = null;
    private ArrayList<SessionData> sessionDatas = null;
    private SessionData currentSessionData = null;
    @SuppressWarnings("unused")
    private static Integer currentSessionDataId = null;

    public AppServer(Database database, MessageQueue messageQueue, DataWarehouse dataWarehouse) {
        this.sessionDatas = database.getDatabase();
        this.messageQueue = messageQueue;
        this.dataWarehouse = dataWarehouse;
    }

    public AppServerEndpoint addCurrentSessionData = (String... parameters) -> {
        AppServer.currentSessionDataId = this.currentSessionData.getSessionID();
        this.currentSessionData = new SessionData();
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        this.currentSessionData.setSessionID(date, time, parameters[0]);
        this.currentSessionData.setSessionName(parameters[0]);
        this.currentSessionData.setDate(date);
        this.currentSessionData.setTime(time);
        this.currentSessionData.setCountPerSession(0);
        this.currentSessionData.setCountPerLaps(new ArrayList<>());
        this.currentSessionData.getCountPerLaps().add(0);
        this.sessionDatas.add(currentSessionData);
        return new Message(this.currentSessionData, this.dataWarehouse.setSessionData);
    };

    public AppServerEndpoint addCurrentLap = (String... parameters) -> {
        this.currentSessionData.getCountPerLaps().add(0);
        return new Message(this.currentSessionData, this.dataWarehouse.setCountPerSession, this.dataWarehouse.setCountPerLaps);
    };

    public AppServerEndpoint incrementLastLap = (String... parameters) -> {
        ArrayList<Integer> temp = this.currentSessionData.getCountPerLaps();
        temp.set(temp.size() - 1, temp.get(temp.size() - 1) + 1);
        this.currentSessionData.setCountPerSession(this.currentSessionData.getCountPerSession() + 1);
        return new Message(this.currentSessionData, this.dataWarehouse.setCountPerSession, this.dataWarehouse.setCountPerLaps);
    };

    public AppServerEndpoint decrementLastLap = (String... parameters) -> {
        ArrayList<Integer> temp = this.currentSessionData.getCountPerLaps();
        if (temp.get(temp.size() - 1) > 0) {
            temp.set(temp.size() - 1, temp.get(temp.size() - 1) - 1);
            this.currentSessionData.setCountPerSession(this.currentSessionData.getCountPerSession() - 1);
        }
        return new Message(this.currentSessionData, this.dataWarehouse.setCountPerSession, this.dataWarehouse.setCountPerLaps);
    };

    public AppServerEndpoint resetCurrentLapToZero = (String... parameters) -> {
        ArrayList<Integer> temp = this.currentSessionData.getCountPerLaps();
        temp.set(temp.size() - 1, 0);
        return new Message(this.currentSessionData, this.dataWarehouse.setCountPerSession, this.dataWarehouse.setCountPerLaps);
    };

    public AppServerEndpoint editSessionName = (String... parameters) -> {
        Integer sessionId = Integer.parseInt(parameters[0]);
        for (SessionData sessionData : this.sessionDatas) {
            if (sessionId == sessionData.getSessionID()) {
                sessionData.setSessionName(parameters[1]);
            }
        }
        return new Message(this.currentSessionData, this.dataWarehouse.setSessionName);
    };

    public Map<Integer, String> getSessionNameListFromDatabase(String... parameters) {
        Map<Integer, String> sessionNameListFromDatabase = new HashMap<>();
        for (SessionData sessionData : this.sessionDatas) {
            sessionNameListFromDatabase.put(sessionData.getSessionID(), sessionData.getSessionName());
        }
        return sessionNameListFromDatabase;
    }

    public void execute(AppServerEndpoint appServerEndpoint, String... parameters) {
        Message message = appServerEndpoint.operate(parameters);
        this.messageQueue.load(message);
    }
}

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Message {  
    private Endpoint endpoint = null;
    private Target target = null;
    
    static enum Endpoint {
        addCurrentSessionData, addCurrentLap, incrementLastLap, decrementLastLap, resetCurrentLapToZero, editSessionName
    };

    static enum Target {
        all, sessionName, lapPerSessionAndCountPerSession
    };

    Message(Endpoint endpoint, Target target) {
        this.endpoint = endpoint;
        this.target = target;
    }

    @Override
    public String toString() {
        return "[ " + this.endpoint + " : " + this.target + " ]";
    }
}

@FunctionalInterface
interface AppServerEndpoint {
    Message operate(String... parameters);
}

public class AppServer {
    private MessageQueue messageQueue = null;
    private ArrayList<SessionData> sessionDatas = null;
    private SessionData currentSessionData = null;

    public AppServer(Database database, MessageQueue messageQueue) {
        this.sessionDatas = database.getDatabase();
        this.messageQueue = messageQueue;
    }

    public AppServerEndpoint addCurrentSessionData = (String... parameters) -> {
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
        return new Message(Message.Endpoint.addCurrentSessionData, Message.Target.all);
    };

    public AppServerEndpoint addCurrentLap = (String... parameters) -> {
        this.currentSessionData.getCountPerLaps().add(0);
        return new Message(Message.Endpoint.addCurrentLap, Message.Target.lapPerSessionAndCountPerSession);
    };

    public AppServerEndpoint incrementLastLap = (String... parameters) -> {
        ArrayList<Integer> temp = this.currentSessionData.getCountPerLaps();
        temp.set(temp.size() - 1, temp.get(temp.size() - 1) + 1);
        this.currentSessionData.setCountPerSession(this.currentSessionData.getCountPerSession() + 1);
        return new Message(Message.Endpoint.incrementLastLap, Message.Target.lapPerSessionAndCountPerSession);
    };

    public AppServerEndpoint decrementLastLap = (String... parameters) -> {
        ArrayList<Integer> temp = this.currentSessionData.getCountPerLaps();
        if (temp.get(temp.size() - 1) > 0) {
            temp.set(temp.size() - 1, temp.get(temp.size() - 1) - 1);
            this.currentSessionData.setCountPerSession(this.currentSessionData.getCountPerSession() - 1);
        }
        return new Message(Message.Endpoint.decrementLastLap, Message.Target.lapPerSessionAndCountPerSession);
    };

    public AppServerEndpoint resetCurrentLapToZero = (String... parameters) -> {
        ArrayList<Integer> temp = this.currentSessionData.getCountPerLaps();
        temp.set(temp.size() - 1, 0);
        return new Message(Message.Endpoint.resetCurrentLapToZero, Message.Target.lapPerSessionAndCountPerSession);
    };

    public AppServerEndpoint editSessionName = (String... parameters) -> {
        Integer sessionId = Integer.parseInt(parameters[0]);
        for (SessionData sessionData : this.sessionDatas) {
            if (sessionId == sessionData.getSessionID()) {
                sessionData.setSessionName(parameters[1]);
            }
        }
        return new Message(Message.Endpoint.editSessionName, Message.Target.sessionName);
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

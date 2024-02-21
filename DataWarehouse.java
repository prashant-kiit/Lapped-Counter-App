import java.util.ArrayList;

@FunctionalInterface
interface DataWarehouseAttributeLambda {
    void operate(SessionData currentSessionData);
}

public class DataWarehouse {
    private static ArrayList<SessionData> sessionDatas = null;
    private static DataWarehouse dataWarehouse = null;
    private DataWarehouse() {}

    public static DataWarehouse getInstance() {
        if (dataWarehouse == null) {
            dataWarehouse = new DataWarehouse();
            sessionDatas = new ArrayList<SessionData>();
        }
        return dataWarehouse;
    }

    public DataWarehouseAttributeLambda setSessionData = (SessionData currentSessionData) -> {
        sessionDatas.add(currentSessionData);
    };

    public DataWarehouseAttributeLambda setSessionName = (SessionData currentSessionData) -> {
        int sessionId = currentSessionData.getSessionID();
        for(SessionData sessionData : sessionDatas) {
            if(sessionData.getSessionID() == sessionId) {
                sessionData.setSessionName(currentSessionData.getSessionName());
            }
        }
    }; 

    public DataWarehouseAttributeLambda setCountPerSession = (SessionData currentSessionData) -> {
        int sessionId = currentSessionData.getSessionID();
        for(SessionData sessionData : sessionDatas) {
            if(sessionData.getSessionID() == sessionId) {
                sessionData.setCountPerSession(currentSessionData.getCountPerSession());
            }
        }
    }; 

    public DataWarehouseAttributeLambda setCountPerLaps = (SessionData currentSessionData) -> {
        int sessionId = currentSessionData.getSessionID();
        for(SessionData sessionData : sessionDatas) {
            if(sessionData.getSessionID() == sessionId) {
                sessionData.setCountPerLaps(currentSessionData.getCountPerLaps());
            }
        }
    };
}

import java.util.ArrayList;

@FunctionalInterface
interface DataWarehouseAttributeLambda {
    void operate(Database database1, Integer currentSessionDataIndex);
}

public class DataWarehouse {
    private static ArrayList<SessionData> sessionDatas = null;
    private static DataWarehouse dataWarehouse = null;
    private DataWarehouse() {}

    public static DataWarehouse getInstance( ) {
        if (dataWarehouse == null) {
            dataWarehouse = new DataWarehouse();
            sessionDatas = new ArrayList<SessionData>();
        }
        return dataWarehouse;
    }

    public DataWarehouseAttributeLambda setSessionData = (Database database, Integer currentSessionDataIndex) -> {
        sessionDatas.add(database.getDatabase().get(currentSessionDataIndex));
    };

    public DataWarehouseAttributeLambda setSessionName = (Database database, Integer currentSessionDataIndex) -> {
        int sessionId = database.getDatabase().get(currentSessionDataIndex).getSessionID();
        String sessionName = database.getDatabase().get(currentSessionDataIndex).getSessionName();
        for(SessionData sessionData : sessionDatas) {
            if(sessionData.getSessionID() == sessionId) {
                sessionData.setSessionName(sessionName);
                break;
            }
        }
    }; 

    public DataWarehouseAttributeLambda setCountPerSession = (Database database, Integer currentSessionDataIndex) -> {
        int sessionId = database.getDatabase().get(currentSessionDataIndex).getSessionID();
        int countPerSession = database.getDatabase().get(currentSessionDataIndex).getCountPerSession();
        for(SessionData sessionData : sessionDatas) {
            if(sessionData.getSessionID() == sessionId) {
                sessionData.setCountPerSession(countPerSession);
                break;
            }
        }
    }; 

    public DataWarehouseAttributeLambda setCountPerLaps = (Database database, Integer currentSessionDataIndex) -> {
        int sessionId = database.getDatabase().get(currentSessionDataIndex).getSessionID();
        ArrayList<Integer> countPerLaps = database.getDatabase().get(currentSessionDataIndex).getCountPerLaps();
        for(SessionData sessionData : sessionDatas) {
            if(sessionData.getSessionID() == sessionId) {
                sessionData.setCountPerLaps(countPerLaps);
                break;
            }
        }
    };
}

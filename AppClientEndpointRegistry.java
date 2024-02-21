import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class AppClientEndpointRegistry {
    private AppServer appServer = null;

    private Map<String, AppClientEndpoint> endpointIndex = null;

    private AppClientEndpoint addCurrentSessionDataEndpoint = () -> {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        String sessionName = "";
        try {
            System.out.println("Enter Session Name");
            sessionName = read.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.appServer.execute(this.appServer.addCurrentSessionData, sessionName);
    };

    private AppClientEndpoint addCurrentLapEndpoint = () -> {
        this.appServer.execute(this.appServer.addCurrentLap, "");
        System.out.println("New Lap Added");
    };

    private AppClientEndpoint incrementLastLapEndpoint = () -> {
        this.appServer.execute(this.appServer.incrementLastLap, "");
        System.out.println("Incremented Count");
    };

    private AppClientEndpoint decrementLastLapEndpoint = () -> {
        this.appServer.execute(this.appServer.decrementLastLap, "");
        System.out.println("Decremented Count");
    };

    private AppClientEndpoint resetCurrentLapToZeroEndpoint = () -> {
        this.appServer.execute(this.appServer.resetCurrentLapToZero, "");
        System.out.println("Current LapReset to Zero");
    };

    private AppClientEndpoint editSessionNameEndpoint = () -> {
        System.out.println("Got all session Names from Database");
        System.out.println(this.appServer.getSessionNameListFromDatabase());
        String sessionIdString = "";
        String newSessionName = "";
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Enter SessionId");
            sessionIdString = read.readLine();
            System.out.println("Enter SessionName");
            newSessionName = read.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.appServer.execute(this.appServer.editSessionName, sessionIdString, newSessionName);
        System.out.println("Session name edited");
    };

    public AppClientEndpointRegistry(AppServer appServer) {
        this.appServer = appServer;
        this.endpointIndex = new HashMap<>();
        this.endpointIndex.put("1", addCurrentSessionDataEndpoint);
        this.endpointIndex.put("2", addCurrentLapEndpoint);
        this.endpointIndex.put("3", incrementLastLapEndpoint);
        this.endpointIndex.put("4", decrementLastLapEndpoint);
        this.endpointIndex.put("5", resetCurrentLapToZeroEndpoint);
        this.endpointIndex.put("6", editSessionNameEndpoint);
    }

    public Map<String, AppClientEndpoint> getEndpointIndex() {
        return this.endpointIndex;
    }
}
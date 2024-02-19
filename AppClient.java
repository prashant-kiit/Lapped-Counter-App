import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AppClient extends Thread {
    private AppServer appServer = null;

    public AppClient(AppServer appServer) {
        this.appServer = appServer;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Enter something-1,2,3,4");
            BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
            String input = null;
            try {
                input = read.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (input.equals("1")) {
                String sessionName = "";
                try {
                    System.out.println("Enter Session Name");
                    sessionName = read.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                this.appServer.addCurrentSessionData(sessionName);
            } else if (input.equals("2")) {
                this.appServer.addCurrentLap();
                System.out.println("New Lap Added");
            } else if (input.equals("3")) {
                this.appServer.incrementLastLap();
                System.out.println("Incremented");
            } else if (input.equals("4")) {
                this.appServer.decrementLastLap();
                System.out.println("Decremented");
            } else if (input.equals("5")) {
                this.appServer.resetCurrentLapToZero();
                System.out.println("Current LapReset to Zero");
            } else if (input.equals("6")) {
                System.out.println("Got all session Names from Database");
                System.out.println(this.appServer.getSessionNameListFromDatabase());
                String sessionIdString = "";
                String newSessionName = "";
                try {
                    System.out.println("Enter SessionId");
                    sessionIdString = read.readLine();
                    System.out.println("Enter SessionName");
                    newSessionName = read.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Integer sessionId = 0;
                try {
                    sessionId = Integer.parseInt(sessionIdString);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                this.appServer.editSessionName(sessionId, newSessionName);
            } else {
                System.out.println("Wrong Input");
            }
        }
    }
}

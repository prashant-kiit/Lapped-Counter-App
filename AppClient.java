import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AppClient extends Thread {
    private AppClientEndpointRegistry appClientEndpointRegistry = null;
    private AppClientEndpoint request = null;

    public AppClient(AppServer appServer) {
        this.appClientEndpointRegistry = new AppClientEndpointRegistry(appServer);
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Enter something-1,2,3,4");
            BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
            String endpointIndex = null;
            try {
                endpointIndex = read.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.request = this.appClientEndpointRegistry.getEndpointIndex().get(endpointIndex);
            this.request.operate();
        }
    }
}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class AppClient extends Thread {
    private AppClientEndpointRegistry appClientEndpointRegistry = null;
    private AppClientEndpoint request = null;
    private String button = null;
    private final String[] buttons = {"1","2","3","4","5","6"};

    public AppClient(AppServer appServer) {
        this.appClientEndpointRegistry = new AppClientEndpointRegistry(appServer);
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Enter something-1,2,3,4,5,6");
            BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
            try {
                button = read.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Arrays.asList(buttons).contains(button)) {
                this.request = this.appClientEndpointRegistry.getEndpointIndex().get(button);
                this.request.operate();
            }
            else {
                System.out.println("Wrong Input");
            }
        }
    }
}

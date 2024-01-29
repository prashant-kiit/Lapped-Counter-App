import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Application {
    static int sessionSwitch = 1;

    static int count(int lap) throws IOException{
        int countPerLap = 0;

        while (true) {
            System.out.println("----------------Enter continue/end Lap " + lap + "------------------\n");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            int input = Integer.parseInt(reader.readLine());

            if (input == 0) {
                System.out.println("\n------------------------Lap " + lap + " ends------------------------");
                break;
            }

            if (input == -1) {
                sessionSwitch = 0;
                break;
            }

            countPerLap++;
            System.out.println("\nCount " + countPerLap + "\n");
        }

        return countPerLap;
    }

    public static void main(String[] args) throws IOException {
        Database database = new Database();
        while (true) {
            System.out.println("\n----------------------Session starts----------------------------\n");

            int countPerSession = 0;
            int lap = 0;
            ArrayList<Integer> countPerLaps = new ArrayList<Integer>();
            countPerLaps.add(-1);
            SessionData sessionData = null;

            while (true) {
                if (sessionSwitch == 0) {
                    System.out.println("----------------------Session ends----------------------------\n");
                    break;
                }
                lap++;
                System.err.println("---------------------Lap " + lap + " starts-----------------------\n");

                int countPerLap = count(lap);

                countPerLaps.add(countPerLap);
                countPerSession += countPerLap;
                System.out.println("\nCount in Lap " + lap + " - " + countPerLap);
                System.out.println("\nCount Per Session " + countPerSession + "\n");
            }

            System.err.println("---------------------Save/Not Save-----------------------\n");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            int save = Integer.parseInt(reader.readLine());
            if (save == 1) {
                System.err.println("---------------------Saving-----------------------\n");
                sessionData = new SessionData(countPerLaps, countPerSession);
                database.save(sessionData);
            } else {
                System.err.println("\n---------------------Save Cancelled-----------------------\n");
            }
            // async saving
            Integer.parseInt(reader.readLine());
            sessionSwitch = 1;
            // async termination
        }
    }
}
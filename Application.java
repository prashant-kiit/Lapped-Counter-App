import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Application {
    static int sessionSwitch = 0;
    static int lapSwitch = 0;

    static int count(int lap) throws IOException {
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
                sessionSwitch = 1;
                break;
            }

            if (input == -2) {
                System.out.println("\n-------------Redoing the count " + (countPerLap) + "---------------");
                countPerLap -= 2;
            }

            if (input == -3) {
                lapSwitch = 1;
                break;
            }

            countPerLap++;
            System.out.println("\nCount " + countPerLap + "\n");
        }

        return countPerLap;
    }

    public static void main(String[] args) throws IOException {
        Database database = new Database();
        String sessionName = new String("");

        while (true) {
            System.out.println("\n----------------------Session starts----------------------------\n");

            System.out.println("-------------Enter the name of the Session-------------\n");
            BufferedReader read = new BufferedReader(new InputStreamReader(System.in));

            sessionName = read.readLine();

            int countPerSession = 0;
            int lap = 1;
            ArrayList<Integer> countPerLaps = new ArrayList<Integer>();
            countPerLaps.add(-1);
            SessionData sessionData = null;

            while (true) {
                if (sessionSwitch == 1) {
                    System.out.println("----------------------Session ends----------------------------\n");
                    break;
                }
                System.err.println("\n---------------------Lap " + lap + " starts-----------------------\n");

                int countPerLap = count(lap);

                if (lapSwitch == 1) {
                    System.out.println("\n-----------------Redoing the Lap " + lap + " -----------------");
                } else {
                    countPerLaps.add(countPerLap);
                    countPerSession += countPerLap;
                    System.out.println("\nCount in Lap " + lap + " - " + countPerLap);
                    System.out.println("\nCount Per Session " + countPerSession + "\n");
                    lap++;
                }
            }

            System.err.println("---------------------Save/Not Save-----------------------\n");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            int save = Integer.parseInt(reader.readLine());
            if (save == 1) {
                sessionData = new SessionData(sessionName, countPerLaps, countPerSession);
                database.save(sessionData);
                System.err.println("\n------------------Saved " + sessionName + " -----------------\n");
            } else {
                System.err.println("\n---------------------Save Cancelled-----------------------\n");
            }

            int end = Integer.parseInt(reader.readLine());

            if (end == 0) {
                System.out.println("\n---------------App Terminates / Exit from App-------------------");
                break;
            }
            sessionSwitch = 0;
        }
    }
}
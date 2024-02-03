import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Application extends Thread {
    private int sessionSwitch = 0;
    private int lapSwitch = 0;
    private Database database = null;

    public Application(Database database) {
        this.database = database;
    }

    public int count(int lap) throws IOException {
        int countPerLap = 0;

        while (true) {
            System.out.println("----------------Enter continue/end Lap " + lap + "------------------\n");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            int input = Integer.parseInt(reader.readLine());

            // To change Lap
            if (input == 0) {
                System.out.println("\n------------------------Lap " + lap + " ends------------------------");
                break;
            }

            // Redo Counting
            if (input == -1) {
                System.out.println("\n-------------Redoing the count " + countPerLap + "---------------");
                countPerLap -= 2;
            }

            // Redo Lap
            if (input == -2) {
                lapSwitch = 1;
                break;
            }

            // To change/exit Session
            if (input == -3) {
                sessionSwitch = 1;
                break;
            }

            countPerLap++;
            System.out.println("\nCount " + countPerLap + "\n");
        }

        return countPerLap;
    }

    public void run() {
        String sessionName = " ";

        // Counting and Lapping
        while (true) {
            System.out.println("\n----------------------Session starts----------------------------\n");

            System.out.println("-------------Enter the name of the Session-------------\n");
            BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
            try {
                sessionName = read.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            SessionData sessionData = null;
            int lap = 1;
            ArrayList<Integer> countPerLaps = new ArrayList<Integer>();
            int countPerSession = 0;

            while (true) {
                if (sessionSwitch == 1) {
                    System.out.println("----------------------Session ends/exited----------------------------\n");
                    break;
                }
                System.err.println("\n---------------------Lap " + lap + " starts-----------------------\n");

                int countPerLap = 0;
                try {
                    countPerLap = count(lap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (lapSwitch == 1) {
                    System.out.println("\n-----------------Redoing the Lap " + lap + " -----------------");
                    lapSwitch = 0;
                } else {
                    countPerLaps.add(countPerLap);
                    countPerSession += countPerLap;
                    System.out.println("\nCount in Lap " + lap + " - " + countPerLap);
                    System.out.println("\nCount Per Session " + countPerSession + "\n");
                    lap++;
                }
            }

            // Saving Reminder
            System.err.println("---------------------Save/Not Save-----------------------\n");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            int saved = 0;
            try {
                saved = Integer.parseInt(reader.readLine());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (saved == 1) {
                sessionData = new SessionData(sessionName, countPerLaps, countPerSession);
                database.save(sessionData);
                System.err.println("\n------------------Saved " + sessionName + "-----------------");
                saved = 0;
            } else {
                System.err.println("\n---------------------Save Cancelled-----------------------");
            }


            // Auto Saving

            // Termination
            // BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            // int end = 1;
            // try {
            // end = Integer.parseInt(reader.readLine());
            // } catch (NumberFormatException e) {
            // e.printStackTrace();
            // } catch (IOException e) {
            // e.printStackTrace();
            // }

            // if (end == 0) {
            // System.out.println("\n---------------App Terminates / Exit from
            // App-------------------");
            // break;
            // }
            // while (true) {
            // if (end == 0) {
            // break session;
            // }
            // }
            sessionSwitch = 0;
        }
    }
}
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Query extends Thread {

    public void selectAll(Database database) {
        for (int i = 0; i < database.getDatabase().size(); i++) {
            System.out.println(database.getDatabase().get(i).getSessionID());
            System.out.println(database.getDatabase().get(i).getSessionName());
            System.out.println(database.getDatabase().get(i).getDate());
            System.out.println(database.getDatabase().get(i).getTime());
            System.out.println(database.getDatabase().get(i).getCountPerSession());
            System.out.println(database.getDatabase().get(i).getCountPerLaps().toString());
            System.out.println("------------------------------------------------------------");
        }
    }

    public void filterBySessionId(Database database) {

    }

    public void filterBySessionName(Database database) {

    }

    public void filterByDate(Database database) {

    }

    public void filterByTime(Database database) {

    }

    public void filterByCounterPerSession(Database database) {

    }

    public void run() {
        Database database = Database.getInstance();
        while (true) {
            System.out.println("Enter query options: 1,2,3,4,5,6");
            BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
            int input = 0;

            try {
                input = Integer.parseInt(read.readLine());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (input == 100) {
                selectAll(database);
            }

            if (input == 2) {
                filterBySessionId(database);
            }
            if (input == 3) {

                filterBySessionName(database);
            }
            if (input == 4) {
                filterByDate(database);

            }
            if (input == 5) {

                filterByTime(database);
            }
            if (input == 6) {

                filterByCounterPerSession(database);
            }
        }
    }
}

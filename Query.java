import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    
    public void filterBySessionId(Database database, Integer targetSessionId) {
        for (int i = 0; i < database.getDatabase().size(); i++) {
            if(database.getDatabase().get(i).getSessionID() == targetSessionId){
                System.out.println(database.getDatabase().get(i).getSessionID());
                System.out.println(database.getDatabase().get(i).getSessionName());
                System.out.println(database.getDatabase().get(i).getDate());
                System.out.println(database.getDatabase().get(i).getTime());
                System.out.println(database.getDatabase().get(i).getCountPerSession());
                System.out.println(database.getDatabase().get(i).getCountPerLaps().toString());
                System.out.println("------------------------------------------------------------");
            }
        }
    }

    public void filterBySessionName(Database database, String targetSessionName) {
        for (int i = 0; i < database.getDatabase().size(); i++) {
            if(database.getDatabase().get(i).getSessionName().contains(targetSessionName)){
                System.out.println(database.getDatabase().get(i).getSessionID());
                System.out.println(database.getDatabase().get(i).getSessionName());
                System.out.println(database.getDatabase().get(i).getDate());
                System.out.println(database.getDatabase().get(i).getTime());
                System.out.println(database.getDatabase().get(i).getCountPerSession());
                System.out.println(database.getDatabase().get(i).getCountPerLaps().toString());
                System.out.println("------------------------------------------------------------");
            }
        }
    }

    public void filterByDate(Database database, Date date1, Date date2 ) throws ParseException {     
        for (int i = 0; i < database.getDatabase().size(); i++) {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(database.getDatabase().get(i).getDate());          
            if(date.after(date1) && date.before(date2)) {
                System.out.println(database.getDatabase().get(i).getSessionID());
                System.out.println(database.getDatabase().get(i).getSessionName());
                System.out.println(database.getDatabase().get(i).getDate());
                System.out.println(database.getDatabase().get(i).getTime());
                System.out.println(database.getDatabase().get(i).getCountPerSession());
                System.out.println(database.getDatabase().get(i).getCountPerLaps().toString());
                System.out.println("------------------------------------------------------------");
            }
        }
    }

    public void filterByTime(Database database) {

    }

    public void filterByCounterPerSession(Database database) {

    }

    public void run() {
        Database database = Database.getInstance();
        // System.out.println(database);
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
                Integer targetSessionId = null;
                try {
                    targetSessionId = Integer.parseInt(read.readLine());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                filterBySessionId(database, targetSessionId);
            }

            if (input == 3) {
                String targetSessionName = null;
                try {
                    targetSessionName = read.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                filterBySessionName(database, targetSessionName);
            }

            if (input == 4) {
                try {
                    String date1 = read.readLine();
                    Date date11 = new SimpleDateFormat("yyyy-MM-dd"). parse(date1);
                    String date2 = read.readLine();
                    Date date22 = new SimpleDateFormat("yyyy-MM-dd"). parse(date2);
                    filterByDate(database, date11, date22);
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

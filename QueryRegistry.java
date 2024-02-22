import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class QueryRegistry {
    private Database database = null;
    private Map<String, Query> queryIndex = null;
    private ArrayList<String> result = null;

    private String getJsonstring(int i) {
        return "{\"SessionID\" : " + database.getDatabase().get(i).getSessionID() + ", \"SessionName\" : \""
                + database.getDatabase().get(i).getSessionName() + "\", \"Date\" : \""
                + database.getDatabase().get(i).getDate() + "\", \"Time\" : \""
                + database.getDatabase().get(i).getTime()
                + "\", \"CountPerSession\" : " + database.getDatabase().get(i).getCountPerSession()
                + ", \"CountPerLaps\" : " + database.getDatabase().get(i).getCountPerLaps().toString() + "}";
    }

    private Query selectAll = (ArrayList<String> paramaters) -> {
        for (int i = 0; i < database.getDatabase().size(); i++)
            result.add(getJsonstring(i));
        return result;
    };

    private Query filterBySessionId = (ArrayList<String> paramaters) -> {
        Integer targetSessionId = Integer.parseInt(paramaters.get(0));
        for (int i = 0; i < database.getDatabase().size(); i++) {
            if (database.getDatabase().get(i).getSessionID() == targetSessionId) {
                System.out.println("true");
                result.add(getJsonstring(i));
            }
        }
        return result;
    };

    private Query filterBySessionName = (ArrayList<String> paramaters) -> {
        String targetSessionName = paramaters.get(0);
        for (int i = 0; i < database.getDatabase().size(); i++) {
            if (database.getDatabase().get(i).getSessionName().contains(targetSessionName)) {
                System.out.println("true");
                result.add(getJsonstring(i));
            }
        }
        return result;
    };

    private Query filterByDate = (ArrayList<String> paramaters) -> {
        Date date1 = new Date();
        Date date2 = new Date();
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(paramaters.get(0));
            date2 = new SimpleDateFormat("yyyy-MM-dd").parse(paramaters.get(1));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < database.getDatabase().size(); i++) {
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(database.getDatabase().get(i).getDate());
                if (date.after(date1) && date.before(date2)) {
                    result.add(getJsonstring(i));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return result;
    };

    private Query filterByTime = (ArrayList<String> paramaters) -> {
        Time time1 = Time.valueOf(paramaters.get(0));
        Time time2 = Time.valueOf(paramaters.get(1));
        for (int i = 0; i < database.getDatabase().size(); i++) {
            Time time = Time.valueOf(database.getDatabase().get(i).getTime());
            int tmp1 = time.compareTo(time1);
            int tmp2 = time.compareTo(time2);
            if (tmp2 >= 0 && tmp1 <= 0) {
                result.add(getJsonstring(i));
            }
        }
        return result;
    };

    public QueryRegistry(Database database) {
        this.database = database;
        this.queryIndex = new HashMap<>();
        this.result = new ArrayList<>();
        this.queryIndex.put("1", selectAll);
        this.queryIndex.put("2", filterBySessionId);
        this.queryIndex.put("3", filterBySessionName);
        this.queryIndex.put("4", filterByDate);
        this.queryIndex.put("5", filterByTime);
    }

    public Map<String, Query> getQueryIndex() {
        return this.queryIndex;
    }
}
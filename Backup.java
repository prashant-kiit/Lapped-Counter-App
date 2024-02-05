import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Backup extends Thread {
    private Database database = null;

    public Backup(Database database) {
        this.database = database;
    }

    @Override
    public void run() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("databaseBackup.ser"))) {
                oos.writeObject(database);
                System.out.println("Database has been serialized/backed-up");
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        
        scheduler.scheduleWithFixedDelay(task, 0, 15, TimeUnit.MINUTES);
    }
}

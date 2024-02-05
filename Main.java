public class Main {
    public static void main(String[] args) {
        Database database = Database.getInstance();
        Backup backup = new Backup(database);
        backup.start();
        Query query = new Query(database);
        query.start();
        Application application = new Application(database);
        application.start();
    }
}

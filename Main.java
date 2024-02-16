public class Main {
    public static void main(String[] args) {
        Database database = Database.getInstance();
        Backup backup = new Backup(database);
        backup.start();
        QueryServer queryServer = new QueryServer(database);
        queryServer.start();
        Application application = new Application(database);
        application.start();
    }
}

public class Main {
    public static void main(String[] args) {
        Database database = Database.getInstance();
        AppServer appServer = new AppServer(database);
        Backup backup = new Backup(database);
        backup.start();
        QueryServer queryServer = new QueryServer(database);
        queryServer.start();
        AppClient appClient = new AppClient(appServer);
        appClient.start();
    }
}

public class Main {
    public static void main(String[] args) {
        MessageQueue messageQueue = MessageQueue.getInstance();
        Database database = Database.getInstance();
        AppServer appServer = new AppServer(database, messageQueue);
        Backup backup = new Backup(database);
        backup.start();
        QueryServer queryServer = new QueryServer(database);
        queryServer.start();
        AppClient appClient = new AppClient(appServer);
        appClient.start();
    }
}

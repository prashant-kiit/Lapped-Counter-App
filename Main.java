public class Main {
    public static void main(String[] args) {
        Database database = Database.getInstance();
        DataWarehouse dataWarehouse = DataWarehouse.getInstance();
        MessageQueue messageQueue = MessageQueue.getInstance();
        Ingestor ingestor = new Ingestor(messageQueue);
        ingestor.start(); 
        AppServer appServer = new AppServer(database, messageQueue, dataWarehouse);
        Backup backup = new Backup(database);
        backup.start();
        QueryServer queryServer = new QueryServer(database);
        queryServer.start();
        AppClient appClient = new AppClient(appServer);
        appClient.start();
        // python-java gateway for extraction and loading data from/to data warehouse
    }
}

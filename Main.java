public class Main {
    public static void main(String[] args) {
        Database database = Database.getInstance();
        Query query = new Query(database);
        query.start();
        Application application = new Application(database);
        application.start();
    }
}

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class MessageQueue {
    private static MessageQueue messageQueue = null;
    private static Queue<Message> queue = null;
    private MessageQueue() {}
    
    public static MessageQueue getInstance() {
        if(messageQueue == null) {
            messageQueue = new MessageQueue();
            queue = new LinkedList<>();
        }
        System.out.println("Message Queue Created");
        return messageQueue;
    }

    public synchronized void load(Message message) {
        queue.add(message);
        System.out.println("Message Added");
    }

    public synchronized Message removeLastElement() {
        System.out.println("Message Removed");
        return queue.remove();
    }

}

class Message {
    private Database database = null;
    private Integer currentSessionDataIndex = null;
    private DataWarehouseAttributeLambda[] dataWarehouseAttributeLambdas = null;

    public Message(Database database, Integer currentSessionDataIndex, DataWarehouseAttributeLambda... dataWarehouseAttributeLambdas) {
        this.database = database;
        this.currentSessionDataIndex = currentSessionDataIndex;
        this.dataWarehouseAttributeLambdas = dataWarehouseAttributeLambdas;
    }

    public synchronized Database getDatabase() {
        return this.database;
    }

    public synchronized DataWarehouseAttributeLambda[] getDataWarehouseAttributeLambda() {
        return this.dataWarehouseAttributeLambdas;
    }

    public synchronized Integer getCurrentSessionDataIndex() {
        return this.currentSessionDataIndex;
    }

    @Override
    public String toString() {
        return "[ " + this.database + " : "  + this.currentSessionDataIndex + " : " + Arrays.toString( this.dataWarehouseAttributeLambdas) + " ]";
    }
}
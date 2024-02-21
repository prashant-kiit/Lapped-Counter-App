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
    
    public Queue<Message> getMessageQueue() {
        return queue;
    }

    public void load(Message message) {
        queue.add(message);
        System.out.println(message.toString());
        System.out.println("Message Queued");
    }
}
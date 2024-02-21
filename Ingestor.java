import java.util.Arrays;
import java.util.Queue;

public class Ingestor extends Thread {
    private MessageQueue messageQueue = null;
    private Queue<Message> queue = null;
    private Message currentMessage = null;

    public Ingestor(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    @Override
    public void run() {
        this.queue = this.messageQueue.getMessageQueue();
        while(true) {
            if(!this.queue.isEmpty()) {
                this.currentMessage = this.queue.remove();
                Arrays.stream(this.currentMessage.dataWarehouseAttributeLambdas)
                .forEach(dataWarehouseAttributeLambda -> dataWarehouseAttributeLambda.operate(this.currentMessage.currentSessionData));
            }
        }
    }
}

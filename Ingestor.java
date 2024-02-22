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
        while(true) {
            if(!this.queue.isEmpty()) {
                this.currentMessage = this.messageQueue.removeLastElement();
                Arrays.stream(this.currentMessage.getDataWarehouseAttributeLambda())
                .forEach(dataWarehouseAttributeLambda -> dataWarehouseAttributeLambda.operate(this.currentMessage.getDatabase(), this.currentMessage.getCurrentSessionDataIndex()));
            }
        }
    }
}

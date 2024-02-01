/**
 * Browser
 */
package Socketeer.JSlike;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Browser {
    public static int id = 0;

    public static void setTimeout(Function fun, int para, int sleepTime) {
        id++;
        Runnable sleeper = new Sleeper(id, fun, para, sleepTime);
        new Thread(sleeper).start();
    }
}

class Registry {
    public int id;
    public Function fun;
    public int para;
}

class Sleeper implements Runnable {
    private int sleepTime;
    private Registry registry = new Registry();

    public Sleeper(int id, Function fun, int para, int sleepTime) {
        this.registry.id = id;
        this.registry.fun = fun;
        this.registry.para = para;
        this.sleepTime = sleepTime;
    }

    @Override
    public void run() {
        System.out.println(
                "Sleeper " + this.registry.id + " is running on thread " + Thread.currentThread().getName());
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        CallbackQueue.queue.add(this.registry);
    }
}

class CallbackQueue {
    public static Queue<Registry> queue = new LinkedList<>();
}

class EventLoop implements Runnable {
    @Override
    public void run() {
        while (true) {
            if (CallStack.stack.empty()) {
                Registry registry = CallbackQueue.queue.poll();
                if (registry != null) {
                    CallStack.stack.push(registry);
                }
            }
        }
    }
}

class CallStack implements Runnable {
    public static Stack<Registry> stack = new Stack<>();

    @Override
    public void run() {
        while (true) {
            if (!stack.empty()) {
                Registry registry = stack.pop();
                System.out.println(registry.fun.show(registry.para));
            }
        }
    }
}
package Tester;

public class Broker {
    private static String message = "INACTIVE";
    private static int isTerminated = 0;
    private static int terminateDone = 0;
    //private static int counter = 0;

    public static void feed(String button) {
        message = button;
    }

    public static String retrieve() {
        return message;
    }

    public static int getIsTerminated() {
        return isTerminated;
    }

    public static void terminate() {
        isTerminated = 1;
    }

    public static void terminateNoteToClient() {
        isTerminated = 0;
        terminateDone = 1;
    }

    public static int getTerminateNoteToClient() {
        return terminateDone;
    }

    public static void resetTerminateNoteToClient() {
        terminateDone = 0;
    }

}

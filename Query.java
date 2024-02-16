import java.util.ArrayList;

@FunctionalInterface
public interface Query {
    ArrayList<String> operate(ArrayList<String> paramaters);
}

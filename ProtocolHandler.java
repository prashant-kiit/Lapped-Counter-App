import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ProtocolHandler {
    private static String json = new String();
    private static Map<String, String> map = new HashMap<>();

    public static String jsonify(Map<String, String> map) {
        String elements = "";
        for (String key : map.keySet()) {
            String part1 = "\"" + key + "\"";
            String part2 = "\"" + map.get(key) + "\"";
            String element = part1 + ":" + part2 + ",";
            elements = elements + element;
        }
        json = "{" + elements + "\"Status\":\"Active\"}";
        return json;
    }
    
    public static Map<String, String> mapify(String json) {
        String elementString = json.substring(1, json.length() - 1);
        ArrayList<String> elements = new ArrayList<>(Arrays.asList(elementString.split(",")));
        for(String element : elements) {
            ArrayList<String> parts = new ArrayList<>(Arrays.asList(element.split(":")));
            String part1 = parts.get(0).substring(1, parts.get(0).length()-1);
            String part2 = parts.get(1).substring(1, parts.get(1).length()-1);
            System.out.println("part1="+part1);
            System.out.println("part2="+part2);
            map.put(part1, part2);
        }
        return map;
    }
}

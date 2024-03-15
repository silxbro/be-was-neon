package business;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class BusinessMapper {
    private static final Map<String, Consumer<String>> businessMap = init();

    public static boolean isBusiness(String path) {
        return businessMap.containsKey(path);
    }

    public static Consumer<String> getBusiness(String path) {
        return businessMap.get(path);
    }

    private static Map<String, Consumer<String>> init() {
        Map<String, Consumer<String>> businessMap = new HashMap<>();
        businessMap.put("/user/create", Registration::execute);
        return businessMap;
    }
}

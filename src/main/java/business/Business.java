package business;

import http.HttpRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Business {
    private static final Map<String, Consumer<String>> businessMap = new HashMap<>();

    static {
        businessMap.put("/user/create", Registration::execute);
    }

    public static void execute(HttpRequest request) {
        if (isBusiness(request)) {
            businessMap.get(request.getAbsolutePath()).accept(request.getQuery());
        }
    }

    public static boolean isBusiness(HttpRequest request) {
        return businessMap.containsKey(request.getAbsolutePath());
    }
}

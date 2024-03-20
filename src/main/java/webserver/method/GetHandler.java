package webserver.method;

import business.Registration;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import webserver.http.HttpRequest;

public class GetHandler implements MethodHandler {

    private static final Map<String, Consumer<String>> businessMap = new HashMap<>();

    static {
        businessMap.put("/user/create", Registration::execute);
    }

    @Override
    public void process(HttpRequest request) {
        if (isBusiness(request)) {
            String absolutePath = request.getAbsolutePath();
            String query = request.getQuery();
            businessMap.get(absolutePath).accept(query);
        }
    }

    private boolean isBusiness(HttpRequest request) {
        return businessMap.containsKey(request.getAbsolutePath());
    }
}
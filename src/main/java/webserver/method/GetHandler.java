package webserver.method;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import webserver.http.HttpRequest;

public class GetHandler implements MethodHandler {

    private static final Map<String, Consumer<String>> businessMap = new HashMap<>();
    private static final Set<String> invalidPaths = new HashSet<>();

    static {
    }

    static {
        invalidPaths.add("/user/create");
    }

    @Override
    public void process(HttpRequest request) {
        validateRequest(request);
        if (isBusiness(request)) {
            String absolutePath = request.getAbsolutePath();
            String query = request.getQuery();
            businessMap.get(absolutePath).accept(query);
        }
    }

    private boolean isBusiness(HttpRequest request) {
        return businessMap.containsKey(request.getAbsolutePath());
    }

    private void validateRequest(HttpRequest request) {
        if (invalidPaths.contains(request.getAbsolutePath())) {
            throw new IllegalArgumentException("INVALID_PATH");
        }
    }
}
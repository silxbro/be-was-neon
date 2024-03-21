package webserver.method;

import business.Registration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import webserver.http.HttpRequest;

public class PostHandler implements MethodHandler {

    private static final Map<String, Consumer<String>> businessMap = new HashMap<>();
    private static final Set<String> invalidPaths = new HashSet<>();

    static {
        businessMap.put("/user/create", Registration::execute);
    }

    @Override
    public void process(HttpRequest request) {
        validateRequest(request);
        if (isBusiness(request)) {
            String absolutePath = request.getAbsolutePath();
            String body = request.getBody();
            businessMap.get(absolutePath).accept(body);
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

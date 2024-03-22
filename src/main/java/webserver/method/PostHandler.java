package webserver.method;

import java.util.Set;
import webserver.http.HttpRequest;
import webserver.path.BusinessPath;

public class PostHandler implements MethodHandler {

    private static final Set<BusinessPath> validPaths = Set.of(
        BusinessPath.USER_CREATE, BusinessPath.USER_LOGIN);

    @Override
    public boolean execute(HttpRequest request) {
        BusinessPath path = BusinessPath.of(request.getAbsolutePath());
        String body = request.getBody();
        return (isValid(path) && path.getExecutor().execute(body));
    }

    private boolean isValid(BusinessPath path) {
        return validPaths.contains(path);
    }
}
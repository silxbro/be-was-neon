package webserver.method;

import java.util.Set;
import webserver.http.HttpRequest;
import webserver.path.BusinessPath;

public class GetHandler implements MethodHandler {

    private static final Set<BusinessPath> validPaths = Set.of();

    @Override
    public boolean execute(HttpRequest request) {
        BusinessPath path = BusinessPath.of(request.getAbsolutePath());
        String query = request.getQuery();
        return (isValid(path) && path.getExecutor().execute(query));
    }

    private boolean isValid(BusinessPath path) {
        return validPaths.contains(path);
    }
}
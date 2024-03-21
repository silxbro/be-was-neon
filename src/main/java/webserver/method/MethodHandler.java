package webserver.method;

import webserver.http.HttpRequest;

public interface MethodHandler {
    boolean isValid(HttpRequest request);
    void execute(HttpRequest request);
}

package webserver.method;

import webserver.http.HttpRequest;

public interface MethodHandler {
    boolean execute(HttpRequest request);
}

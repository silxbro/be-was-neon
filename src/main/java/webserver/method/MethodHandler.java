package webserver.method;

import webserver.http.HttpRequest;

public interface MethodHandler {
    void process(HttpRequest request);
}

package webserver.method;

import webserver.http.HttpRequest;

public enum Method {
    GET(new GetHandler()),
    ;
    private final MethodHandler handler;

    Method(MethodHandler handler) {
        this.handler = handler;
    }

    public void process(HttpRequest request) {
        handler.process(request);
    }
}

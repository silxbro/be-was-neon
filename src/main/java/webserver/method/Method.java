package webserver.method;

import webserver.http.HttpRequest;

public enum Method {
    GET(new GetHandler()),
    POST(new PostHandler()),
    ;
    private final MethodHandler handler;

    Method(MethodHandler handler) {
        this.handler = handler;
    }

    public boolean execute(HttpRequest request) {
        return handler.execute(request);
    }
}
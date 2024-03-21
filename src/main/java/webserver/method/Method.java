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

    public boolean needExecution(HttpRequest request) {
        return handler.isValid(request);
    }

    public void execute(HttpRequest request) {
        if (needExecution(request)) {
            handler.execute(request);
        }
    }
}
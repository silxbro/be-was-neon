package webserver;

import java.io.IOException;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.method.Method;

public class Router {

    public void route(HttpRequest request, HttpResponse response) throws IOException {
        getMethod(request).process(request);
        response.send(request);
    }

    private Method getMethod(HttpRequest request) {
        return Method.valueOf(request.getMethod().toUpperCase());
    }
}
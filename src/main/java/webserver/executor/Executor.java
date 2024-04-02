package webserver.executor;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public interface Executor {
    void service(HttpRequest request, HttpResponse response);
}

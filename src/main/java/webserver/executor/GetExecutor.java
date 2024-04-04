package webserver.executor;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public abstract class GetExecutor implements Executor {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        if (request.isGet()) {
            doGet(request, response);
            return;
        }
        response.setMethodError();
    }

    abstract void doGet(HttpRequest request, HttpResponse response);
}

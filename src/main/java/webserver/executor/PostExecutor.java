package webserver.executor;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public abstract class PostExecutor implements Executor {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        if (request.isPost()) {
            doPost(request, response);
            return;
        }
        response.setMethodError();
    }

    abstract void doPost(HttpRequest request, HttpResponse response);
}

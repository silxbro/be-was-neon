package webserver.executor;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public abstract class AbstractExecutor implements Executor {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        if (request.isGet()) {
            doGet(request, response);
            return;
        }
        if (request.isPost()) {
            doPost(request, response);
            return;
        }
    }

    abstract void doGet(HttpRequest request, HttpResponse response);

    abstract void doPost(HttpRequest request, HttpResponse response);
}
